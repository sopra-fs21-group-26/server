package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.server.ResponseStatusException;

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

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private LobbyService lobbyService;

    private Lobby testLobby;
    private User testUser;
    private Game testGame;

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

        Mockito.when(userRepository.findByToken(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);

        testGame = lobbyService.startGame(testLobby.getLobbyId(), testUser);

        //Mockito.when(lobbyRepository.findByLobbyId(Mockito.anyLong())).thenReturn(testLobby);
        //Mockito.when(lobbyRepository.findByLobbyName(Mockito.anyString())).thenReturn(testLobby);
    }
}
