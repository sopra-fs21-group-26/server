/*
package ch.uzh.ifi.hase.soprafs21;


//Test if in full lobby (3 players with ready status) --> startgame

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.service.LobbyService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

*/
/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 *//*

@WebAppConfiguration
@SpringBootTest
public class IntegrationTestStartGame {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("lobbyRepository")
    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private LobbyService lobbyService;

    private User user1;
    private User user2;
    private User user3;

    private Lobby lobby;

    private List<User> playerInLobby;

    @BeforeEach
    public void setup() {
       userRepository.deleteAll();
       lobbyRepository.deleteAll();
    }


    @Test
    public void StartGameWithReadyLobby(){
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("testUsername1");
        user1.setToken("1");
        user1.setOnlineStatus(OnlineStatus.ONLINE);
        user1.setPassword("TestPassword1");
        user1.setCreatedOn();
        //testUser.setCurrentlyCreating("TestCurrentlyCreating");
        user1.setGuessedOtherPicturesCorrectly(0);
        user1.setOwnPicturesCorrectlyGuessed(0);
        user1.setPlayerStatus(PlayerStatus.READY);
        user1.setScore(0);

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("testUsername2");
        user2.setToken("2");
        user2.setOnlineStatus(OnlineStatus.ONLINE);
        user2.setPassword("TestPassword2");
        user2.setCreatedOn();
        //tetUser.setCurrentlyCreating("TestCurrentlyCreating");
        user2.setGuessedOtherPicturesCorrectly(0);
        user2.setOwnPicturesCorrectlyGuessed(0);
        user2.setPlayerStatus(PlayerStatus.READY);
        user2.setScore(0);

        user3 = new User();
        user3.setId(3L);
        user3.setUsername("testUsername3");
        user3.setToken("3");
        user3.setOnlineStatus(OnlineStatus.ONLINE);
        user3.setPassword("TestPassword3");
        user3.setCreatedOn();
        //testUser.setCurrentlyCreating("TestCurrentlyCreating");
        user3.setGuessedOtherPicturesCorrectly(0);
        user3.setOwnPicturesCorrectlyGuessed(0);
        user3.setPlayerStatus(PlayerStatus.READY);
        user3.setScore(0);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.flush();

        playerInLobby= new ArrayList<>();
        playerInLobby.add(user1);
        playerInLobby.add(user2);
        playerInLobby.add(user3);

        lobby = new Lobby();
        lobby.setLobbyId((long) 1);
        lobby.setPlayersInLobby(playerInLobby);
        lobby.setLobbyStatus(LobbyStatus.WAITING);
        lobby.setNumbersOfPlayers(3);
        lobby.setAdmin(user1);
        lobby.setLobbyName("TestLobby");
        lobbyRepository.save(lobby);
        lobbyRepository.flush();

        User user = new User();
        user.setToken("1");

        Game game = new Game();
        game.setGameId(lobby.getLobbyId());
        game.setPlayersInGame(playerInLobby);
        game.setGameName(lobby.getLobbyName());
        game.setNumbersOfPlayers(lobby.getNumbersOfPlayers());
        game.setPlayersInGame(lobby.getPlayersInLobby());
        game.setGameRound(1);
        game.resetAllHasCreated();
        game.resetAllHasCreated();




        Game createdGame = lobbyService.startGame(lobby.getLobbyId(), user);

        assertEquals(game.getGameId(), createdGame.getGameId());


    }
*/

/*
}*/



