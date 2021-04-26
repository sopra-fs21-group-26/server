package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
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
    @ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER)
    private List<User> players;
    @Column
    private Hashtable<String, Integer> scoreSheet = new Hashtable<>();

    public ScoreSheet(){
    }

    public ScoreSheet(List<User> players){
            this.players = players;
            updateScoreSheet();
        }

    public void updateScoreSheet() {
    for (User player : this.players){
        String name = player.getUsername();
        int points = player.getPoints();
        this.scoreSheet.put(name, points);
        }
    }

    public Hashtable<String, Integer> getScoreSheet() {
        return this.scoreSheet;
    }
}
