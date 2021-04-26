package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.entity.ScoreSheet;

import java.util.Hashtable;

public class GameGetScoreSheetDTO {
    private Hashtable<String, Integer> scoreSheet;

    public Hashtable<String, Integer> getScoreSheet() {
        return scoreSheet;
    }

    public void setScoreSheet(Hashtable<String, Integer> scoreSheet) {
        this.scoreSheet = scoreSheet;
    }
}
