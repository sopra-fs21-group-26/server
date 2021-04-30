package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn()
    private List<Picture> picturesonGrid;

    @OneToOne(cascade = CascadeType.ALL)
    private ScoreSheet scoreSheet;

    /*@OneToOne(cascade = CascadeType.ALL)
    private SetList setList;*/

    @Column
    private int gameRound;


    public Game() {
    }

    public Game(long lobbyId, List<User> players) {
        this.scoreSheet = new ScoreSheet(players);
        //this.setList = new SetList((UserRepository) players);
        this.gameId = lobbyId;
        this.picturesonGrid = new ArrayList<>();
    }

    public void setAllPlayerStatusToPlaying(){
        for (User user : this.playersInGame){
            user.setPlayerStatus(PlayerStatus.PLAYING);
        }
    }

    public void increaseGameRound(){
        this.setGameRound(this.getGameRound()+1);
    }

    public void resetAllHasGuessed(){
        for (User user: this.playersInGame){
            user.setHasGuessed(false);
        }

    }

    public void resetAllHasCreated(){
        for (User user: this.playersInGame){
            user.setHasCreated(false);
        }
    }

    public void resetAllAreReadyForNextRound(){
        for (User user: this.playersInGame){
            user.setReadyForNextRound(false);
        }
    }

    public void setWinner(){
        User winner = new User();
        winner.setPoints(0);
        for (User user : this.playersInGame){
            if (user.getPoints() > winner.getPoints()){
                winner = user;
            }
        }
        winner.setGamesWon(winner.getGamesWon()+1);
    }

    public void setAllPlayerStatusToFinished(){
        for (User user: this.playersInGame){
            user.setPlayerStatus(PlayerStatus.FINISHED);
        }
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

    /*public SetList getSetList() {
        return this.setList;
    }

    public void rotateSets() {
        this.setList.rotateSetList();
    }*/

}
