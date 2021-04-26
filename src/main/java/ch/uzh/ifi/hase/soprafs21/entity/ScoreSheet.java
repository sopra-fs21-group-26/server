package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

@Entity
@Table(name = "SCORESHEET")
public class ScoreSheet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long scoreSheetId;

    private Hashtable<String, Integer> scoreSheet = new Hashtable<>();

    public ScoreSheet(){
        this.scoreSheet.put("name", 1);
    }

    public ScoreSheet(List<User> players){
            updateScoreSheet(players);
        }

    public void updateScoreSheet(List<User> players) {
    for (User player : players){
        String name = player.getUsername();
        int points = player.getPoints();
        this.scoreSheet.put(name, points);
        }
    }

    public Hashtable<String, Integer> getScoreSheet() {
        return scoreSheet;
    }
}
