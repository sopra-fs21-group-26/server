package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.LobbyDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.LobbyService;
import org.springframework.data.util.Pair;
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
    //createdLobby:returns lobby and admin
    //-->
    @PostMapping("/lobby/create/{lobbyName}")
    @ResponseStatus(HttpStatus.CREATED) //Corresponding to REST Specification
    @ResponseBody
    public LobbyGetDTO createLobby(@PathVariable String lobbyName, @RequestBody UserPutTokenDTO userPutTokenDTO){
        User admin = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        Lobby lobby = lobbyService.createLobby(lobbyName, admin);
        return LobbyDTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);
    }

    //1. convert userputtokendto to entity
    //2. pass it to user service for authentification and return list of all available lobbies
    //3. return lobbygetalllobbiesdto to frontend such that can be shown
    @GetMapping("/lobby/join")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public List<LobbyGetDTO> getAllAvailableLobbies(@RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        List<Lobby> allAvailableLobbies = lobbyService.getAllAvailableLobbies(user);
        List<LobbyGetDTO> lobbyGetDTOS = new ArrayList<>();

        for (Lobby lobby : allAvailableLobbies){
            lobbyGetDTOS.add(LobbyDTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby));
        }
        return lobbyGetDTOS;
    }



    // get lobbyid and token for user to add
    // convert to User entity
    //pass lobbyid, and usertoken to userservice
    //userservice: check Exceptions
    //else: lobby: add to playerlist, increase nrOfPlayers, check if full (change status if yes)
    //return to controller and convert
    @PutMapping("/lobby/join/{lobbyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Corresponding to REST Specification
    @ResponseBody
    public LobbyGetDTO joinSpecificLobby(@PathVariable long lobbyId, @RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        Lobby lobby = lobbyService.joinSpecificLobby(user, lobbyId);
        return LobbyDTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);
    }

    @GetMapping("lobby/{lobbyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyGetDTO getSingleLobbyById(@PathVariable long lobbyId){
        Lobby lobby = lobbyService.getSingleLobbyById(lobbyId);
        return LobbyDTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);
    }


}
