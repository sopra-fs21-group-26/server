package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DTOMapper.class})
public interface LobbyDTOMapper {

    LobbyDTOMapper INSTANCE = Mappers.getMapper(LobbyDTOMapper.class);

    @Mapping(source = "lobbyName", target = "lobbyName")
    @Mapping(source = "numbersOfPlayers", target = "numbersOfPlayers")
    @Mapping(source = "admin", target = "admin")
    @Mapping(source = "playersInLobby", target = "playersInLobby")
    @Mapping(source = "lobbyId", target = "lobbyId")
    LobbyGetDTO convertEntityToLobbyGetDTO(Lobby lobby);

}
