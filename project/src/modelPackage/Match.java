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
    public Match(int id, Team teamBlue, Team teamRed, Competition competition, LocalDate creationDate, LocalDate occurenceDate, boolean isBlueWin, String replayLink, String summary){
        this.id = id;
        this.teamBlue = teamBlue;
        this.teamRed = teamRed;
        this.competition = competition;
        this.creationDate = creationDate;
        this.occurenceDate = occurenceDate;
        this.isBlueWin = isBlueWin;
        this.replayLink = replayLink;
        this.summary = summary;

    }
    public int getId() {
        return id;
    }
    public Team getTeamBlue() {
        return teamBlue;
    }
    public Team getTeamRed() {
        return teamRed;
    }
    public Competition getCompetition() {
        return competition;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public LocalDate getOccurenceDate() {
        return occurenceDate;
    }
    public boolean isBlueWin() {
        return isBlueWin;
    }
    public String getReplayLink() {
        return replayLink;
    }
    public String getSummary() {
        return summary;
    }
}
