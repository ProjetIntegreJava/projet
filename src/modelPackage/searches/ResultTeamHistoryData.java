package modelPackage.searches;

import java.time.LocalDate;

public class ResultTeamHistoryData {
    public final String player;
    public final String teamName, clubName, clubNationality;
    public final LocalDate beginningDate, endingDate;

    public ResultTeamHistoryData(
            String player,
            String teamName, String clubName, String clubNationality,
            LocalDate beginningDate, LocalDate endingDate
    ) {
        this.player = player;
        this.teamName = teamName;
        this.clubName = clubName;
        this.clubNationality = clubNationality;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
    }

    public String getPlayer() {
        return player;
    }
    public String getTeamName() {
        return teamName;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubNationality() {
        return clubNationality;
    }

    public LocalDate getBeginningDate() {
        return beginningDate;
    }
    public LocalDate getEndingDate() {
        return endingDate;
    }
}
