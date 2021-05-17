package ch.uzh.ifi.hase.soprafs21.controller;
import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(LobbyController.class)
public class LobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LobbyService lobbyService;


    @Test
    public void createLobby_successful() throws Exception{
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(testUser.getToken());

        given(lobbyService.createLobby(Mockito.any(), Mockito.any())).willReturn(testLobby);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/lobby/create/" + testLobby.getLobbyName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lobbyName", is(testLobby.getLobbyName())))
                .andExpect(jsonPath("$.numbersOfPlayers", is(testLobby.getNumbersOfPlayers())))
                .andExpect(jsonPath("$.allAreReadyForNextRound", is(testLobby.isAllAreReadyForNextRound())))
                .andExpect(jsonPath("$.isEndGame", is(testLobby.getIsEndGame())));
    }


    @Test
    public void getAllAvailableLobbies_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        User testUser2 = new User();
        testUser.setId(2L);
        testUser.setUsername("testUsername2");
        testUser.setToken("2");
        testUser.setOnlineStatus(OnlineStatus.ONLINE);
        testUser.setPassword("TestPassword2");
        testUser.setCreatedOn();
        //testUser.setCurrentlyCreating("TestCurrentlyCreating");
        testUser.setGuessedOtherPicturesCorrectly(1);
        testUser.setOwnPicturesCorrectlyGuessed(1);
        testUser.setPlayerStatus(PlayerStatus.FINISHED);
        testUser.setScore(1);

        List<User> testList2 = new ArrayList<>();
        testList2.add(testUser2);


        Lobby testLobby2 = new Lobby();
        testLobby.setAdmin(testUser2);
        testLobby.setPlayersInLobby(testList2);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName2");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        List<Lobby> availableLobbies = new ArrayList<>();
        availableLobbies.add(testLobby);
        availableLobbies.add(testLobby2);


        given(lobbyService.getAllAvailableLobbies()).willReturn(availableLobbies);


        MockHttpServletRequestBuilder getRequest = get("/lobby/join")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].lobbyName", is(testLobby.getLobbyName())))
                .andExpect(jsonPath("$[1].lobbyName", is(testLobby2.getLobbyName())));
    }


    @Test
    public void joinSpecificLobby_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(testUser.getToken());

        given(lobbyService.joinSpecificLobby(Mockito.any(User.class), Mockito.anyLong())).willReturn(testLobby);

        MockHttpServletRequestBuilder putRequest = put("/lobby/join/" + testLobby.getLobbyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.lobbyName", is(testLobby.getLobbyName())))
                .andExpect(jsonPath("$.numbersOfPlayers", is(testLobby.getNumbersOfPlayers())))
                .andExpect(jsonPath("$.allAreReadyForNextRound", is(testLobby.isAllAreReadyForNextRound())))
                .andExpect(jsonPath("$.isEndGame", is(testLobby.getIsEndGame())));
    }


    @Test
    public void getSingleLobby_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        given(lobbyService.getSingleLobbyById(Mockito.anyLong())).willReturn(testLobby);

        MockHttpServletRequestBuilder getRequest = get("/lobby/" + testLobby.getLobbyId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lobbyName", is(testLobby.getLobbyName())))
                .andExpect(jsonPath("$.numbersOfPlayers", is(testLobby.getNumbersOfPlayers())))
                .andExpect(jsonPath("$.allAreReadyForNextRound", is(testLobby.isAllAreReadyForNextRound())))
                .andExpect(jsonPath("$.isEndGame", is(testLobby.getIsEndGame())));

    }


    @Test
    public void leaveLobby_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(testUser.getToken());

        MockHttpServletRequestBuilder putRequest = put("/lobby/leave/" + testLobby.getLobbyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());

    }

    @Test
    public void makePLayerReady_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(testUser.getToken());

        MockHttpServletRequestBuilder putRequest = put("/lobby/ready/" + testLobby.getLobbyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());

    }

    @Test
    public void kickPlayer_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(testUser.getToken());

        MockHttpServletRequestBuilder putRequest = put("/lobby/kick/" + testLobby.getLobbyId() + "/" + testUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());

    }

    @Test
    public void allAreReady_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        given(lobbyService.areAllReady(Mockito.anyLong())).willReturn(true);

        MockHttpServletRequestBuilder getRequest = get("/game/allReady/" + testLobby.getLobbyId())
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(getRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void startGame_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken(testUser.getToken());

        MockHttpServletRequestBuilder putRequest = put("/lobby/start/" + testLobby.getLobbyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());


    }



    @Test
    public void searchLobby_successful() throws Exception {
        User testUser = new User();
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

        List<User> testList = new ArrayList<>();
        testList.add(testUser);


        Lobby testLobby = new Lobby();
        testLobby.setAdmin(testUser);
        testLobby.setPlayersInLobby(testList);
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("TestName");
        testLobby.setNumbersOfPlayers(1);
        testLobby.setLobbyStatus(LobbyStatus.WAITING);
        testLobby.setAllAreReadyForNextRound(false);
        testLobby.setIsEndGame(false);

        given(lobbyService.checkIfLobbynameExists(Mockito.anyString())).willReturn(testLobby);

        MockHttpServletRequestBuilder getRequest = get("/lobby/search/" + testLobby.getLobbyName())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
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
