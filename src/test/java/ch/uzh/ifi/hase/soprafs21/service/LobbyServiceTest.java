package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.service.LobbyService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class LobbyServiceTest {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("lobbyRepository")
    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LobbyService lobbyService;

    private Lobby testLobby;
    private User testUser;

    @BeforeEach
    public void setup() {

    }

   /* @Test
    public void createLobby_validInputs_success() {
        // given



        User testUser = new User();
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
        userRepository.save(testUser);
        userRepository.flush();

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        List<Lobby> testLobbyList = new ArrayList<>();
        testLobbyList.add(testLobby);


        // when
        Lobby createdLobby = lobbyService.createLobby("TestName", testUser);

        // then
        assertEquals(testLobby.getLobbyStatus(), createdLobby.getLobbyStatus());
        assertEquals(testLobby.getIsEndGame(),createdLobby.getIsEndGame());
        assertEquals(testLobby.getNumbersOfPlayers(), createdLobby.getNumbersOfPlayers());
        assertEquals(testLobby.getLobbyName(), createdLobby.getLobbyName());
        assertEquals(testLobby.isAllAreReadyForNextRound(), createdLobby.isAllAreReadyForNextRound());

    }*/

    @Test
    public void createLobby_noAdmin() {

        User testUser = new User();
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
        userRepository.save(testUser);
        userRepository.flush();

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        User testUser2 = new User();


        // when
        //Lobby createdLobby = lobbyService.createLobby("TestName",testUser2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.createLobby("Testlobby",testUser2));

    }

    @Test
    public void createLobby_alreadyPlaying() {

        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUsername");
        testUser.setToken("1");
        testUser.setOnlineStatus(OnlineStatus.ONLINE);
        testUser.setPassword("TestPassword");
        testUser.setCreatedOn();
        testUser.setGuessedOtherPicturesCorrectly(1);
        testUser.setOwnPicturesCorrectlyGuessed(1);
        testUser.setPlayerStatus(PlayerStatus.PLAYING);
        testUser.setScore(1);
        userRepository.save(testUser);
        userRepository.flush();

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


       /* Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        List<Lobby> testLobbyList = new ArrayList<>();
        testLobbyList.add(testLobby);*/

        User testUser2 = new User();


        // when
        //Lobby createdLobby = lobbyService.createLobby("TestName",testUser2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.createLobby("Testlobby",testUser));

    }

    @Test
    public void createLobby_duplicatelobbyName() {

        User testUser = new User();
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
        userRepository.save(testUser);
        userRepository.flush();

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        lobbyRepository.save(testLobby);
        lobbyRepository.flush();

        List<Lobby> testLobbyList = new ArrayList<>();
        testLobbyList.add(testLobby);

        User testUser2 = new User();
        testUser2.setToken("Token2");




        // when
        //Lobby createdLobby = lobbyService.createLobby("TestName",testUser2);

        assertThrows(ResponseStatusException.class, () -> lobbyService.createLobby("TestName",testUser));

    }

    @Test
    public void getAllAvailableLobbies_succesful() {

        User testUser = new User();
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
        userRepository.save(testUser);
        userRepository.flush();

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        lobbyRepository.save(testLobby);
        lobbyRepository.flush();

        List<Lobby> testLobbyList = new ArrayList<>();
        testLobbyList.add(testLobby);



        User testUser2 = new User();
        testUser2.setToken("Token2");


        List<Lobby> allLobbies = lobbyService.getAllAvailableLobbies();


        // when
        //Lobby createdLobby = lobbyService.createLobby("TestName",testUser2);

        assertEquals(1, allLobbies.size());

    }

    @Test
    public void joinSpecificLobby_noToken() {

        User testUser = new User();
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
        userRepository.save(testUser);
        userRepository.flush();

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        lobbyRepository.save(testLobby);
        lobbyRepository.flush();

        User testUser2 = new User();
        testUser2.setToken("Token2");
        testUser2.setId(2L);
        testUser2.setUsername("testUsername2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);

        assertThrows(ResponseStatusException.class, () -> lobbyService.joinSpecificLobby(testUser2 , testLobby.getLobbyId()));
    }
}
