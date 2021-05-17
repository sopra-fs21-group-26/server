package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


import java.util.ArrayList;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g., UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);


    //Tested
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    //Tested
    @Mapping(source = "token", target = "token")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "onlineStatus", target = "status")
    @Mapping(source = "playerStatus", target = "playerStatus")
    UserGetDTO convertEntityToUserGetDTO(User user);

    //Tested
    @Mapping(source = "username", target = "username")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "gamesWon", target = "gamesWon")
    @Mapping(source = "gamesPlayed", target = "gamesPlayed")
    UserGetProfileDTO convertEntityToUserGetProfileDTO(User user);

    //Tested
    @Mapping(source = "token", target = "token")
    User convertUserPutTokenDTOtoEntity(UserPutTokenDTO userPutTokenDTO);

    //Tested
    @Mapping(source= "id", target = "id")
    @Mapping(source = "username", target = "username")
    //@Mapping(source = "score", target = "score")
    UserGetScoreboardDTO convertEntityToUserGetScoreboardDTO(User user);

    //Tested
    @Mapping(source = "scoreSheet", target = "scoreSheet")
    GameGetScoreSheetDTO convertEntityToGameGetScoreSheetDTO(ScoreSheet scoreSheet);

    /*@Mapping(source = "setList", target = "setList")
    GameGetSetsDTO convertEntityToGameGetSetsDTO(SetList setList);*/

    //Tested
    @Mapping(source = "token", target = "token")
    @Mapping(source = "id", target = "id")
    UserPutTokenIdDTO convertEntityToUserPutTokenIdDTO(User user);

    //Tested
    @Mapping(source = "token", target = "token")
    @Mapping(source = "username", target = "username")
    User convertUserPutTokenUsernameDTOtoEntity(UserPutTokenUsernameDTO userPutTokenUsernameDTO);

    //Tested
    @Mapping(source = "url", target = "url")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "coordinate", target = "coordinate")
    PictureGetDTO convertEntityToPictureGetDTO(Picture picture);

    //Tested
    @Mapping(source = "usernames", target = "usernames")
    @Mapping(source = "recreatedPictures", target = "recreatedPictures")
    GuessScreenGetDTO convertEntityToGuessScreenGetDTO(GuessScreen guessScreen);

    //Mappings for Lobby:

   /* @Mapping(source = "lobbyName", target = "lobbyName")
    @Mapping(source = "numbersOfPlayers", target = "numbersOfPlayers")
    @Mapping(source = "admin", target = "admin")
    @Mapping(source = "playersInLobby", target = "playersInLobby")
    LobbyGetDTO convertEntityToLobbyGetDTO(Lobby lobby);*/

    /*@Mapping(source = "allLobbies", target = "allLobbies")
    LobbyGetAllLobbiesDTO convertEntityToLobbyGetAllLobbiesDTO(ArrayList<Lobby> allLobbies);*/

}
