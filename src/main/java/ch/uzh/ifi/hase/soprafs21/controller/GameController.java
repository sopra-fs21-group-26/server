package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;
import ch.uzh.ifi.hase.soprafs21.entity.ScoreSheet;
import ch.uzh.ifi.hase.soprafs21.rest.dto.GameGetScoreSheetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.PictureGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPutTokenDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class GameController {
    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games/{gameID}/grid")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public List<Picture> getGrid(@PathVariable long gameID) throws IOException, ParseException {
        Game game = gameService.getGame(gameID);
        if (game.getPicturesonGrid().size() > 1) {
            return game.getPicturesonGrid();
        }
        else {
            List<Picture> Grid = gameService.getGrid();
            game.setPicturesonGrid(Grid);
            return Grid;
        }
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






}
