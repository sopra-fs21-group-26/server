package ch.uzh.ifi.hase.soprafs21.controller;


import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenUsernameDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
import ch.uzh.ifi.hase.soprafs21.service.LobbyService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LobbyService lobbyService;

    @MockBean
    private GameService gameService;

    @Test
    public void getGridStatus_successful() throws Exception{

        User loggedInUser = new User();
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

        User userToBeGuessed = new User();
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
        Picture recreation = new Picture();
        recreation.setCoordinate("A1");
        userToBeGuessed.setCurrentlyCreating(recreation);
        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(userToBeGuessed);
        playersInGame.add(loggedInUser);
        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        given(gameService.getGame(Mockito.anyLong())).willReturn(game);

        MockHttpServletRequestBuilder getRequest = get("/games/" + game.getGameId() + "/grid/status")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    public void getGridStatus_true() throws Exception{

        User loggedInUser = new User();
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

        User userToBeGuessed = new User();
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
        Picture recreation = new Picture();
        recreation.setCoordinate("A1");
        userToBeGuessed.setCurrentlyCreating(recreation);
        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(userToBeGuessed);
        playersInGame.add(loggedInUser);
        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(true);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        given(gameService.getGame(Mockito.anyLong())).willReturn(game);

        MockHttpServletRequestBuilder getRequest = get("/games/" + game.getGameId() + "/grid/status")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }


    @Test
    public void getGrid_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.getGrid(Mockito.anyLong())).willReturn(grid);

        MockHttpServletRequestBuilder getRequest = get("/games/" + game.getGameId() + "/grid")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void makeGrid_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.makeGrid(Mockito.anyLong())).willReturn(grid);

        MockHttpServletRequestBuilder getRequest = get("/games/" + game.getGameId() + "/grid/make")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void getScoresheet_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.getGame(Mockito.anyLong())).willReturn(game);

        MockHttpServletRequestBuilder getRequest = get("/games/" + game.getGameId() + "/score")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scoreSheet", aMapWithSize(1)));

    }

    @Test
    public void getRandomPicture_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.getRandomPicture(Mockito.any(User.class), Mockito.anyLong())).willReturn(recreation);

        MockHttpServletRequestBuilder putRequest = put("/games/" + game.getGameId() + "/picture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is(recreation.getUrl())));

    }

    @Test
    public void getPictureOfUser_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.getPictureOfUser(Mockito.any(User.class))).willReturn(recreation);

        MockHttpServletRequestBuilder putRequest = put("/games/pictures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is(recreation.getUrl())));

    }

    @Test
    public void getGuessScreen_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.getGuessScreen(Mockito.anyLong(), Mockito.any(User.class))).willReturn(guessScreen);

        MockHttpServletRequestBuilder putRequest = put("/games/guessScreen/" + game.getGameId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usernames[0]", is(guessScreen.getUsernames().get(0))));

    }


    @Test
    public void checkIfGuessCorrect_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenUsernameDTO userPutTokenUsernameDTO = new UserPutTokenUsernameDTO();
        userPutTokenUsernameDTO.setToken(loggedInUser.getToken());
        userPutTokenUsernameDTO.setUsername(loggedInUser.getUsername());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);



        MockHttpServletRequestBuilder putRequest = put("/games/correctGuess/" + game.getGameId() + "/" + recreation.getCoordinate())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenUsernameDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void setHasCreated_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);



        MockHttpServletRequestBuilder putRequest = put("/games/creation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void haveAllCreated_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.haveAllCreated(Mockito.anyLong())).willReturn(true);

        MockHttpServletRequestBuilder putRequest = get("/games/allCreated/" + game.getGameId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));

    }

    @Test
    public void setHasGuessed_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);



        MockHttpServletRequestBuilder putRequest = put("/games/guess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void haveAllGuessed_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.haveAllGuessed(Mockito.anyLong())).willReturn(true);

        MockHttpServletRequestBuilder putRequest = get("/games/allGuessed/" + game.getGameId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));

    }

    @Test
    public void readyForNextRound_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.haveAllCreated(Mockito.anyLong())).willReturn(true);

        MockHttpServletRequestBuilder putRequest = put("/games/" + game.getGameId() + "/ready-for-next-round")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void prepareForNextRound_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);


        MockHttpServletRequestBuilder putRequest = put("/games/preparation-next-round/" + game.getGameId())
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(putRequest)
                .andExpect(status().isOk());


    }


    @Test
    public void endGame_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);


        MockHttpServletRequestBuilder putRequest = put("/games/" + game.getGameId() + "/end-game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());


    }

    @Test
    public void isGrid_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        given(gameService.hasGrid(Mockito.anyLong())).willReturn(true);

        MockHttpServletRequestBuilder putRequest = put("/games/" + game.getGameId() + "/is-grid")
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));

    }


    @Test
    public void saveCreation_successful() throws Exception{
        User loggedInUser = new User();
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
        recreation.setCoordinate("A1");
        recreation.setUrl("TestUrl");

        List<User> playersInGame = new ArrayList<>();
        playersInGame.add(loggedInUser);

        Game game = new Game();
        game.setPlayersInGame(playersInGame);
        game.setGameId((long) 1);
        game.setGridStatus(false);
        ScoreSheet scoreSheet = new ScoreSheet(playersInGame);
        game.setScoreSheet(scoreSheet);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(loggedInUser.getToken());

        List<String> pictures = new ArrayList<>();
        pictures.add(recreation.getUrl());
        List<String> usernames = new ArrayList<>();
        usernames.add(loggedInUser.getUsername());
        GuessScreen guessScreen = new GuessScreen();
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);


        List <Picture> grid = new ArrayList<>();
        grid.add(recreation);

        Map<String, String> map = new HashMap<>();
        map.put("token", "Test");


        given(userService.getSingleUserByToken(Mockito.anyString())).willReturn(loggedInUser);

        MockHttpServletRequestBuilder putRequest = put("/games/saveCreation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(map));


        mockMvc.perform(putRequest)
                .andExpect(status().isOk());

    }




    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The request body could not be created.%s", e.toString()));
        }
    }






}

