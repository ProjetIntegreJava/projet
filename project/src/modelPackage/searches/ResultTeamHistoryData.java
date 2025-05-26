package modelPackage.searches;

import java.time.LocalDate;

public class ResultTeamHistoryData {
    public final String player1FirstName, player1LastName, player2FirstName, player2LastName, player3FirstName, player3LastName, player4FirstName, player4LastName, player5FirstName, player5LastName;
    public final String teamName, clubName, clubNationality;
    public final LocalDate beginningDate, endingDate;

    public ResultTeamHistoryData(
            String player1FirstName, String player1LastName,
            String player2FirstName, String player2LastName,
            String player3FirstName, String player3LastName,
            String player4FirstName, String player4LastName,
            String player5FirstName, String player5LastName,
            String teamName, String clubName, String clubNationality,
            LocalDate beginningDate, LocalDate endingDate
    ) {
        this.player1FirstName = player1FirstName;
        this.player1LastName = player1LastName;
        this.player2FirstName = player2FirstName;
        this.player2LastName = player2LastName;
        this.player3FirstName = player3FirstName;
        this.player3LastName = player3LastName;
        this.player4FirstName = player4FirstName;
        this.player4LastName = player4LastName;
        this.player5FirstName = player5FirstName;
        this.player5LastName = player5LastName;
        this.teamName = teamName;
        this.clubName = clubName;
        this.clubNationality = clubNationality;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
    }

    public String getPlayer1FirstName() {
        return player1FirstName;
    }

    public String getPlayer1LastName() {
        return player1LastName;
    }

    public String getPlayer2FirstName() {
        return player2FirstName;
    }

    public String getPlayer2LastName() {
        return player2LastName;
    }

    public String getPlayer3FirstName() {
        return player3FirstName;
    }

    public String getPlayer3LastName() {
        return player3LastName;
    }

    public String getPlayer4FirstName() {
        return player4FirstName;
    }

    public String getPlayer4LastName() {
        return player4LastName;
    }

    public String getPlayer5FirstName() {
        return player5FirstName;
    }

    public String getPlayer5LastName() {
        return player5LastName;
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
