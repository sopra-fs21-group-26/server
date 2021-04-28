package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
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
        //user.setCurrentlyCreating("TestCurrentlyCreating");
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
}
