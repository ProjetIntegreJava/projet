package modelPackage.searches;

import java.time.LocalDate;

public class ResultMatchData {
    public final String replayLink;
    public final LocalDate occurrenceDate;
    public final boolean isBlueWinner;
    public final String teamBlueName, clubBlueName, teamRedName, clubRedName;

    public ResultMatchData(String replayLink, LocalDate occurrenceDate, boolean isBlueWinner, String teamBlueName, String clubBlueName, String teamRedName, String clubRedName) {
        this.replayLink = replayLink;
        this.occurrenceDate = occurrenceDate;
        this.isBlueWinner = isBlueWinner;
        this.teamBlueName = teamBlueName;
        this.clubBlueName = clubBlueName;
        this.teamRedName = teamRedName;
        this.clubRedName = clubRedName;
    }
    public String getReplayLink() {
        return replayLink;
    }

    public LocalDate getOccurrenceDate() {
        return occurrenceDate;
    }

    public boolean isBlueWinner() {
        return isBlueWinner;
    }

    public String getTeamBlueName() {
        return teamBlueName;
    }

    public String getClubBlueName() {
        return clubBlueName;
    }

    public String getTeamRedName() {
        return teamRedName;
    }

    public String getClubRedName() {
        return clubRedName;
    }
}
