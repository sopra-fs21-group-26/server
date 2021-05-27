package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

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

        // when -> any object is being save in the userRepository -> return the dummy testUser
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
    }

    @Test
    public void createUser_validInputs_success() {
        // when -> any object is being save in the userRepository -> return the dummy testUser
        User createdUser = userService.createUser(testUser);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUser.getId(), createdUser.getId());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(OnlineStatus.ONLINE, createdUser.getOnlineStatus());
    }

    /*@Test
    public void createUser_duplicateName_throwsException() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);

        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
    }*/

    @Test
    public void createUser_duplicateInputs_throwsException() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
    }

    @Test
    public void getSortedUserListTest() {
        // given -> a first user has already been created
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
        testUser2.setScore(5);

        userRepository.saveAndFlush(testUser);
        userRepository.saveAndFlush(testUser2);

        List<User> userList = new ArrayList<>();
        userList.add(testUser2);
        userList.add(testUser);


        // when -> setup additional mocks for UserRepository
        Mockito.when(userService.getSortedUsers()).thenReturn(userList);

        // then -> attempt to create second user with same user -> check that an error is thrown
        assert(userList.get(0).getScore() > userList.get(1).getScore());
    }

    @Test
    public void getUserListTest() {
        // given -> a first user has already been created
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

        userRepository.saveAndFlush(testUser);
        userRepository.saveAndFlush(testUser2);

        List<User> userList = new ArrayList<>();
        userList.add(testUser);
        userList.add(testUser2);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userService.getUsers()).thenReturn(userList);

        // then -> attempt to create second user with same user -> check that an error is thrown
        assert(userList.size()>0);
    }
    @Test
    public void bubbleSortTest() {
        // given -> a first user has already been created
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
        testUser2.setScore(5);

        userRepository.saveAndFlush(testUser);
        userRepository.saveAndFlush(testUser2);

        List<User> userList = new ArrayList<>();
        userList.add(testUser2);
        userList.add(testUser);


        userService.bubbleSort(userList);

        // then -> attempt to create second user with same user -> check that an error is thrown
        assert(userList.get(1).getScore() > userList.get(0).getScore());
    }

    @Test
    public void userLogout_success() {
        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        userService.logoutUser(testUser);
        // then -> attempt to create second user with same user -> check that an error is thrown
        assertEquals(testUser.getOnlineStatus(), OnlineStatus.OFFLINE);
    }

    @Test
    public void userLogout_fail() {

        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.logoutUser(testUser));
    }

    @Test
    public void userLoginStatus_success() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser);
        userService.checkLogin(testUser);
        assertEquals(testUser.getOnlineStatus(), OnlineStatus.ONLINE);
    }

    @Test
    public void userLoginStatus_noUser() {
        assertThrows(ResponseStatusException.class, () -> userService.checkLogin(testUser));
    }
    @Test
    public void userLoginStatus_wrongPassword() {
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
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser2);
        assertThrows(ResponseStatusException.class, () -> userService.checkLogin(testUser));
    }

    @Test
    public void getSingleUserID_fail() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testUser));
        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.getSingleUser(testUser.getId()));
    }

    @Test
    public void getSingleUserID_success() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(testUser);
        User test = userService.getSingleUser(testUser.getId());
        assertEquals(testUser, test);
    }

    @Test
    public void getSingleUserToken_success() {
        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        User test = userService.getSingleUserByToken(testUser.getToken());
        assertEquals(testUser, test);
    }

    @Test
    public void getSingleUserToken_fail() {
        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> userService.getSingleUserByToken(testUser.getToken()));
    }

    @Test
    public void checkUsername_success() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser);
        User test = userService.checkIfUsernameExists(testUser.getUsername());
        assertEquals(testUser, test);
    }

    @Test
    public void checkUsername_fail() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> userService.checkIfUsernameExists(testUser.getUsername()));
    }


}
