package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LOBBY")
public class Lobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long lobbyId;

    @Column(nullable = false)
    private String lobbyName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn ()
    private List<User> playersInLobby;

    @Column(nullable = false)
    private LobbyStatus lobbyStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (nullable = false)
    private User admin;

    @Column(nullable = false)
    private int numbersOfPlayers;


    public boolean checkIfAllAreReady(){
        boolean isReady = false;
        for (User user : this.getPlayersInLobby()){
            if (user.getPlayerStatus() == PlayerStatus.READY){
                isReady = true;
            }
            else {
                return isReady = false;
            }
        }
        return isReady;
    }


    public void addPlayerToPlayersInLobby(User user){
        List<User> oldPlayersInLobby = this.getPlayersInLobby();
        oldPlayersInLobby.add(user);
        List <User> newPlayersInLobby = oldPlayersInLobby;
        this.setPlayersInLobby(newPlayersInLobby);
    }

    public void deletePlayerInPlayersInLobby(User user){
        List<User> oldPlayersInLobby = this.getPlayersInLobby();
        oldPlayersInLobby.remove(user);
        List <User> newPlayersInLobby = oldPlayersInLobby;
        this.setPlayersInLobby(newPlayersInLobby);
    }

    public void increaseNumbersOfPlayers(){
        int oldNumbersOfPlayers = this.getNumbersOfPlayers();
        this.setNumbersOfPlayers(oldNumbersOfPlayers+1);
    }

    public void decreaseNumbersOfPlayers(){
        int oldNumbersOfPlayers = this.getNumbersOfPlayers();
        this.setNumbersOfPlayers(oldNumbersOfPlayers-1);
    }

    public void changeAllPLayerStatusToPlaying(){
        List <User> players = this.getPlayersInLobby();
        for (User user : players){
            user.setPlayerStatus(PlayerStatus.PLAYING);
        }
    }

    public void increaseAllPlayerGamesPlayed(){
        List <User> players = this.getPlayersInLobby();
        for (User user : players){
            user.increaseGamesPlayed();
        }
    }







    public int getNumbersOfPlayers() {
        return numbersOfPlayers;
    }

    public void setNumbersOfPlayers(int numbersOfPlayers) {
        this.numbersOfPlayers = numbersOfPlayers;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public List<User> getPlayersInLobby() {
        return playersInLobby;
    }

    public LobbyStatus getLobbyStatus() {
        return lobbyStatus;
    }

    public User getAdmin() {
        return admin;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public void setPlayersInLobby(List<User> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

    public void setLobbyStatus(LobbyStatus lobbyStatus) {
        this.lobbyStatus = lobbyStatus;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public void join(User user){
        this.playersInLobby.add(user);
    }
}
