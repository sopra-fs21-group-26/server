package ch.uzh.ifi.hase.soprafs21.TestsForReport;

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
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class LobbyServiceIntegrationTest {

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

    @BeforeEach
    public void setup() {

        userRepository.deleteAll();
        lobbyRepository.deleteAll();
    }

    @Test
    public void createLobby_validInputs_success() {
        // given



        User testUser = new User();
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
        Lobby createdLobby = lobbyService.createLobby("TestName", testLobby.getAdmin());

        // then
        assertEquals(testLobby.getLobbyStatus(), createdLobby.getLobbyStatus());
        assertEquals(testLobby.getIsEndGame(),createdLobby.getIsEndGame());
        assertEquals(testLobby.getNumbersOfPlayers(), createdLobby.getNumbersOfPlayers());
        assertEquals(testLobby.getLobbyName(), createdLobby.getLobbyName());
        assertEquals(testLobby.isAllAreReadyForNextRound(), createdLobby.isAllAreReadyForNextRound());

//        assertEquals(testUser.getUsername(), createdUser.getUsername());
//        assertNotNull(createdUser.getToken());
//        assertEquals(OnlineStatus.ONLINE, createdUser.getOnlineStatus());
    }
}