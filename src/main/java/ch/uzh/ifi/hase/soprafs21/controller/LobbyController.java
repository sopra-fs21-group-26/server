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
    //Tested
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
    //tested
    @GetMapping("/lobby/join")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public List<LobbyGetDTO> getAllAvailableLobbies(){
 //       User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        List<Lobby> allAvailableLobbies = lobbyService.getAllAvailableLobbies();
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
    //Tested
    @PutMapping("/lobby/join/{lobbyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Corresponding to REST Specification
    @ResponseBody
    public LobbyGetDTO joinSpecificLobby(@PathVariable long lobbyId, @RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        Lobby lobby = lobbyService.joinSpecificLobby(user, lobbyId);
        return LobbyDTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);
    }

    //tested
    @GetMapping("lobby/{lobbyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyGetDTO getSingleLobbyById(@PathVariable long lobbyId){
        Lobby lobby = lobbyService.getSingleLobbyById(lobbyId);
        return LobbyDTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);
    }

    //tested
    @PutMapping("lobby/leave/{lobbyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void leaveLobby(@PathVariable long lobbyId, @RequestBody UserPutTokenDTO userPutTokenDTO){
        User userToLeave = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        lobbyService.leaveLobby(lobbyId, userToLeave);
    }

    //tested
    @PutMapping("lobby/ready/{lobbyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void makePlayerReady(@PathVariable long lobbyId, @RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        lobbyService.makePlayerReady(lobbyId, user);
    }

    //Tested
    @PutMapping("lobby/kick/{lobbyId}/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void kickPlayer(@PathVariable long lobbyId, @PathVariable String username, @RequestBody UserPutTokenDTO userPutTokenDTO){
        User userWhoWantsToKick = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        lobbyService.kickPlayer(lobbyId, username, userWhoWantsToKick);
    }

    @GetMapping("/game/allReady/{lobbyId}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public boolean areAllReady(@PathVariable long lobbyId){
        boolean areAllReady = lobbyService.areAllReady(lobbyId);
        return areAllReady;
    }



    @PutMapping("lobby/start/{lobbyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void startGame(@PathVariable long lobbyId, @RequestBody UserPutTokenDTO userPutTokenDTO){
        User userWhoWantsToStart = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        lobbyService.startGame(lobbyId, userWhoWantsToStart);
    }



}
