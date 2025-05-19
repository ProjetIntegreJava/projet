package modelPackage;

import java.time.LocalDate;

public class Match {
    private int id;
    private Team teamBlue;
    private Team teamRed;
    private Competition competition;
    private LocalDate creationDate;
    private LocalDate occurenceDate;
    private boolean isBlueWin;
    private String replayLink;
    private String summary;
    public Match(String id, Team teamBlue, Team teamRed, Competition competition, LocalDate creationDate, LocalDate occurenceDate, boolean isBlueWin, String replayLink, String summary){
        this.creationDate = creationDate;
        this.occurenceDate = occurenceDate;
        this.isBlueWin = isBlueWin;
        this.replayLink = replayLink;
        this.summary = summary;

    }
}
