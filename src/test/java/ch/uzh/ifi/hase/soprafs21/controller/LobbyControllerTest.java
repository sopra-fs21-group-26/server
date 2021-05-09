package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LobbyController.class)
public class LobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private GameService gameService;

    @MockBean
    private LobbyService lobbyService;

    @Test
    public void givenLobby_joinLobby() throws Exception {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setScore(0);

        Lobby lobby = new Lobby();
        lobby.setLobbyName("test");
        lobby.setLobbyId(1000L);
        lobby.join(user);

        // this mocks the LobbyService -> we define above what the lobbyService should return
        given(lobbyService.getSingleLobbyById(1000L)).willReturn(lobby);

        MockHttpServletRequestBuilder putRequest = put("/lobbies/1000/join");

        // then
        mockMvc.perform(putRequest).andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void givenUserInLobby_leaveLobby() throws Exception {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setScore(0);

        Lobby lobby = new Lobby();
        lobby.setLobbyName("test");
        lobby.setLobbyId(1000L);
        lobby.setAdmin(user);
        lobby.join(user);
        lobby.deletePlayerInPlayersInLobby(user);

        // this mocks the LobbyService -> we define above what the lobbyService should return
        given(lobbyService.getSingleLobbyById(1000L)).willReturn(lobby);

        MockHttpServletRequestBuilder putRequest = put("/lobbies/1000/leave");

        // then
        mockMvc.perform(putRequest).andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void givenUserInLobby_terminateLobby_userIsAdmin() throws Exception {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setScore(0);

        Lobby lobby = new Lobby();
        lobby.setLobbyName("test");
        lobby.setLobbyId(1000L);
        lobby.setAdmin(user);
        lobby.join(user);
        lobby.setUpPlayerLeaveAdmin(user);

        // this mocks the LobbyService -> we define above what the lobbyService should return
        given(lobbyService.getSingleLobbyById(1000L)).willReturn(lobby);

        MockHttpServletRequestBuilder putRequest = put("/lobbies/1000/terminate");

        // then
        mockMvc.perform(putRequest).andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void givenUserInLobby_terminateLobby_userNotAdmin() throws Exception {
        // given
        User admin = new User();
        admin.setUsername("firstname@admin");
        admin.setScore(0);

        User user = new User();
        user.setUsername("firstname@lastname");
        user.setScore(0);

        Lobby lobby = new Lobby();
        lobby.setLobbyName("test");
        lobby.setLobbyId(1000L);
        lobby.setAdmin(admin);
        lobby.join(admin);
        lobby.join(user);
        lobby.setUpPlayerLeaveNoAdmin(user);

        // this mocks the LobbyService -> we define above what the lobbyService should return
        given(lobbyService.getSingleLobbyById(1000L)).willReturn(lobby);

        MockHttpServletRequestBuilder putRequest = put("/lobbies/1000/terminate");

        // then
        mockMvc.perform(putRequest).andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }



    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     *
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