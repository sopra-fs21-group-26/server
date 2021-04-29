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
import ch.uzh.ifi.hase.soprafs21.constant.Set;

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

    @GetMapping("/games/{gameID}/score")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public GameGetScoreSheetDTO getScoreSheet(@PathVariable long gameID) throws IOException, ParseException {
        Game game = gameService.getGame(gameID);
        ScoreSheet scoreSheet = game.getScoreSheet();
        return DTOMapper.INSTANCE.convertEntityToGameGetScoreSheetDTO(scoreSheet);
    }

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

    @PutMapping("/games/guessScreen/{gameId}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public GuessScreenGetDTO getGuessScreen(@PathVariable long gameId, @RequestBody UserPutTokenDTO userPutTokenDTO) {
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        GuessScreen guessScreen = gameService.getGuessScreen(gameId, user);
        return DTOMapper.INSTANCE.convertEntityToGuessScreenGetDTO(guessScreen);
    }

    @PutMapping("/games/correctGuess/{gameId}/{coordinate}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public void checkIfGuessCorrect(@PathVariable long gameId, @PathVariable String coordinate, @RequestBody UserPutTokenUsernameDTO userPutTokenUsernameDTO) {
        User user = DTOMapper.INSTANCE.convertUserPutTokenUsernameDTOtoEntity(userPutTokenUsernameDTO);
        gameService.checkIfGuessCorrect(gameId, coordinate, user);
    }

    @PutMapping("games/creation")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public void setHasCreated(@RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        userService.setHasCreated(user);
    }

    @GetMapping("games/allCreated/{gameId}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public boolean haveAllCreated(@PathVariable long gameId){
        boolean haveAllCreated = gameService.haveAllCreated(gameId);
        return haveAllCreated;
    }

    @PutMapping("games/guess")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public void setHasGuessed(@RequestBody UserPutTokenDTO userPutTokenDTO){
        User user = DTOMapper.INSTANCE.convertUserPutTokenDTOtoEntity(userPutTokenDTO);
        userService.setHasGuessed(user);
    }

    @GetMapping("games/allGuessed/{gameId}")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public boolean haveAllGuessed(@PathVariable long gameId){
        boolean haveAllGuessed = gameService.haveAllGuessed(gameId);
        return haveAllGuessed;
    }

    @GetMapping("/games/{gameID}/sets-rotation")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public GameGetSetsDTO rotateSets(@PathVariable long gameID) throws IOException, ParseException {
        Game game = gameService.getGame(gameID);
        SetList sets = game.getSetList();
        sets.rotateSetList();
        return DTOMapper.INSTANCE.convertEntityToGameGetSetsDTO(sets);
    }


    //To do: return true false if it was last round (one Mapping)
    //To do:
}
