package ch.uzh.ifi.hase.soprafs21.rest.dto;

public class UserGetProfileDTO {

    private int score;
    private String username;
    private int gamesPlayed;
    private int gamesWon;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public String getUsername() {
        return username;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

}
