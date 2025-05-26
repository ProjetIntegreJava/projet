package modelPackage;

import java.time.LocalDate;

public class Match {
    private final int id;
    private final Team teamBlue, teamRed;
    private final Competition competition;
    private final LocalDate creationDate, occurrenceDate;
    private final boolean isBlueWin;
    private final String replayLink, summary;
    public Match(int id, Team teamBlue, Team teamRed, Competition competition, LocalDate creationDate, LocalDate occurrenceDate, boolean isBlueWin, String replayLink, String summary){
        this.id = id;
        this.teamBlue = teamBlue;
        this.teamRed = teamRed;
        this.competition = competition;
        this.creationDate = creationDate;
        this.occurrenceDate = occurrenceDate;
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
    public LocalDate getOccurrenceDate() {
        return occurrenceDate;
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
