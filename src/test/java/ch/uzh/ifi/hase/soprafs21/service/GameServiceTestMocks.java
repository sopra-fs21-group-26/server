package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;
import ch.uzh.ifi.hase.soprafs21.entity.User;
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
    private Game testGame;
    private List<Picture> picturesOnGrid;
    private Picture picture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

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

        picture = new Picture();
        picture.setId(1L);
        picture.setCoordinate("T1");
        picture.setUrl("testURL");
        picturesOnGrid = new ArrayList<>();
        picturesOnGrid.add(picture);

        testGame = new Game(testLobby.getLobbyId(), testList);
        testGame.setGameName("testGame");
        testGame.setPicturesonGrid(picturesOnGrid);
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


}
