package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;

public class UserGetProfileDTO {

    private int score;
    private String username;
    private int gamesPlayed;
    private int gamesWon;

    private PlayerStatus playerStatus;

    public PlayerStatus getPlayerStatus() { return playerStatus; }

    public void setPlayerStatus(PlayerStatus playerStatus) { this.playerStatus = playerStatus; }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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
