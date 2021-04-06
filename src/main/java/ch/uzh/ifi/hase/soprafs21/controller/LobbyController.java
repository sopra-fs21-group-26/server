package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LobbyController {
    private final LobbyService lobbyService;

    LobbyController(LobbyService lobbyService) { this.lobbyService = lobbyService; }

    //Lobby Mappings:


    // gets lobbyname and token of creator:
    // --> create lobby in userService: set lobbyname, set admin via token,
    // add admin in player list via token, set playerstatus, lobbystatus, set nr of players to 1
    // return lobbygetdto s.t players in lobby can be shown on lobbyscreen
    @PostMapping("/lobby/create/{lobbyName}")
    @ResponseStatus(HttpStatus.CREATED) //Corresponding to REST Specification
    @ResponseBody
    public LobbyGetDTO createLobby(@PathVariable String lobbyName, @RequestBody UserPutTokenDTO userPutTokenDTO){
        User admin = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        Lobby createdLobby = lobbyService.createLobby(lobbyName, admin);
        return DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(createdLobby);
    }



}
