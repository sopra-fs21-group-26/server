package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;


public class GuessScreen {

    private List<String> recreatedPictures;
    private List<String> usernames;

    public List<String> getUsernames() {
        return usernames;
    }

    public List<String> getRecreatedPictures() {
        return recreatedPictures;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public void setRecreatedPictures(List<String> recreatedPictures) {
        this.recreatedPictures = recreatedPictures;
    }

}
