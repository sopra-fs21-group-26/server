package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

@Entity
@Table(name = "GAME")

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long gameId;

    @Column
    private String gameName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn()
    private List<User> playersInGame;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User admin;

    @Column
    private int numbersOfPlayers;

    @Transient
    private List<Picture> picturesonGrid;

    @OneToOne(cascade = CascadeType.ALL)
    private ScoreSheet scoreSheet;

    @Column
    private int gameRound;

    public Game() {
    }

    public Game(long lobbyId, List<User> players) {
        this.scoreSheet = new ScoreSheet(players);
        this.gameId = lobbyId;
        this.picturesonGrid = new ArrayList<>();
    }

    public int getGameRound() {
        return gameRound;
    }

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }


    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public int getNumbersOfPlayers() {
        return numbersOfPlayers;
    }

    public void setNumbersOfPlayers(int numbersOfPlayers) {
        this.numbersOfPlayers = numbersOfPlayers;
    }

    public List<User> getPlayersInGame() {
        return playersInGame;
    }

    public void setPlayersInGame(List<User> playersInGame) {
        this.playersInGame = playersInGame;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public List<Picture> getPicturesonGrid() {
        return picturesonGrid;
    }

    public void setPicturesonGrid(List<Picture> picturesonGrid) {
        this.picturesonGrid = picturesonGrid;
    }


    public ScoreSheet getScoreSheet(){
        this.scoreSheet.updateScoreSheet();
        return this.scoreSheet;
    }

}
