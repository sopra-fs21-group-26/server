package ch.uzh.ifi.hase.soprafs21.TestsForReport;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;
import ch.uzh.ifi.hase.soprafs21.entity.ScoreSheet;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
import ch.uzh.ifi.hase.soprafs21.service.LobbyService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
//test if, when guess is correct, points evenly distributed
//--> mock two users, mock lobby and game, mock picture of usertobeguessed
//--> make the guess and assertequal points
//create game (add scoresheet), tobeguesseduser (save picture in entity user), loggedinuser --->mock them s.t repos return them
//pass user with user.username = tobeguessed.username, user.token = loggedin.token
//pass coordinate from tobeguessed picture

//UNITTEST FOR PRESENTATION
public class UnitTestPointsDistributed {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private GameService gameService;

    @InjectMocks
    private LobbyService lobbyService;

    private User loggedInUser;

    private User userToBeGuessed;

    private Game game;

    private Picture recreation;

    private User userToBePassed;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

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

        userToBeGuessed = new User();
        userToBeGuessed = new User();
        userToBeGuessed.setId(2L);
        userToBeGuessed.setUsername("testUsername2");
        userToBeGuessed.setToken("2");
        userToBeGuessed.setOnlineStatus(OnlineStatus.ONLINE);
        userToBeGuessed.setPassword("TestPassword2");
        userToBeGuessed.setCreatedOn();
        //testUser.setCurrentlyCreating("TestCurrentlyCreating");
        userToBeGuessed.setGuessedOtherPicturesCorrectly(0);
        userToBeGuessed.setOwnPicturesCorrectlyGuessed(0);
        userToBeGuessed.setPlayerStatus(PlayerStatus.PLAYING);
        userToBeGuessed.setScore(0);
        recreation = new Picture();
        recreation.setCoordinate("A1");
        userToBeGuessed.setCurrentlyCreating(recreation);
        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(userToBeGuessed);
        playersInGame.add(loggedInUser);
        game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        userToBePassed = new User();
        userToBePassed.setToken(loggedInUser.getToken());
        userToBePassed.setUsername(userToBeGuessed.getUsername());
        userToBePassed.setToken("3");
        userToBePassed.setId(3L);
        userToBePassed.setPassword("testPassword3");
        userToBePassed.setPlayerStatus(PlayerStatus.PLAYING);
        userToBePassed.setPoints(0);
        userToBePassed.setCreatedOn();

        Mockito.when(gameRepository.findByGameId(Mockito.anyLong())).thenReturn(game);
        Mockito.when(userRepository.findByToken(Mockito.any())).thenReturn(loggedInUser);
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(userToBeGuessed);

    }

    @Test
    public void pointsEvenly_distributed(){
        gameService.checkIfGuessCorrect(1, "A1", userToBePassed);

        Mockito.verify(gameRepository, Mockito.times(1)).findByGameId(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1)).findByToken(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.anyString());
        assertEquals(2, userToBeGuessed.getPoints());
        assertEquals(1, loggedInUser.getPoints());

    }
}
