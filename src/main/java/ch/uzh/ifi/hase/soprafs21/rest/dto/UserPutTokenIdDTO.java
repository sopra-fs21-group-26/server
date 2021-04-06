package ch.uzh.ifi.hase.soprafs21.rest.dto;

public class UserPutTokenIdDTO {
    private String token;
    private long id;

    public String getToken() {
        return token;
    }

    public long getId() {
        return id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(long id) {
        this.id = id;
    }


}
