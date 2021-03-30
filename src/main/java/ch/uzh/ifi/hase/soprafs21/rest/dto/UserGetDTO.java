package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;

public class UserGetDTO {

    private Long id;
    private String name;
    private String username;
    private OnlineStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
