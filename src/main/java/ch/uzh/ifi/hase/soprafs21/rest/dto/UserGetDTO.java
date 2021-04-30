package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;

public class UserGetDTO {

    private Long id;
    private String username;
    private OnlineStatus status;
    private String token;
    private PlayerStatus playerStatus;




    public PlayerStatus getPlayerStatus() { return playerStatus; }

    public void setPlayerStatus(PlayerStatus playerStatus) { this.playerStatus = playerStatus; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

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

    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }
}
