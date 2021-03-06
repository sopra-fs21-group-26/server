package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.reverse;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getUsers(){
        List <User> users = this.userRepository.findAll();
        return users;
    }

    //Todo: Test if Bubblesort works when implemented score mechanism!
    public List<User> getSortedUsers() {
        List <User> users = this.userRepository.findAll();
        users.sort(Comparator.comparing(User::getPoints));
        reverse(users);
        return users;
    }

    public static void bubbleSort(List <User> users) {
        int n = users.size();
        User temp;
        for (int i= 0; i<n; i++){
            for(int j =1; j<(n-1); j++){
                if (users.get(j-1).getPoints() > users.get(j).getPoints()){
                    temp = users.get(j-1);
                    users.set(j-1,users.get(j));
                    users.set(j, temp);
                }
            }
        }
        reverse(users);
    }

    public User createUser(User newUser) {
        newUser.setUpNewUser();
        checkIfUserExists(newUser);

        // saves the given entity but data is only persisted in the database once flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public void logoutUser(User user){
        User user1 = userRepository.findByToken(user.getToken());
        if (user1 == null){
            String baseErrorMessage = "User with token was not found!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }
        else {
            user1.setOnlineStatus(OnlineStatus.OFFLINE);
            userRepository.save(user1);
            userRepository.flush();
        }
    }

    public User checkLogin(User user){
        User user1 = userRepository.findByUsername(user.getUsername());

        String baseErrorMessage = "The %s provided %s not registered. Therefore, the user could not be logged in!";
        if (user1 == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format(baseErrorMessage, "username", "is"));
        }
        if (!user1.getPassword().equals(user.getPassword())){
            String baseErrorMessage2 = "The %s provided %s not correct. Therefore, the user could not be logged in!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format(baseErrorMessage2, "password", "is"));
        }
        else{
            user1.setOnlineStatus(OnlineStatus.ONLINE);
            userRepository.save(user1);
            userRepository.flush();
            return user1;
        }
    }



    public User getSingleUser(long id){


        User user = userRepository.findById(id);

        if(user == null){

            String baseErrorMessage = "User with ID was not found!";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, baseErrorMessage);
        }

        else{

            return user;
        }

    }

    public User getSingleUserByToken(String token){


        User user = userRepository.findByToken(token);

        if(user == null){

            String baseErrorMessage = "User with ID was not found!";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, baseErrorMessage);
        }

        else{

            return user;
        }

    }

    /**
     * This is a helper method that will check the uniqueness criteria of the username and the name
     * defined in the User entity. The method will do nothing if the input is unique and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */
    public void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());

        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
        if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username and the name", "are"));
        }
    }

    public User checkIfUsernameExists(String username){
        User user = userRepository.findByUsername(username);

        String baseErrorMessage = "The %s provided %s not exist!";
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage, "username", "does"));
        }
        return user;
    }

    public boolean checkEditPermission(String token, long id){
        User user = userRepository.findByToken(token);

        if (user.getId() == id){
            return true;
        }
        else{
            return false;
        }
    }
//
    public User editUser(User user){
        User userToEdit = userRepository.findByToken(user.getToken());

        if (user.getUsername() != null){
            userToEdit.setUsername(user.getUsername());
        }
        userRepository.save(userToEdit);
        userRepository.flush();
        return userToEdit;
    }

    public int addPoint(long playerId){
        User player = userRepository.findById(playerId);
        player.addPoint();
        userRepository.save(player);
        userRepository.flush();
        return player.getPoints();
    }

    public void setHasCreated(User user){
        User userThatHasCreated = userRepository.findByToken(user.getToken());

        if (userThatHasCreated == null){
            String baseErrorMessage = "User does not exist in this game";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        if (userThatHasCreated.getRecreatedPicture() == null){
            String baseErrorMessage = "You can't proceed. You first have to recreate the Picture!";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, baseErrorMessage);
        }

        userThatHasCreated.setHasCreated(true);

        userRepository.save(userThatHasCreated);
        userRepository.flush();
    }

    public void setHasGuessed(User user){
        User userThatHasGuessed = userRepository.findByToken(user.getToken());

        if (userThatHasGuessed == null){
            String baseErrorMessage = "The user doesn't exist!";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, baseErrorMessage);
        }
        userThatHasGuessed.setHasGuessed(true);
    }


}
