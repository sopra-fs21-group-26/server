package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.entity.ScoreSheet;

public class GameGetScoreSheetDTO {
    private ScoreSheet scoreSheet;

    public ScoreSheet getScoreSheet() {
        return scoreSheet;
    }

    public void setScoreSheet(ScoreSheet scoreSheet) {
        this.scoreSheet = scoreSheet;
    }
}
