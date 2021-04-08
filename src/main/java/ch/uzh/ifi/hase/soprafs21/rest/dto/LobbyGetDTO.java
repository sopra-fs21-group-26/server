package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LobbyGetDTO {

    private String lobbyName;
    private int numbersOfPlayers;
    private UserGetDTO admin;
    private List<UserGetDTO> playersInLobby;


    public String getLobbyName() {
        return lobbyName;
    }

    public int getNumbersOfPlayers() {
        return numbersOfPlayers;
    }

    public UserGetDTO getAdmin() {
        return admin;
    }

    public List<UserGetDTO> getPlayersInLobby() {
        return playersInLobby;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public void setNumbersOfPlayers(int numbersOfPlayers) {
        this.numbersOfPlayers = numbersOfPlayers;
    }

    public void setAdmin(UserGetDTO admin) {
        this.admin = admin;
    }

    public void setPlayersInLobby(List<UserGetDTO> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }
}
