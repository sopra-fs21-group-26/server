package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.constant.Set;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private OnlineStatus onlineStatus;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int points;

    @Column()
    private int guessedOtherPicturesCorrectly;

    @Column()
    private int ownPicturesCorrectlyGuessed;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn
    private Picture currentlyCreating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Picture recreatedPicture;

    @Column(nullable = false)
    private String createdOn;

    @Column()
    private PlayerStatus playerStatus;

    @Column()
    private int gamesPlayed;

    @Column()
    private int gamesWon;

    @Column()
    private int score;

    @Column()
    private String ownPictureCoordinate;

    @Column
    private boolean hasCreated;

    @Column
    private boolean hasGuessed;

    @Column
    private Set activeSet;

    public boolean isHasGuessed() {
        return hasGuessed;
    }

    public void setHasGuessed(boolean hasGuessed) {
        this.hasGuessed = hasGuessed;
    }

    public boolean isHasCreated() {
        return hasCreated;
    }

    public void setHasCreated(boolean hasCreated) {
        this.hasCreated = hasCreated;
    }


    public int calculatePoints(){
        return this.guessedOtherPicturesCorrectly + 2 * this.ownPicturesCorrectlyGuessed;
    }

    public Picture getRecreatedPicture() {
        return recreatedPicture;
    }

    public void setRecreatedPicture(Picture recreatedPicture) {
        this.recreatedPicture = recreatedPicture;
    }

    public String getOwnPictureCoordinate() {
        return ownPictureCoordinate;
    }

    public void setOwnPictureCoordinate(String ownPictureCoordinate) {
        this.ownPictureCoordinate = ownPictureCoordinate;
    }

    public void increaseGamesPlayed(){
        this.setGamesPlayed(this.getGamesPlayed()+1);
    }


    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void addGuessPoint(int points){
        this.guessedOtherPicturesCorrectly += points;
    }

    public void addPicturePoint(int points){
        this.ownPicturesCorrectlyGuessed += points;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date();
        this.createdOn = sdf.format(date);
    }

    public Picture getCurrentlyCreating() {
        return currentlyCreating;
    }

    public void setCurrentlyCreating(Picture currentlyCreating) {
        this.currentlyCreating = currentlyCreating;
    }


    public int getOwnPicturesCorrectlyGuessed() {
        return ownPicturesCorrectlyGuessed;
    }

    public void setOwnPicturesCorrectlyGuessed(int ownPicturesCorrectlyGuessed) {
        this.ownPicturesCorrectlyGuessed = ownPicturesCorrectlyGuessed;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public void setGuessedOtherPicturesCorrectly(int guessedOtherPicturesCorrectly) {
        this.guessedOtherPicturesCorrectly = guessedOtherPicturesCorrectly;
    }

    public int getGuessedOtherPicturesCorrectly() {
        return guessedOtherPicturesCorrectly;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public void resetPoints() {
        this.points = 0;
    }

    public void addPoint() {
        this.points += 1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

    public Set getActiveSet() {
        return this.activeSet;
    }

    public void setActiveSet(Set activeSet) {
        this.activeSet = activeSet;
    }
}
