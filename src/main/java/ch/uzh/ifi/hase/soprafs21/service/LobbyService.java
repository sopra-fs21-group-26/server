package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LobbyService {

    private final Logger log = LoggerFactory.getLogger(LobbyService.class);

    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;

    @Autowired
    public LobbyService(@Qualifier("lobbyRepository") LobbyRepository lobbyRepository,
                        @Qualifier("userRepository") UserRepository userRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
    }

    //create lobby in userService: set lobbyname, set admin via token,
    // add admin in player list via token, set playerstatus, lobbystatus, set nr of players to 1
    public Lobby createLobby(String lobbyName, User admin){
        Lobby lobby = new Lobby();
        User admin1 = userRepository.findByToken(admin.getToken());
        List<User> playersInLobby = new ArrayList<>();
        List <Lobby> lobbies = this.lobbyRepository.findAll();
        if (admin1 == null){
            String baseErrorMessage = "User with token was not found!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }
        if (admin1.getPlayerStatus() == PlayerStatus.JOINED || admin1.getPlayerStatus() == PlayerStatus.PLAYING){
            String baseErrorMessage = "You are already in a Lobby. You can't create two at the same time!";
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage);
        }
        for (Lobby lobby2 : lobbies){
            if (lobby2.getLobbyName().equals(lobbyName)){
                String baseErrorMessage = "Your lobby name already exists, please choose another one!";
                throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage);
            }
        }

        playersInLobby.add(admin1);

        admin1.setPlayerStatus(PlayerStatus.JOINED);
        lobby.setAdmin(admin1);
        lobby.setLobbyName(lobbyName);
        lobby.setNumbersOfPlayers(1);
        lobby.setPlayersInLobby(playersInLobby);
        lobby.setLobbyStatus(LobbyStatus.WAITING);

        lobby = lobbyRepository.save(lobby);
        lobbyRepository.flush();

        return lobby;
    }
    //User user
    public List<Lobby> getAllAvailableLobbies(User user){
        User user2 = userRepository.findByToken(user.getToken());

        if (user2 == null){
            String baseErrorMessage = "User with token was not found! You don't have access!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }
        List<Lobby> allLobbies = lobbyRepository.findAll();
        List<Lobby> availableLobbies = new ArrayList<>();
        for (Lobby lobby : allLobbies){
            if (lobby.getLobbyStatus() == LobbyStatus.WAITING){
                availableLobbies.add(lobby);
            }
        }
        return availableLobbies;
    }

    //userservice: check Exceptions
    //else: lobby: add to playerlist, increase nrOfPlayers, check if full (change status if yes)
    public Lobby joinSpecificLobby(User user, long lobbyId){
        User userToJoin = userRepository.findByToken(user.getToken());
        Lobby lobbyToJoin = lobbyRepository.findByLobbyId(lobbyId);

        if (userToJoin == null){
            String baseErrorMessage = "User with token was not found! You don't have access!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        if (lobbyToJoin == null){
            String baseErrorMessage = "The lobby you want to join doesn't exist!";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, baseErrorMessage);
        }

        if (lobbyToJoin.getLobbyStatus() == LobbyStatus.PLAYING){
            String baseErrorMessage = "The lobby you want to join already started playing. Find another Lobby!";
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, baseErrorMessage);
        }

        if (lobbyToJoin.getLobbyStatus() == LobbyStatus.FULL){
            String baseErrorMessage = "The lobby you want to join is full. Find another Lobby!";
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, baseErrorMessage);
        }

        lobbyToJoin.addPlayerToPlayersInLobby(userToJoin);
        lobbyToJoin.increaseNumbersOfPlayers();

        if (lobbyToJoin.getNumbersOfPlayers() == 5){
            lobbyToJoin.setLobbyStatus(LobbyStatus.FULL);
        }

        else{
            lobbyToJoin.setLobbyStatus(LobbyStatus.WAITING);
        }

        return lobbyToJoin;
    }

    public Lobby getSingleLobbyById(Long lobbyId){
        return lobbyRepository.findByLobbyId(lobbyId);
    }

    public void leaveLobby(Long lobbyId, User user){
        Lobby lobby = lobbyRepository.findByLobbyId(lobbyId);
        User userToLeave = userRepository.findByToken(user.getToken());

        if(userToLeave == null){
            String baseErrorMessage = "User with token was not found! You don't have access!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        if (lobby == null){
            String baseErrorMessage = "The lobby you want to leave doesn't exist!";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, baseErrorMessage);
        }

        if (!lobby.getPlayersInLobby().contains(userToLeave)){
            String baseErrorMessage = "You are not in this Lobby, you can't leave it!";
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage);
        }

        if (lobby.getLobbyStatus() == LobbyStatus.PLAYING){
            String baseErrorMessage = "The Lobby already started playing, you can't leave it!";
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, baseErrorMessage);
        }

        if (userToLeave.getToken().equals(lobby.getAdmin().getToken())){
            lobbyRepository.delete(lobby);
            lobbyRepository.flush();
        }

        if (lobby.getNumbersOfPlayers() == 1){
            lobbyRepository.delete(lobby);
            lobbyRepository.flush();
        }

        if (lobby.getLobbyStatus() == LobbyStatus.FULL){
            lobby.setLobbyStatus(LobbyStatus.WAITING);
        }

        lobby.decreaseNumbersOfPlayers();
        lobby.deletePlayerInPlayersInLobby(userToLeave);
        userToLeave.setPlayerStatus(PlayerStatus.LEFT);
    }

    public void makePlayerReady(long lobbyId, User user){
        Lobby lobby = lobbyRepository.findByLobbyId(lobbyId);
        User userToMakeReady = userRepository.findByToken(user.getToken());

        if (lobby == null){
            String baseErrorMessage = "You are not in this lobby!";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, baseErrorMessage);
        }

        if(userToMakeReady == null){
            String baseErrorMessage = "User with token was not found! You don't have access!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        if (!lobby.getPlayersInLobby().contains(userToMakeReady)){
            String baseErrorMessage = "You are not in this Lobby, you can't leave it!";
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage);
        }

        userToMakeReady.setPlayerStatus(PlayerStatus.READY);
    }

    public void kickPlayer(long lobbyId, String usernameToKick, User user){
        Lobby lobby = lobbyRepository.findByLobbyId(lobbyId);
        User userWhoWantsToKick = userRepository.findByToken(user.getToken());
        User userToKick = userRepository.findByUsername(usernameToKick);

        if (lobby == null){
            String baseErrorMessage = "You are not in this lobby!";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, baseErrorMessage);
        }

        if(userWhoWantsToKick == null){
            String baseErrorMessage = "User with token was not found! You don't have access!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        if (!lobby.getPlayersInLobby().contains(userToKick)){
            String baseErrorMessage = "You can't kick this Player, he is not in your lobby";
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage);
        }

        if (!userWhoWantsToKick.getToken().equals(lobby.getAdmin().getToken())){
            String baseErrorMessage = "You can't kick this Player, because you are not the admin";
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, baseErrorMessage);
        }

        if (lobby.getNumbersOfPlayers() == 1){
            lobbyRepository.delete(lobby);
            lobbyRepository.flush();
        }

        if (lobby.getLobbyStatus() == LobbyStatus.FULL){
            lobby.setLobbyStatus(LobbyStatus.WAITING);
        }

        userToKick.setPlayerStatus(PlayerStatus.LEFT);

        lobby.decreaseNumbersOfPlayers();
        lobby.deletePlayerInPlayersInLobby(userToKick);
    }

    public void startGame(long lobbyId, User user){
        Lobby lobby = lobbyRepository.findByLobbyId(lobbyId);
        User userWhoWantsToStart = userRepository.findByToken(user.getToken());

        if (lobby == null){
            String baseErrorMessage = "You are not in this lobby!";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, baseErrorMessage);
        }

        if(userWhoWantsToStart == null){
            String baseErrorMessage = "User with token was not found! You don't have access!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        if (!lobby.getPlayersInLobby().contains(userWhoWantsToStart)){
            String baseErrorMessage = "You can't kick this Player, he is not in your lobby";
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage);
        }

        if (lobby.getNumbersOfPlayers() <2){
            String baseErrorMessage = "You can't start the game, you are not enough players";
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, baseErrorMessage);
        }

        if (!lobby.getAdmin().getToken().equals(userWhoWantsToStart.getToken())){
            String baseErrorMessage = "You can't start the game, you are not the admin";
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, baseErrorMessage);
        }

        lobby.changeAllPLayerStatusToPlaying();
        lobby.increaseAllPlayerGamesPlayed();
        lobby.setLobbyStatus(LobbyStatus.PLAYING);
    }


}
