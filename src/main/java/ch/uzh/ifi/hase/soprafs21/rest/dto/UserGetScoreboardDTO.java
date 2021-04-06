package ch.uzh.ifi.hase.soprafs21.rest.dto;

public class UserGetScoreboardDTO {

    private Long id;
    private String username;
    private int score;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
