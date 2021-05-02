package ch.uzh.ifi.hase.soprafs21.TestsForReport;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTestSaveRecreation {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private GameService gameService;

    private User loggedInUser;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        loggedInUser = new User();
        loggedInUser = new User();
        loggedInUser.setId(1L);
        loggedInUser.setUsername("testUsername1");
        loggedInUser.setToken("1");
        loggedInUser.setOnlineStatus(OnlineStatus.ONLINE);
        loggedInUser.setPassword("TestPassword1");
        loggedInUser.setCreatedOn();
        //testUser.setCurrentlyCreating("TestCurrentlyCreating");
        loggedInUser.setGuessedOtherPicturesCorrectly(0);
        loggedInUser.setOwnPicturesCorrectlyGuessed(0);
        loggedInUser.setPlayerStatus(PlayerStatus.PLAYING);
        loggedInUser.setScore(0);

        Picture recreation = new Picture();
        recreation.setUrl("TestUrl");

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(loggedInUser);
    }


    //Unit Test for save of recreation
    @Test
    public void successful_recreationSave(){
        gameService.saveCreation(loggedInUser, "TestUrl");

        Mockito.verify(userRepository, Mockito.times(1)).findByToken(Mockito.anyString());

        assertEquals(loggedInUser.getRecreatedPicture().getUrl(), "TestUrl");
    }


}
