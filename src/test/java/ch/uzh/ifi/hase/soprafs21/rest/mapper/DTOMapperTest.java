package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */
public class DTOMapperTest {
    @Test
    public void testCreateUser_fromUserPostDTO_toUser_success() {
        // create UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("username");

        // MAP -> Create user
        User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // check content
        assertEquals(userPostDTO.getUsername(), user.getUsername());
    }

    @Test
    public void testGetUser_fromUser_toUserGetDTO_success() {
        // create User
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");

        // MAP -> Create UserGetDTO
        UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

        // check content
        assertEquals(user.getId(), userGetDTO.getId());
        assertEquals(user.getUsername(), userGetDTO.getUsername());
        assertEquals(user.getOnlineStatus(), userGetDTO.getStatus());
    }


    //Rest Interface Test
    @Test
    public void testGetLOBBY_fromLobby_toLobbyGetDTO_success() {
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");
        Lobby lobby = new Lobby();
        lobby.setLobbyName("TestLobby");
        lobby.setAdmin(user);
        List<User> list = new ArrayList<>();
        list.add(user);
        lobby.setPlayersInLobby(list);
        lobby.setNumbersOfPlayers(1);
        lobby.setAllAreReadyForNextRound(false);
        lobby.setIsEndGame(false);

        LobbyGetDTO lobbyGetDTO = LobbyDTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);

        assertEquals(lobby.getIsEndGame(), lobbyGetDTO.getIsEndGame());
    }

    @Test
    public void testGetUserProfileDTO_fromUser_toUserProfileGetDTO(){
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");
        user.setGamesWon(1);
        user.setScore(0);
        user.setGamesPlayed(1);

        UserGetProfileDTO userGetProfileDTO = DTOMapper.INSTANCE.convertEntityToUserGetProfileDTO(user);

        assertEquals(user.getUsername(), userGetProfileDTO.getUsername());
        assertEquals(user.getGamesPlayed(), user.getGamesPlayed());
        assertEquals(user.getGamesWon(), user.getGamesWon());
        assertEquals(user.getScore(), user.getScore());
    }

    @Test
    public void testPutUserTokenDTO_fromUserPutTokenDTO_toUser(){
        UserPutTokenDTO userPutTokenDTO = new UserPutTokenDTO();

        userPutTokenDTO.setToken("TestToken");

        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);

        assertEquals(userPutTokenDTO.getToken(), user.getToken());
    }

    @Test
    public void testGetScoreboardDTO_fromUser_toUserGetScoreboardDTO(){
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");
        user.setGamesWon(1);
        user.setScore(0);
        user.setGamesPlayed(1);
        user.setId((long) 1);

        UserGetScoreboardDTO userGetScoreboardDTO = DTOMapper.INSTANCE.convertEntityToUserGetScoreboardDTO(user);

        assertEquals(user.getId(), userGetScoreboardDTO.getId());
        assertEquals(user.getUsername(), userGetScoreboardDTO.getUsername());
    }

    @Test
    public void testGetScoreSheetdDTO_fromScoreSheet_toGetScoreSheetDTO(){
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");
        user.setGamesWon(1);
        user.setScore(0);
        user.setGamesPlayed(1);
        user.setId((long) 1);

        User user2 = new User();
        user2.setUsername("firstname@lastname2");
        user2.setOnlineStatus(OnlineStatus.OFFLINE);
        user2.setToken("2");
        user2.setGamesWon(1);
        user2.setScore(0);
        user2.setGamesPlayed(1);
        user2.setId((long) 1);

        List <User> players = new ArrayList<>();
        players.add(user);
        players.add(user2);

        ScoreSheet scoreSheet = new ScoreSheet(players);

        GameGetScoreSheetDTO gameGetScoreSheetDTO = DTOMapper.INSTANCE.convertEntityToGameGetScoreSheetDTO(scoreSheet);

        assertEquals(scoreSheet.getScoreSheet(), gameGetScoreSheetDTO.getScoreSheet());

    }

    @Test
    public void testPutTokenIdDTO_fromUser_toUserPutTokenIdDTO(){
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setOnlineStatus(OnlineStatus.OFFLINE);
        user.setToken("1");
        user.setGamesWon(1);
        user.setScore(0);
        user.setGamesPlayed(1);
        user.setId((long) 1);

        UserPutTokenIdDTO userPutTokenIdDTO = DTOMapper.INSTANCE.convertEntityToUserPutTokenIdDTO(user);

        assertEquals(user.getId(), userPutTokenIdDTO.getId());
        assertEquals(user.getToken(), userPutTokenIdDTO.getToken());

    }

    @Test
    public void testUserPutTokenUsernameDTO_fromUserPutTokenUsernameDTO_toUser(){
        UserPutTokenUsernameDTO userPutTokenUsernameDTO = new UserPutTokenUsernameDTO();
        userPutTokenUsernameDTO.setUsername("Test");
        userPutTokenUsernameDTO.setToken("TestToken");

        User user = DTOMapper.INSTANCE.convertUserPutTokenUsernameDTOtoEntity(userPutTokenUsernameDTO);

        assertEquals(userPutTokenUsernameDTO.getUsername(), user.getUsername());
        assertEquals(userPutTokenUsernameDTO.getToken(), user.getToken());

    }

    @Test
    public void testPictureGetDTO_fromPicture_toPictureGetDTO(){
        Picture picture = new Picture();
        picture.setUrl("TestURL");
        picture.setCoordinate("TestCoordinate");
        picture.setId((long) 1);

        PictureGetDTO pictureGetDTO = DTOMapper.INSTANCE.convertEntityToPictureGetDTO(picture);

        assertEquals(picture.getUrl(), pictureGetDTO.getUrl());
        assertEquals(picture.getCoordinate(), pictureGetDTO.getCoordinate());
        assertEquals(picture.getId(), pictureGetDTO.getId());
    }

    @Test
    public void testGuessScreenGetDTO_fromGuessScreen_toGUessScreenGetDTO(){
        List<String> usernames = new ArrayList<>();
        List <String> recreatedPictures = new ArrayList<>();
        GuessScreen guessScreen = new GuessScreen();

        usernames.add("Test1");
        usernames.add("Test2");
        usernames.add("Test3");

        recreatedPictures.add("Test4");
        recreatedPictures.add("Test5");
        recreatedPictures.add("Test6");

        guessScreen.setUsernames(usernames);
        guessScreen.setRecreatedPictures(recreatedPictures);

        GuessScreenGetDTO guessScreenGetDTO = DTOMapper.INSTANCE.convertEntityToGuessScreenGetDTO(guessScreen);

        assertEquals(guessScreen.getRecreatedPictures(),guessScreenGetDTO.getRecreatedPictures());
        assertEquals(guessScreen.getUsernames(), guessScreenGetDTO.getUsernames());
    }






}