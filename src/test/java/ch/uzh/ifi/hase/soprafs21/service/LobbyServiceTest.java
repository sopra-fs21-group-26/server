package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
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
/*@WebAppConfiguration
@SpringBootTest*/
public class LobbyServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LobbyRepository lobbyRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private LobbyService lobbyService;

    private User testUser;

    private Lobby testLobby;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUsername");
        testUser.setToken("1");
        testUser.setOnlineStatus(OnlineStatus.ONLINE);
        testUser.setPassword("TestPassword");
        testUser.setCreatedOn();
        //testUser.setCurrentlyCreating("TestCurrentlyCreating");
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

        List<Lobby> testLobbyList = new ArrayList<>();
        testLobbyList.add(testLobby);


        // when -> any object is being save in the userRepository -> return the dummy testUser
        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.save(Mockito.any())).thenReturn(testLobby);
        Mockito.when(lobbyRepository.findAll()).thenReturn(testLobbyList);

    }


    @Test
    public void createLobby_validInputs_success() {
        Lobby createdLobby = lobbyService.createLobby("TestName1", testUser);

        Mockito.verify(userRepository, Mockito.times(1)).findByToken(Mockito.any());
        Mockito.verify(lobbyRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testLobby.getLobbyId(), createdLobby.getLobbyId());
        assertEquals(testLobby.getAdmin(), createdLobby.getAdmin());
        assertEquals(testLobby.getNumbersOfPlayers(), createdLobby.getNumbersOfPlayers());
        assertEquals(testLobby.getPlayersInLobby(), createdLobby.getPlayersInLobby());
        assertEquals(testLobby.getIsEndGame(), createdLobby.getIsEndGame());
        assertEquals(testLobby.getLobbyStatus(), createdLobby.getLobbyStatus());
        assertEquals(testLobby.isAllAreReadyForNextRound(), createdLobby.isAllAreReadyForNextRound());



    }
}
        /*Lobby lobbyTest = new Lobby();
        User admin = new User();
        admin.setCreatedOn();
        admin.setUsername("TestUsername");
        admin.setToken("TestToken");
        admin.setOnlineStatus(OnlineStatus.ONLINE);
        admin.setPassword("TestPassword");
        admin.setPoints(0);
        List<User> users = new ArrayList<>();
        users.add(admin);
        userRepository.save(admin);

        lobbyTest.setPlayersInLobby(users);
        lobbyTest.setLobbyId((long) 2);
        lobbyTest.setAdmin(admin);
        lobbyTest.setLobbyStatus(LobbyStatus.WAITING);
        lobbyTest.setNumbersOfPlayers(1);
        lobbyTest.setLobbyName("TestLobby");

        Lobby createdLobby = lobbyService.createLobby(lobbyTest.getLobbyName(), admin);

        assertEquals(lobbyTest.getLobbyId(), createdLobby.getLobbyId());*/
      /*  assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(OnlineStatus.ONLINE, createdUser.getOnlineStatus());*/


