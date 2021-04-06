package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.entity.User;

import java.util.ArrayList;

public class LobbyGetDTO {

    private String lobbyName;
    private int numbersOfPlayers;
    private User admin;
    private ArrayList<User> playersInLobby;

    public String getLobbyName() {
        return lobbyName;
    }

    public int getNumbersOfPlayers() {
        return numbersOfPlayers;
    }

    public User getAdmin() {
        return admin;
    }

    public ArrayList<User> getPlayersInLobby() {
        return playersInLobby;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public void setNumbersOfPlayers(int numbersOfPlayers) {
        this.numbersOfPlayers = numbersOfPlayers;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public void setPlayersInLobby(ArrayList<User> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }
}
