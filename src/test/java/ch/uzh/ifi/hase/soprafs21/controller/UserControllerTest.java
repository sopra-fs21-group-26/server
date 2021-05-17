package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenUsernameDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
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
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setScore(0);

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(userService.getSortedUsers()).willReturn(allUsers);

        // when
        MockHttpServletRequestBuilder getRequest = get("/players/leaderboard").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].score", is(user.getScore())));
    }

    @Test
    public void createUser_validInput_userCreated() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setOnlineStatus(OnlineStatus.ONLINE);
        user.setPassword("TestPassword");
        user.setCreatedOn();
        //user.setCurrentlyCreating("TestCurrentlyCreating");
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getOnlineStatus().toString())));
    }

    @Test
    public void getSearchedUser_ValidInput() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setOnlineStatus(OnlineStatus.ONLINE);
        user.setPassword("TestPassword");
        user.setCreatedOn();
        //user.setCurrentlyCreating("TestCurrentlyCreating");
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);

        given(userService.checkIfUsernameExists(Mockito.any())).willReturn(user);

        MockHttpServletRequestBuilder getRequest = get("/players/search/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user.getUsername()));

        mockMvc.perform(getRequest)
                .andExpect(status().isOk());
    }


    @Test
    public void getSingleUser_ValidInput() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setOnlineStatus(OnlineStatus.ONLINE);
        user.setPassword("TestPassword");
        user.setCreatedOn();
        //user.setCurrentlyCreating("TestCurrentlyCreating");
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);

        given(userService.getSingleUser(Mockito.anyLong())).willReturn(user);

        MockHttpServletRequestBuilder getRequest = get("/players/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(user.getScore())));

    }

    @Test
    public void logoutUser_ValidInput() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setOnlineStatus(OnlineStatus.ONLINE);
        user.setPassword("TestPassword");
        user.setCreatedOn();
        //user.setCurrentlyCreating("TestCurrentlyCreating");
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken("test");

        MockHttpServletRequestBuilder putRequest = put("/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());

    }

    @Test
    public void loginUser_ValidInput() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setOnlineStatus(OnlineStatus.ONLINE);
        user.setPassword("TestPassword");
        user.setCreatedOn();
        //user.setCurrentlyCreating("TestCurrentlyCreating");
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("TestPassword");
        userPostDTO.setUsername("TestUsername");

        MockHttpServletRequestBuilder putRequest = put("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
    }


    @Test
    public void checkEditPermission_PermissionGranted() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setOnlineStatus(OnlineStatus.ONLINE);
        user.setPassword("TestPassword");
        user.setCreatedOn();
        //user.setCurrentlyCreating("TestCurrentlyCreating");
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);

        given(userService.checkEditPermission(Mockito.anyString(), Mockito.anyLong())).willReturn(true);


        MockHttpServletRequestBuilder putRequest = put("/edit/" + user.getToken() + "/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());


    }

    @Test
    public void checkEditPermission_NoPermission() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setOnlineStatus(OnlineStatus.ONLINE);
        user.setPassword("TestPassword");
        user.setCreatedOn();
        //user.setCurrentlyCreating("TestCurrentlyCreating");
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);

        given(userService.checkEditPermission(Mockito.anyString(), Mockito.anyLong())).willReturn(false);


        MockHttpServletRequestBuilder putRequest = put("/edit/" + user.getToken() + "/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());

    }


    @Test
    public void checkEditUser_ValidInput() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setOnlineStatus(OnlineStatus.ONLINE);
        user.setPassword("TestPassword");
        user.setCreatedOn();
        //user.setCurrentlyCreating("TestCurrentlyCreating");
        user.setGuessedOtherPicturesCorrectly(1);
        user.setOwnPicturesCorrectlyGuessed(1);
        user.setPlayerStatus(PlayerStatus.FINISHED);
        user.setScore(1);

        UserPutTokenUsernameDTO userPutTokenUsernameDTO = new UserPutTokenUsernameDTO();
        userPutTokenUsernameDTO.setToken("Test");
        userPutTokenUsernameDTO.setUsername("TestUsernane");

        given(userService.editUser(Mockito.any(User.class))).willReturn(user);

        MockHttpServletRequestBuilder putRequest = put("/edit/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenUsernameDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk());




    }















    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The request body could not be created.%s", e.toString()));
        }
    }
}