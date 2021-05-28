package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs21.repository.PictureRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class GameServiceTestMocks {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private LobbyService lobbyService;

    @InjectMocks
    private GameService gameService;

    private Lobby testLobby;
    private User testUser;
    private User testUser2;
    private Game testGame;
    private List<Picture> picturesOnGrid;
    private Picture picture1;
    private Picture picture2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        picture1 = new Picture();
        picture1.setId(1L);
        picture1.setCoordinate("T1");
        picture1.setUrl("testURL");
        picturesOnGrid = new ArrayList<>();
        picturesOnGrid.add(picture1);

        picture2 = new Picture();
        picture2.setId(1L);
        picture2.setCoordinate("T1");
        picture2.setUrl("testURL");
        picturesOnGrid = new ArrayList<>();
        picturesOnGrid.add(picture2);

        testUser = new User();
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
        testUser.setRecreatedPicture(picture1);

        testUser2 = new User();
        testUser2.setId(1L);
        testUser2.setUsername("testUsername2");
        testUser2.setToken("2");
        testUser2.setOnlineStatus(OnlineStatus.ONLINE);
        testUser2.setPassword("TestPassword2");
        testUser2.setCreatedOn();
        testUser2.setGuessedOtherPicturesCorrectly(1);
        testUser2.setOwnPicturesCorrectlyGuessed(1);
        testUser2.setPlayerStatus(PlayerStatus.FINISHED);
        testUser2.setScore(1);
        testUser2.setRecreatedPicture(picture2);

        List<User> testList = new ArrayList<>();
        testList.add(testUser);
        testList.add(testUser2);

        testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        testGame = new Game(testLobby.getLobbyId(), testList);
        testGame.setGameName("testGame");
        testGame.setPicturesonGrid(picturesOnGrid);
        testGame.setPlayersInGame(testList);
    }

    @Test
    public void returngame_successful(){
        Mockito.when(gameRepository.findByGameId(Mockito.anyLong())).thenReturn(testGame);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);
        Game game = gameService.getGame(testLobby.getLobbyId());

        assertEquals(testLobby.getLobbyId(), game.getGameId());
    }

    @Test
    public void returngame_notSuccessful(){
        Mockito.when(gameRepository.findByGameId(Mockito.anyLong())).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> gameService.getGame(testLobby.getLobbyId()));
    }

    @Test
    public void returnGrid_successful() throws IOException, ParseException {
        Mockito.when(gameRepository.findByGameId(Mockito.anyLong())).thenReturn(testGame);
        List<Picture> grid = gameService.getGrid(testLobby.getLobbyId());

        assertEquals(picturesOnGrid.size(), grid.size());
    }

    @Test
    public void makeGrid_successful() throws IOException, ParseException {
        Mockito.when(gameRepository.findByGameId(Mockito.anyLong())).thenReturn(testGame);

        List<Picture> grid = gameService.makeGrid(testLobby.getLobbyId());

        assertEquals(16, grid.size());
    }

    @Test
    public void getGuessScreen_successful() throws IOException, ParseException {
        Mockito.when(gameRepository.findByGameId(Mockito.anyLong())).thenReturn(testGame);

        GuessScreen guessScreen = gameService.getGuessScreen(testLobby.getLobbyId(), testUser);

        assertEquals(1, guessScreen.getRecreatedPictures().size());
    }


}
