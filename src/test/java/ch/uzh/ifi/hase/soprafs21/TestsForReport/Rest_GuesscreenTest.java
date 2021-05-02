package ch.uzh.ifi.hase.soprafs21.TestsForReport;



import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.controller.GameController;
import ch.uzh.ifi.hase.soprafs21.controller.UserController;
import ch.uzh.ifi.hase.soprafs21.entity.GuessScreen;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenDTO;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(GameController.class)
public class Rest_GuesscreenTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameservice;

    @MockBean
    private UserService userService;

    @MockBean
    private GameController gameController;

    //Rest test for presentation
    @Test
    public void correctGuessScreen() throws Exception {

        GuessScreen guessScreen = new GuessScreen();
        List<String> usernames = new ArrayList<>();
        List<String> pictures = new ArrayList<>();
        usernames.add("Test1");
        pictures.add("Test1");
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(usernames);

        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();
        userPutTokenDTO.setToken("TestToken");

        given(gameservice.getGuessScreen(Mockito.anyLong(), Mockito.any())).willReturn(guessScreen);

        MockHttpServletRequestBuilder putRequest =
                MockMvcRequestBuilders.put("/games/guessScreen/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutTokenDTO));

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
