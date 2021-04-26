package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Lobby;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.LobbyDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
import ch.uzh.ifi.hase.soprafs21.service.LobbyService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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

<<<<<<< Updated upstream

=======
    @GetMapping("/games/{gameID}/score")
    @ResponseStatus(HttpStatus.OK) //Corresponding to REST Specification
    @ResponseBody
    public GameGetScoreSheetDTO getScoreSheet(@PathVariable long gameID) throws IOException, ParseException {
        Game game = gameService.getGame(gameID);
        ScoreSheet scoreSheet = game.getScoreSheet();
        return DTOMapper.INSTANCE.convertEntityToGameGetScoreSheetDTO(scoreSheet);
>>>>>>> Stashed changes
    }
}
