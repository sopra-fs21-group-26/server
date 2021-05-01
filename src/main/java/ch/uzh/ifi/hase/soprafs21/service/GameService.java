package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs21.repository.LobbyRepository;
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
    private final LobbyRepository lobbyRepository;


    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository,
                       @Qualifier("userRepository") UserRepository userRepository,
                       @Qualifier("pictureRepository") PictureRepository pictureRepository,
                       @Qualifier("lobbyRepository") LobbyRepository lobbyRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.lobbyRepository = lobbyRepository;
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
            List<Picture> grid = game.getPicturesonGrid();
            return grid;
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
            game.setScoreSheet(scoreSheet);
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


    //Put Mapping, i get token, gameId, --> set user isreadyForNextRound to true --> check if all are ready for next round --> save in lobbyobj new variable, increase gameRound
    //--> is there a next round? (gameRound=6) --> save in lobby
    //--> next round yes: Put Mapping /prepareNextRound (i get gameId) -> set allPlayerStatus to playing, reset...

    public void readyForNextRound(User user, long gameId){
        User userToMakeReadyForNextRound = userRepository.findByToken(user.getToken());
        Game game = gameRepository.findByGameId(gameId);
        Lobby lobby = lobbyRepository.findByLobbyId(gameId);

        userToMakeReadyForNextRound.setReadyForNextRound(true);
        userRepository.save(userToMakeReadyForNextRound);
        userRepository.flush();

        for (User player : game.getPlayersInGame()){
            if (!player.isReadyForNextRound()){
                return;
            }
        }
        lobby.setAllAreReadyForNextRound(true);
        if (game.getGameRound() <=4){
            game.increaseGameRound();
            lobby.setIsEndGame(false);
        }
        else{
            game.setWinner();
            game.setAllPlayerStatusToFinished();
            lobby.setIsEndGame(true);
        }

        lobbyRepository.save(lobby);
        lobbyRepository.flush();
        gameRepository.save(game);
        gameRepository.flush();
    }

    public void prepareForNextRound(long gameId){
        Game game = gameRepository.findByGameId(gameId);
        Lobby lobby = lobbyRepository.findByLobbyId(gameId);

        game.resetAllHasCreated();
        game.resetAllHasGuessed();
        game.resetAllAreReadyForNextRound();
        game.setAllPlayerStatusToPlaying();
        lobby.setAllAreReadyForNextRound(false);
        lobby.setIsEndGame(false);
        gameRepository.save(game);
        gameRepository.flush();
        lobbyRepository.save(lobby);
        lobbyRepository.flush();
    }

    public void endGame(User user, long gameId){
        User userWhoFinishedGame = userRepository.findByToken(user.getToken());
        Game gameToFinish = gameRepository.findByGameId(gameId);
        Lobby lobbyToFinish = lobbyRepository.findByLobbyId(gameId);

        userWhoFinishedGame.setReadyForNextRound(false);
        userWhoFinishedGame.setPlayerStatus(PlayerStatus.FINISHED);
        userWhoFinishedGame.setHasCreated(false);
        userWhoFinishedGame.setHasGuessed(false);

        if (userWhoFinishedGame.getToken().equals(lobbyToFinish.getAdmin().getToken())){

            if (lobbyToFinish.getPlayersInLobby().size() == 1){
                lobbyToFinish.setAdmin(null);
                gameToFinish.setAdmin(null);
                lobbyToFinish.setPlayersInLobby(null);
                gameToFinish.setPlayersInGame(null);
                lobbyRepository.deleteById(lobbyToFinish.getLobbyId());
                lobbyRepository.flush();
                gameRepository.deleteById(gameId);
                gameRepository.flush();
                return;
            }
            else{
                lobbyToFinish.deletePlayerInPlayersInLobby(userWhoFinishedGame);
                gameToFinish.deletePlayerInGame(userWhoFinishedGame);
                lobbyToFinish.setAdmin(lobbyToFinish.getPlayersInLobby().get(0));
                gameToFinish.setAdmin(lobbyToFinish.getPlayersInLobby().get(0));
                lobbyRepository.save(lobbyToFinish);
                lobbyRepository.flush();
                gameRepository.save(gameToFinish);
                gameRepository.flush();
                return;
            }
        }

        lobbyToFinish.deletePlayerInPlayersInLobby(userWhoFinishedGame);
        gameToFinish.deletePlayerInGame(userWhoFinishedGame);
        lobbyRepository.save(lobbyToFinish);
        lobbyRepository.flush();
        gameRepository.save(gameToFinish);
        gameRepository.flush();

    }






    /*public SetList rotateSets(Game game) {
        SetList sets = game.getSetList();
        game.rotateSets();
        gameRepository.save(game);
        gameRepository.flush();
        return sets;
    }*/

    //next round:
    /*public boolean isNextRound(long gameId){
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
    }*/

    //For testing
    public Picture getPictureOfUser(User user){
        User user2 = userRepository.findByToken(user.getToken());
        return user2.getCurrentlyCreating();
    }

    public boolean hasGrid(long gameId){
        Game game = gameRepository.findByGameId(gameId);
        if (game.getPicturesonGrid() == null){
            return false;
        }
        else{
            return true;
        }
    }


}

