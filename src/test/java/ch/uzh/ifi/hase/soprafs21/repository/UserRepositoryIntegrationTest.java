package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findByUserName_success() {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");
        user.setPassword("TestPassword");
        user.setCreatedOn();
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);



        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getOnlineStatus(), user.getOnlineStatus());
    }

    /**
     * Tests the findByUsername Method.
     * Should not find user and assertNull
     */
    @Test
    void findByUsername_fail() {
        // given a user
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setPassword("pw");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername("testname");

        // then
        assertNull(found);
    }



    /**
     * Tests find User By Token method
     * Should find correct user
     */
    @Test
    void findByToken_success() {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setPassword("pw");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByToken(user.getToken());

        // then
        assertNotNull(found);
        assertNotNull(found.getId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getOnlineStatus(), user.getOnlineStatus());
        assertEquals(found.getPassword(), user.getPassword());
    }

    /**
     * Tests find User By Token method
     * Should not find user and assertNull
     */
    @Test
    void findByToken_fail() {
        // given a user
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setPassword("pw");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByToken("2");

        // then
        assertNull(found);
    }

    /**
     * Tests find User By Token method
     * Should not find user and assertNull
     */
    @Test
    void findById_fail() {
        // given a user
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setPassword("pw");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findById(5L);

        // then
        assertNull(found);
    }
}
