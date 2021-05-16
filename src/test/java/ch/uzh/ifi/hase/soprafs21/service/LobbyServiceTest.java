package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.constant.*;
import ch.uzh.ifi.hase.soprafs21.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyServiceTest {
    @Mock
    private LobbyRepository lobbyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private LobbyService lobbyService;
    @InjectMocks
    private GameService gameService;
    @MockBean
    private UserService userService;

    private Lobby lobby;
    private User testPlayer;
    private User testPlayer2;
    private User testPlayer3;

    @Test
    public void removePlayerFromLobby_endLobby() {
        lobby.setLobbyStatus(LobbyStatus.PLAYING);
        lobby.setLobbyId(1L);
        List<User> players = new ArrayList<User>(){{
            add(testPlayer);
            add(testPlayer2);
            add(testPlayer3);
        }};

        Game game = new Game(1L, players);
        // test with three players and end the game
        lobbyService.kickPlayer(lobby.getLobbyId(), testPlayer.getUsername(), testPlayer);

        assertEquals(lobby.getAdmin().getId(), testPlayer2.getId());
    }
}
