package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "GAME")

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long gameId;

    @Column(nullable = false)
    private String gameName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn()
    private List<User> playersInGame;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User admin;

    @Column(nullable = false)
    private int numbersOfPlayers;

    @Transient
    private List<Picture> picturesonGrid;



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
}
