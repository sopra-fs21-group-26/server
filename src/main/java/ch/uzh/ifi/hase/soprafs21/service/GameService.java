package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs21.repository.PictureRepository;
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
    private final PictureRepository pictureRepository;


    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
                       @Qualifier("userRepository") UserRepository userRepository,
                       @Qualifier("pictureRepository") PictureRepository pictureRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }


    public List<Picture> makeGrid(long gameID) throws IOException, ParseException {
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
            Picture temp = new Picture((String) imgURLs.get("raw"),i);
            PictureList.add(temp);
        }

        for (int i = 0; i < PictureList.size(); i++) {
            PictureList.get(i).setId((long) i+4);
            if(i<=3){
                if(i%4==0){
                    PictureList.get(i).setCoordinate("A1");
                }
                if(i%4==1){
                    PictureList.get(i).setCoordinate("A2");
                }
                if(i%4==2){
                    PictureList.get(i).setCoordinate("A3");
                }
                if(i%4==3){
                    PictureList.get(i).setCoordinate("A4");
                }
            }
            else if(i<=7){
                if(i%4==0){
                    PictureList.get(i).setCoordinate("B1");
                }
                if(i%4==1){
                    PictureList.get(i).setCoordinate("B2");
                }
                if(i%4==2){
                    PictureList.get(i).setCoordinate("B3");
                }
                if(i%4==3){
                    PictureList.get(i).setCoordinate("B4");
                }

            }
            else if(i<=11){
                if(i%4==0){
                    PictureList.get(i).setCoordinate("C1");
                }
                if(i%4==1){
                    PictureList.get(i).setCoordinate("C2");
                }
                if(i%4==2){
                    PictureList.get(i).setCoordinate("C3");
                }
                if(i%4==3){
                    PictureList.get(i).setCoordinate("C4");
                }

            }
            else{
                if(i%4==0){
                    PictureList.get(i).setCoordinate("D1");
                }
                if(i%4==1){
                    PictureList.get(i).setCoordinate("D2");
                }
                if(i%4==2){
                    PictureList.get(i).setCoordinate("D3");
                }
                if(i%4==3){
                    PictureList.get(i).setCoordinate("D4");
                }

            }


        }
        for (int i = 0; i < PictureList.size(); i++){
            pictureRepository.saveAndFlush(PictureList.get(i));
        }
        Game game = gameRepository.findByGameId(gameID);
        game.setPicturesonGrid(PictureList);
        gameRepository.save(game);
        gameRepository.flush();

        return PictureList;
    }

    public Game getGame(long id) {
        Optional<Game> game = Optional.ofNullable(gameRepository.findByGameId(id));
        if (!game.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found!");
        }
        return game.get();
    }

    public List<Picture> getGrid(long gameID) throws IOException, ParseException {
        Game game = gameRepository.findByGameId(gameID);
        if(game.getPicturesonGrid()==null || game.getPicturesonGrid().isEmpty()){
            List<Picture> PictureList = makeGrid(gameID);
            return PictureList;
        }
        else{
            return game.getPicturesonGrid();
        }
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
        userToAssignCoordinate.setCurrentlyCreating(randomPicture);
        userToAssignCoordinate.setOwnPictureCoordinate(randomPicture.getCoordinate());
        return randomPicture;
    }

    public GuessScreen getGuessScreen(long gameId, User user) {
        Game game = gameRepository.findByGameId(gameId);
        GuessScreen guessScreen = new GuessScreen();
        List<User> users = game.getPlayersInGame();
        List<String> pictures = new ArrayList<>();
        List<String> userNames = new ArrayList<>();

        if (game == null){
            String baseErrorMessage = "Game was not found!";
            throw new ResponseStatusException(HttpStatus.CONFLICT, baseErrorMessage);
        }
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

        if (loggedInUser == null){
            String baseErrorMessage = "User with token was not found! You don't have access!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        if (toBeGuessed == null){
            String baseErrorMessage = "User you want to guess the pocture of was not found!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        if (game == null){
            String baseErrorMessage = "Game was not found!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

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

    public void saveCreation(User user, String recreation1){
        User userToSaveRecreation = userRepository.findByToken(user.getToken());

        if (userToSaveRecreation == null){
            String baseErrorMessage = "User with token was not found!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        Picture recreation = new Picture();
        recreation.setUrl(recreation1);
        userToSaveRecreation.setRecreatedPicture(recreation);
    }




    public boolean haveAllCreated(long gameId){
        Game game = gameRepository.findByGameId(gameId);
        boolean haveAllCreated;

        if (game == null){
            String baseErrorMessage = "Game was not found!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        List<User> allPlayersInGame = game.getPlayersInGame();
        for (User user : allPlayersInGame){
            if (!user.isHasCreated()){
                return haveAllCreated = false;
            }
        }
        return haveAllCreated = true;
    }

    public boolean haveAllGuessed(long gameId){
        Game game = gameRepository.findByGameId(gameId);
        boolean haveAllGuessed;

        if (game == null){
            String baseErrorMessage = "Game was not found!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        List <User> allPlayersInGame = game.getPlayersInGame();

        for (User user : allPlayersInGame){
            if (!user.isHasGuessed()){
                return haveAllGuessed = false;
            }
        }
        return haveAllGuessed = true;
    }


    /*public SetList rotateSets(Game game) {
        SetList sets = game.getSetList();
        game.rotateSets();
        gameRepository.save(game);
        gameRepository.flush();
        return sets;
    }*/

    //next round:
    public boolean isNextRound(long gameId){
        Game game = gameRepository.findByGameId(gameId);

        if (game.getGameRound() != 5){
            game.setAllPlayerStatusToPlaying();
            game.increaseGameRound();
            game.resetAllHasCreated();
            game.resetAllHasGuessed();
            gameRepository.save(game);
            gameRepository.flush();
            return true;
        }
        else{
            game.resetAllHasGuessed();
            game.resetAllHasCreated();
            game.setWinner();
            game.setAllPlayerStatusToFinished();
            gameRepository.save(game);
            gameRepository.flush();
            return false;
        }
    }


}

