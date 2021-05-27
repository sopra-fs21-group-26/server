package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyServiceTestsMocks {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private LobbyService lobbyService;

    private Lobby testLobby;
    private User testUser;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUsername");
        testUser.setToken("1");
        testUser.setOnlineStatus(OnlineStatus.ONLINE);
        testUser.setPassword("TestPassword");
        testUser.setCreatedOn();
        testUser.setGuessedOtherPicturesCorrectly(1);
        testUser.setOwnPicturesCorrectlyGuessed(1);
        testUser.setPlayerStatus(PlayerStatus.FINISHED);
        testUser.setScore(1);

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        //Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);
        //Mockito.when(lobbyRepository.findByLobbyName(Mockito.anyString())).thenReturn(testLobby);
    }

    @Test
    public void checkLobbyName_exists() {
        Mockito.when(lobbyRepository.findByLobbyName(Mockito.anyString())).thenReturn(testLobby);
        Lobby lobby = lobbyService.checkIfLobbynameExists("TestName");
        assertEquals(1, lobby.getNumbersOfPlayers());
    }

    @Test
    public void checkLobbyName_doesntExist() {

        Mockito.when(lobbyRepository.findByLobbyName(Mockito.anyString())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> lobbyService.checkIfLobbynameExists("Z"));
    }

    @Test
    public void getLobbyByLobbyId() {


        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);
        Lobby lobby = lobbyService.getSingleLobbyById(1L);

        assertEquals(1, lobby.getNumbersOfPlayers());
    }

    @Test
    public void joinSpecificLobbyAlreadyPlaying() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(1);
        testLobby2.setLobbyStatus(LobbyStatus.PLAYING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.joinSpecificLobby(testUser,testLobby2.getLobbyId()));
    }

    @Test
    public void joinSpecificLobbyNoLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(1);
        testLobby2.setLobbyStatus(LobbyStatus.PLAYING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> lobbyService.joinSpecificLobby(testUser,testLobby2.getLobbyId()));
    }

    @Test
    public void joinSpecificLobbyFull() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(1);
        testLobby2.setLobbyStatus(LobbyStatus.FULL);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.joinSpecificLobby(testUser,testLobby2.getLobbyId()));
    }

    @Test
    public void joinSpecificLobby_successful() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(1);
        testLobby2.setLobbyStatus(LobbyStatus.FULL);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        Lobby lobby = lobbyService.joinSpecificLobby(testUser, testLobby.getLobbyId());

        assertEquals(2, lobby.getNumbersOfPlayers());
    }

    @Test
    public void joinSpecificLobby_successfulNowFull() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);

        Lobby lobby = lobbyService.joinSpecificLobby(testUser, testLobby.getLobbyId());

        assertEquals(5, lobby.getNumbersOfPlayers());
    }

    @Test
    public void leaveLobby_noLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> lobbyService.leaveLobby(testLobby.getLobbyId(), testUser ));
    }

    @Test
    public void leaveLobby_notInLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        assertThrows(ResponseStatusException.class, () -> lobbyService.leaveLobby(testLobby.getLobbyId(), testUser2 ));
    }

    @Test
    public void leaveLobby_noToken() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(null);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        assertThrows(ResponseStatusException.class, () -> lobbyService.leaveLobby(testLobby.getLobbyId(), testUser ));
    }


    @Test
    public void joinSpecificLobby_alreadyPlaying() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.PLAYING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.leaveLobby(testLobby.getLobbyId(), testUser ));


    }

    @Test
    public void joinSpecificLobby_succesful1() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.PLAYING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        lobbyService.leaveLobby(testLobby.getLobbyId(), testUser);

        assertEquals(null, testLobby.getAdmin());


    }

    @Test
    public void joinSpecificLobby_succesful2() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);

        testLobby.addPlayerToPlayersInLobby(testUser2);



        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        lobbyService.leaveLobby(testLobby.getLobbyId(), testUser2);

        assertEquals(PlayerStatus.LEFT, testUser2.getPlayerStatus());


    }

    @Test
    public void joinSpecificLobby_succesful3() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);

        testLobby.addPlayerToPlayersInLobby(testUser2);



        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        lobbyService.leaveLobby(testLobby.getLobbyId(), testUser);

        assertEquals(PlayerStatus.FINISHED, testUser2.getPlayerStatus());

    }

    @Test
    public void joinSpecificLobby_succesful4() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser2);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(3);
        testLobby2.setLobbyStatus(LobbyStatus.FULL);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);
        testLobby2.addPlayerToPlayersInLobby(testUser2);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);

        lobbyService.leaveLobby(testLobby2.getLobbyId(), testUser);

        assertEquals(LobbyStatus.WAITING, testLobby2.getLobbyStatus());


    }

    @Test
    public void makePlayerReady_NoToken() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(1);
        testLobby2.setLobbyStatus(LobbyStatus.PLAYING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(null);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        assertThrows(ResponseStatusException.class, () -> lobbyService.makePlayerReady(testLobby.getLobbyId(), testUser));
    }

    @Test
    public void makePlayerReady_NoLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(1);
        testLobby2.setLobbyStatus(LobbyStatus.PLAYING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> lobbyService.makePlayerReady(testLobby.getLobbyId(), testUser));
    }

    @Test
    public void makePlayerReady_notInLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        assertThrows(ResponseStatusException.class, () -> lobbyService.makePlayerReady(testLobby.getLobbyId(), testUser2 ));
    }

    @Test
    public void makePlayerReady_successful() {
        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        lobbyService.makePlayerReady(testLobby.getLobbyId(), testUser);

        assertEquals(PlayerStatus.READY, testUser.getPlayerStatus());
    }

    @Test
    public void kickPlayer_notInLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.kickPlayer(testLobby.getLobbyId(), "Test", testUser2 ));
    }

    @Test
    public void kickPlayer_noLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(null);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.kickPlayer(testLobby.getLobbyId(), "Test", testUser2 ));
    }

    @Test
    public void kickPlayer_noToken() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(null);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.kickPlayer(testLobby.getLobbyId(), "Test", testUser2 ));
    }

    @Test
    public void kickPlayer_notAdmin() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);

        testLobby.addPlayerToPlayersInLobby(testUser2);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.kickPlayer(testLobby.getLobbyId(), "Test", testUser2 ));
    }

    @Test
    public void kickPlayer_EmptyLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(1);
        testLobby2.setLobbyStatus(LobbyStatus.FULL);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser);

        lobbyService.kickPlayer(testLobby.getLobbyId(), "TEst", testUser);

        assertEquals(PlayerStatus.LEFT, testUser.getPlayerStatus());
    }

    @Test
    public void areAllReady_noLobby() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);

        testLobby.addPlayerToPlayersInLobby(testUser2);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(null);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.areAllReady(testLobby.getLobbyId()));
    }

    @Test
    public void areAllReady_successfulFalse() {

        List<User> testList = new ArrayList<>();
        testList.add(testUser);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.READY);
        testUser2.setScore(1);

        testLobby.addPlayerToPlayersInLobby(testUser2);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser2);

        boolean areReady = lobbyService.areAllReady(testLobby.getLobbyId());

        assertEquals(false, areReady);
    }

    @Test
    public void areAllReady_successfulTrue() {

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.READY);
        testUser2.setScore(1);

        List<User> testList = new ArrayList<>();
        testList.add(testUser2);

        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser2);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(4);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser2);

        boolean areReady = lobbyService.areAllReady(testLobby.getLobbyId());

        assertEquals(true, areReady);
    }

    @Test
    public void startGame_NoLobby() {

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> lobbyService.startGame(testLobby.getLobbyId(), testUser));

    }

    @Test
    public void startGame_NoToken() {

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(null);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        assertThrows(ResponseStatusException.class, () -> lobbyService.startGame(testLobby.getLobbyId(), testUser));

    }

    @Test
    public void startGame_notInLobby() {

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        assertThrows(ResponseStatusException.class, () -> lobbyService.startGame(testLobby.getLobbyId(), testUser2 ));
    }

    @Test
    public void startGame_notEnoughPLayers() {
        List<User> testList = new ArrayList<>();
        testList.add(testUser);
        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(1);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);



        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        assertThrows(ResponseStatusException.class, () -> lobbyService.startGame(testLobby.getLobbyId(), testUser ));
    }

    @Test
    public void startGame_notAdmin() {

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);

        List<User> testList = new ArrayList<>();
        testList.add(testUser);
        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(2);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);
        testLobby2.addPlayerToPlayersInLobby(testUser2);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.startGame(testLobby.getLobbyId(), testUser ));
    }

    @Test
    public void startGame_notReady() {

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);

        List<User> testList = new ArrayList<>();
        testList.add(testUser);
        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(2);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);
        testLobby2.addPlayerToPlayersInLobby(testUser2);

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.startGame(testLobby.getLobbyId(), testUser ));
    }

    @Test
    public void startGame_successful() {

        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.READY);
        testUser2.setScore(1);

        List<User> testList = new ArrayList<>();
        testList.add(testUser2);
        Lobby testLobby2 = new Lobby();
        testLobby2.setAdmin(testUser2);
        testLobby2.setPlayersInLobby(testList);
        testLobby2.setLobbyId(2L);
        testLobby2.setLobbyName("TestName2");
        testLobby2.setNumbersOfPlayers(2);
        testLobby2.setLobbyStatus(LobbyStatus.WAITING);
        testLobby2.setAllAreReadyForNextRound(false);
        testLobby2.setIsEndGame(false);


        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser2);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby2);

        Game game = lobbyService.startGame(testLobby.getLobbyId(), testUser);
        assertEquals(testLobby2.getLobbyName(), game.getGameName());
    }




}
