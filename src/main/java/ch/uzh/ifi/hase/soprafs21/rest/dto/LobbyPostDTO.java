package ch.uzh.ifi.hase.soprafs21.rest.dto;

public class LobbyPostDTO {

    private String token;
    private String lobbyName;

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
