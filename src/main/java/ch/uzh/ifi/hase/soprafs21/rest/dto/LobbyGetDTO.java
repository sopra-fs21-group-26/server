package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LobbyGetDTO {

    private String lobbyName;
    private int numbersOfPlayers;
    private UserGetDTO admin;
    private List<UserGetDTO> playersInLobby;
    private boolean allAreReadyForNextRound;
    private boolean isEndGame;

    public boolean isAllAreReadyForNextRound() {
        return allAreReadyForNextRound;
    }

    public void setAllAreReadyForNextRound(boolean allAreReadyForNextRound) {
        this.allAreReadyForNextRound = allAreReadyForNextRound;
    }

    public void setIsEndGame(boolean endGame) {
        isEndGame = endGame;
    }

    public boolean getIsEndGame() {
        return isEndGame;
    }


    //For testing
    private Long lobbyId;
    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }
    //For testing


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
