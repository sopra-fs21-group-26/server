package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.*;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LobbyRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Qualifier("lobbyRepository")
    @MockBean
    private LobbyRepository lobbyRepository;


    /**
     * Tests the find ByUsername Method.
     * Should succeed
     */
    @Test
    void findByLobbyName_success() {
        // given
        Lobby lobby = createLobby("Testcase_Lobby");

        entityManager.persist(lobby);
        entityManager.flush();

        // when
        Lobby found = lobbyRepository.findByLobbyName(lobby.getLobbyName());

        // then
        assertNotNull(found);
        assertNotNull(found.getLobbyId());
        assertEquals(found.getLobbyName(), lobby.getLobbyName());
    }

    /**
     * Tests the findByLobbyname Method.
     * Should not find lobby and assertNull
     */
    @Test
    void findByLobbyName_fail() {
        // given
        Lobby lobby = createLobby("Testcase_Lobby");

        entityManager.persist(lobby);
        entityManager.flush();

        // when
        Lobby found = lobbyRepository.findByLobbyName("Testcase_lobbynotfound");

        // then
        assertNull(found);
    }

    /**
     * Tests find Lobby By ID method
     * Should find correct lobby
     */
    @Test
    void findByCreator_success() {
        User user = createUser("testUser", "1");
        User player = new User();
        entityManager.persist(player);
        // given
        Lobby lobby = createLobby("Testcase_Lobby");
        Long id = lobby.getLobbyId();

        entityManager.persist(lobby);
        entityManager.flush();

        // when
        Lobby found = lobbyRepository.findByLobbyId(id);

        // then
        assertNotNull(found);
        assertNotNull(found.getLobbyId());
        assertEquals(found.getLobbyName(), lobby.getLobbyName());
    }

    /**
     * Tests find Lobby By Token method
     * Should not find lobby and assertNull
     */
    @Test
    void findById_fail() {
        // given
        Lobby lobby = createLobby("Testcase_Lobby");


        entityManager.persist(lobby);
        entityManager.flush();

        // when
        Lobby found = lobbyRepository.findByLobbyId(5L);

        // then
        assertNull(found);
    }

    private User createUser(String userName, String token) {
        User user = new User();
        user.setId(Long.valueOf(token));
        user.setUsername(userName);
        user.setToken(token);
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        return user;
    }

    private Lobby createLobby(String lobbyName) {

        Lobby lobby = new Lobby();
        lobby.setLobbyName(lobbyName);
        return lobby;
    }

}
