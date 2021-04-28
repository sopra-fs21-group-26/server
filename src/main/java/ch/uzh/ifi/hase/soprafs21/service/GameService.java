package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final UserRepository userRepository;


    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
                       @Qualifier("userRepository") UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }


    public List<Picture> getGrid() throws IOException, ParseException {
        String StringURL = "https://api.unsplash.com/photos/random/?count=16&client_id=3Sgz4djxGEDDUR3CiS6xSx_MKnU8PIYCdQOR8AkEHis";
        URL url = new URL(StringURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        List<Picture> PictureList = new ArrayList<>();
        Scanner sc = new Scanner(url.openStream());
        String inline = new String();
        while (sc.hasNext()) {
            inline += sc.nextLine();
        }
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(inline);
        JSONArray jarray = new JSONArray();
        jarray.add(obj);
        JSONArray UnsplashArray = (JSONArray) jarray.get(0);
        for (int i = 0; i < UnsplashArray.size(); i++) {
            JSONObject obj1 = (JSONObject) UnsplashArray.get(i);
            JSONObject imgURLs = (JSONObject) obj1.get("urls");
            PictureList.add(new Picture((String) imgURLs.get("small"), i));
        }

        return PictureList;
    }

    public Game getGame(long id) {
        Optional<Game> game = Optional.ofNullable(gameRepository.findByGameId(id));
        if (!game.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found!");
        }
        return game.get();
    }

    public Picture getRandomPicture(User user, long gameId) {
        Optional<User> ToAssignUser = Optional.ofNullable(userRepository.findByToken(user.getToken()));
        if (!ToAssignUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        User userToAssignCoordinate = ToAssignUser.get();
        Optional<Game> getGame = Optional.ofNullable(gameRepository.findByGameId(gameId));
        if (!getGame.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found!");
        }
        Game game = getGame.get();

        List<Picture> pictures = game.getPicturesonGrid();
        Random rand = new Random();

        Picture randomPicture = pictures.get(rand.nextInt(pictures.size()));
        userToAssignCoordinate.setOwnPictureCoordinate(randomPicture.getCoordinate());
        return randomPicture;
    }

    public GuessScreen getGuessScreen(long gameId, User user) {
        Game game = gameRepository.findByGameId(gameId);
        GuessScreen guessScreen = new GuessScreen();
        List<User> users = game.getPlayersInGame();
        List<String> pictures = new ArrayList<>();
        List<String> userNames = new ArrayList<>();
        for (User user1 : users) {
            if (!user1.getToken().equals(user.getToken())) {
                userNames.add(user1.getUsername());
                pictures.add(user1.getRecreatedPicture().getUrl());
            }
        }
        guessScreen.setRecreatedPictures(pictures);
        guessScreen.setUsernames(userNames);
        return guessScreen;
    }

    public void checkIfGuessCorrect(long gameId, String coordinate, User user){
        Game game = gameRepository.findByGameId(gameId);
        User loggedInUser = userRepository.findByToken(user.getToken());
        User toBeGuessed = userRepository.findByUsername(user.getUsername());

        if (toBeGuessed.getCurrentlyCreating().getCoordinate().equals(coordinate)){
            loggedInUser.setGuessedOtherPicturesCorrectly(loggedInUser.getGuessedOtherPicturesCorrectly()+1);
            toBeGuessed.setOwnPicturesCorrectlyGuessed(toBeGuessed.getOwnPicturesCorrectlyGuessed()+1);

            loggedInUser.setPoints(loggedInUser.calculatePoints());
            loggedInUser.setScore(loggedInUser.getScore() + loggedInUser.calculatePoints());
            toBeGuessed.setPoints(toBeGuessed.calculatePoints());
            toBeGuessed.setScore(toBeGuessed.getScore() + toBeGuessed.calculatePoints());

            userRepository.save(loggedInUser);
            userRepository.save(toBeGuessed);
            userRepository.flush();

            ScoreSheet scoreSheet = new ScoreSheet(game.getPlayersInGame());
        }

    }






}

