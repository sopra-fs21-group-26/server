package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserGetProfileDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution to the UserService and finally return the result.
 */
@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }
    // 1. (backend) return getprofileviewdto
    // --> Elo score, games played in total (also user), games won (also user), username -->done
    // 2. logout/login --> front und backend
    @GetMapping("/players/{id}")
    @ResponseStatus(HttpStatus.OK) // 200 (as to REST specifications)
    @ResponseBody
    public UserGetProfileDTO getSinglePlayer(@PathVariable long id){

        User user = userService.getSingleUser(id);

        return DTOMapper.INSTANCE.convertEntityToUserGetProfileDTO(user);
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user
        User createdUser = userService.createUser(userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }

    @PutMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT) //corresponding to REST Specification Status
    @ResponseBody
    public void logoutUser(@RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        userService.logoutUser(user);
    }

    @PutMapping("/login")
    @ResponseStatus(HttpStatus.OK) //corresponding to REST Specification Status
    @ResponseBody
    public UserPutTokenDTO loginUser(@RequestBody UserPostDTO userPostDTO){
        User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        User checkedUser = userService.checkLogin(user);
        return DTOMapper.INSTANCE.convertEntityToUserPutTokenDTO(checkedUser);
    }




}
