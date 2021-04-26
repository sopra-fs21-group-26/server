package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;
import ch.uzh.ifi.hase.soprafs21.entity.User;
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
        while(sc.hasNext())
        {
            inline+=sc.nextLine();
        }
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(inline);
        JSONArray jarray = new JSONArray();
        jarray.add(obj);
        JSONArray UnsplashArray = (JSONArray) jarray.get(0);
        for(int i=0;i<UnsplashArray.size();i++)
        {
            JSONObject obj1 = (JSONObject) UnsplashArray.get(i);
            JSONObject imgURLs = (JSONObject) obj1.get("urls");
            PictureList.add(new Picture((String) imgURLs.get("small"), i));
        }

        return PictureList;
    }

    public Game getGame(long id){
        Optional<Game> game = gameRepository.findById(id);
        if(!game.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found!");
        }
        return game.get();
    }

   public Picture getRandomPicture(User user, long gameId){
        User userToAssignCoordinate = userRepository.findByToken(user.getToken());
        Game game = gameRepository.findByGameId(gameId);

        List <Picture> pictures = game.getPicturesonGrid();
        Random rand = new Random();

        Picture randomPicture = pictures.get(rand.nextInt(pictures.size()));
        userToAssignCoordinate.setOwnPictureCoordinate(randomPicture.getCoordinate());
        return randomPicture;
    }




}
