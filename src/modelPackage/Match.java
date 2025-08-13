package modelPackage;

import java.time.LocalDate;

public class Match {
    private final Integer id;
    private Team teamBlue, teamRed;
    private Competition competition;
    private LocalDate creationDate, occurrenceDate;
    private boolean isBlueWin;
    private String replayLink, summary;
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

    public Match (
            Team teamBlue, Team teamRed, Competition competition,
            LocalDate creationDate, LocalDate occurrenceDate,
            boolean isBlueWin, String replayLink, String summary
    ){
        this.id = null;
        this.teamBlue = teamBlue;
        this.teamRed = teamRed;
        this.competition = competition;
        this.creationDate = creationDate;
        this.occurrenceDate = occurrenceDate;
        this.isBlueWin = isBlueWin;
        this.replayLink = replayLink;
        this.summary = summary;
    }

    public Match(
            int id, String teamBlueName, String teamRedName, String competitionName,
            int competitionYear, LocalDate creationDate, LocalDate occurrenceDate,
            boolean isBlueWin, String replayLink, String summary
    ) {
        this.id = id;
        this.teamBlue = new Team(teamBlueName);
        this.teamRed = new Team(teamRedName);
        this.competition = new Competition(competitionName, competitionYear);
        this.creationDate = creationDate;
        this.occurrenceDate = occurrenceDate;
        this.isBlueWin = isBlueWin;
        this.replayLink = replayLink;
        this.summary = summary;
    }
    public Integer getId() {
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

    @Override
    public String toString() {
        return this.teamBlue.getName() + " - " + this.teamRed.getName() + " @ " + this.occurrenceDate.toString();
    }

    public void setTeamBlue(Team teamBlue) {
        this.teamBlue = teamBlue;
    }

    public void setTeamRed(Team teamRed) {
        this.teamRed = teamRed;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setOccurrenceDate(LocalDate occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public void setBlueWin(boolean blueWin) {
        isBlueWin = blueWin;
    }

    public void setReplayLink(String replayLink) {
        this.replayLink = replayLink;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
