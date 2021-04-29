package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    private final GameService gameService;
    private  final  UserService userService;

    @Autowired
    GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }




    @GetMapping("/games/{gameID}/grid")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public List<PictureGetDTO> getGrid(@PathVariable long gameID) throws IOException, ParseException {
            List<Picture> Grid = gameService.getGrid(gameID);
            List<PictureGetDTO> Pictures = new ArrayList<>();
            for(int i = 0;i<Grid.size();i++){
                Pictures.add(DTOMapper.INSTANCE.convertEntityToPictureGetDTO(Grid.get(i)));
            }
            return Pictures;

    }
/*    @GetMapping("/game/grid")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PictureGetDTO> testGetGrid(){
        List<Picture> testGrid = gameService.makeGrid;
    }*/

    @GetMapping("/games/{gameID}/score")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public GameGetScoreSheetDTO getScoreSheet(@PathVariable long gameID) throws IOException, ParseException {
        Game game = gameService.getGame(gameID);
        ScoreSheet scoreSheet = game.getScoreSheet();
        return DTOMapper.INSTANCE.convertEntityToGameGetScoreSheetDTO(scoreSheet);
    }


    //returns PictureGetDTO of random picture
    @GetMapping("/games/{gameId}/picture")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public PictureGetDTO getRandomPicture(@RequestBody UserPutTokenDTO userPutTokenDTO, @PathVariable long gameId) {
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        Picture picture = gameService.getRandomPicture(user, gameId);
        return DTOMapper.INSTANCE.convertEntityToPictureGetDTO(picture);
    }

    @PutMapping("/games/points/{playerID}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public int addPoint(@PathVariable long playerID) throws IOException, ParseException {
        return userService.addPoint(playerID);
    }

    //returns guessScreenGetDTO of all players in lobby except "yourself" (list with username and list with string of recreated picture)
    @PutMapping("/games/guessScreen/{gameId}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public GuessScreenGetDTO getGuessScreen(@PathVariable long gameId, @RequestBody UserPutTokenDTO userPutTokenDTO) {
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        GuessScreen guessScreen = gameService.getGuessScreen(gameId, user);
        return DTOMapper.INSTANCE.convertEntityToGuessScreenGetDTO(guessScreen);
    }

    //adds the points to the user corresponding to his guess, need: token of logged in user and username that he guessed picture of
    @PutMapping("/games/correctGuess/{gameId}/{coordinate}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public void checkIfGuessCorrect(@PathVariable long gameId, @PathVariable String coordinate, @RequestBody UserPutTokenUsernameDTO userPutTokenUsernameDTO) {
        User user = DTOMapper.INSTANCE.convertUserPutTokenUsernameDTOtoEntity(userPutTokenUsernameDTO);
        gameService.checkIfGuessCorrect(gameId, coordinate, user);
    }

    //save recreated picture as string in user.recreatedPicture.url --> after recreating: this request first to save
    //then games/creation to set hascreated = true --> then get to see if all havecreated their picture --> if yes continue

    @PutMapping("games/saveCreation/{recreation}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public void saveCreation(@RequestBody UserPutTokenDTO userPutTokenDTO, @PathVariable String recreation){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        gameService.saveCreation(user, recreation);
    }








    //after creating sets hascreated=true to see that he is ready for guessScreen
    @PutMapping("games/creation")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public void setHasCreated(@RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        userService.setHasCreated(user);
    }

    //returns true if all users have created picture to continue to guessscreen
    @GetMapping("games/allCreated/{gameId}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public boolean haveAllCreated(@PathVariable long gameId){
        boolean haveAllCreated = gameService.haveAllCreated(gameId);
        return haveAllCreated;
    }


    //sets hasguessed = true if user has guessed all guesses to continue to next round
    @PutMapping("games/guess")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public void setHasGuessed(@RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        userService.setHasGuessed(user);
    }


    //returns true if all users have guessed all guesses to continue to next round
    @GetMapping("games/allGuessed/{gameId}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public boolean haveAllGuessed(@PathVariable long gameId){
        boolean haveAllGuessed = gameService.haveAllGuessed(gameId);
        return haveAllGuessed;
    }





    //To do: return true false if it was last round (one Mapping)
    //To do:
}
