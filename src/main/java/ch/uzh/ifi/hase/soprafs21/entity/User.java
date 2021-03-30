package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;



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
    private int score;

    @Column(nullable = false)
    private int guessedOtherPicturesCorrectly;

    @Column(nullable = false)
    private int ownPicturesCorrectlyGuessed;

    @Column(nullable = false)
    private String currentlyCreating;

    @Column(nullable = false)
    private LocalDate createdOn;

    @Column(nullable = false)
    private PlayerStatus playerStatus;

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn() {
        this.createdOn = java.time.LocalDate.now();
    }

    public String getCurrentlyCreating() {
        return currentlyCreating;
    }

    public void setCurrentlyCreating(String currentlyCreating) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public OnlineStatus getStatus() {
        return onlineStatus;
    }

    public void setStatus(OnlineStatus status) {
        this.onlineStatus = status;
    }
}
