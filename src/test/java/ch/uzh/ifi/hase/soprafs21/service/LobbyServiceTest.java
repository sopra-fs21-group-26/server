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

    @Qualifier("lobbyRepository")
    @Autowired
    private LobbyRepository lobbyRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LobbyService lobbyService;

    @BeforeEach
    public void setup() {
        lobbyRepository.deleteAll();
    }


//    @Test
//    public void createLobby_validInputs_success(){
//        Lobby lobbyTest = new Lobby();
//        User admin = new User();
//        admin.setCreatedOn();
//        admin.setUsername("TestUsername");
//        admin.setToken("TestToken");
//        admin.setOnlineStatus(OnlineStatus.ONLINE);
//        admin.setPassword("TestPassword");
//        admin.setPoints(0);
//        List<User> users = new ArrayList<>();
//        users.add(admin);
//        userRepository.save(admin);
//
//        lobbyTest.setPlayersInLobby(users);
//        lobbyTest.setLobbyId((long) 2);
//        lobbyTest.setAdmin(admin);
//        lobbyTest.setLobbyStatus(LobbyStatus.WAITING);
//        lobbyTest.setNumbersOfPlayers(1);
//        lobbyTest.setLobbyName("TestLobby");
//
//        Lobby createdLobby = lobbyService.createLobby(lobbyTest.getLobbyName(), admin);
//
//        assertEquals(lobbyTest.getLobbyId(), createdLobby.getLobbyId());
      /*  assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(OnlineStatus.ONLINE, createdUser.getOnlineStatus());*/

}
