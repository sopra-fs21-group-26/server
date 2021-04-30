/*
package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenUsernameDTO;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
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
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

*/
/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST request without actually sending them over the network.
 * This tests if the UserController works.
 *//*

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameservice;

    @Test
    public void givenTwoUsersCheckIfPointsCorrectlyDistributed() throws Exception{
        User loggedInUser = new User();
        loggedInUser.setToken("Z");
        User userToBeGuessed = new User();
        userToBeGuessed.setUsername("Test");
        Picture currentlyCreating = new Picture();
        currentlyCreating.setCoordinate("A1");
        userToBeGuessed.setCurrentlyCreating(currentlyCreating);
        List <User> users = new ArrayList<>();
        users.add(loggedInUser);
        users.add(userToBeGuessed);
        Lobby lobby = new Lobby();
        lobby.setLobbyId((long) 2);
        lobby.setPlayersInLobby(users);
        Game game = new Game(lobby.getLobbyId(), lobby.getPlayersInLobby());
        UserPutTokenUsernameDTO userPutTokenUsernameDTO = new UserPutTokenUsernameDTO();
        userPutTokenUsernameDTO.setToken(loggedInUser.getToken());
        userPutTokenUsernameDTO.setUsername(userToBeGuessed.getUsername());
        MockHttpServletRequestBuilder putRequest = put("/games/correctGuess/2/A1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenUsernameDTO));

        mockMvc.perform(putRequest)
                .andExpect(1, is(loggedInUser.getPoints()));
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
*/
