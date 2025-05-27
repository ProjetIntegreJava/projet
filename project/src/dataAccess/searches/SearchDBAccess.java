package dataAccess.searches;

import dataAccess.SingletonConnection;
import exceptionPackage.Search.SearchDataAccessException;
import modelPackage.Competition;
import modelPackage.searches.ResultMatchData;
import modelPackage.searches.ResultParticipationData;
import modelPackage.searches.ResultTeamHistoryData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SearchDBAccess implements SearchDataAccess {
    @Override
    public ArrayList<ResultMatchData> getSearchMatchData(Competition competition, String championName) throws SearchDataAccessException  {
        ArrayList<ResultMatchData> resultMatchData = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement("""
                    SELECT
                        m.replay_link,
                        m.occurrence_date,
                        m.is_blue_win,
                        tb.name AS team_blue_name,
                        cb.name AS club_blue_name,
                        tr.name AS team_red_name,
                        cr.name AS club_red_name
                    FROM `match` m
                    JOIN competition c ON m.competition_name = c.name
                    JOIN team tb ON m.team_blue = tb.name
                    JOIN club cb ON tb.club = cb.name
                    JOIN team tr ON m.team_red = tr.name
                    JOIN club cr ON tr.club = cr.name
                    JOIN participation p ON p.id_match = m.id
                    JOIN champion ch ON p.champion = ch.name
                    WHERE
                        c.name = ?
                        AND c.year = ?
                        AND ch.name = ?;
                    """);
            preparedStatement.setString(1, competition.getName());
            preparedStatement.setInt(2, competition.getYear());
            preparedStatement.setString(3, championName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String replayLink = resultSet.getString("replay_link");
                LocalDate occurrenceDate = LocalDate.parse(resultSet.getString("occurrence_date"));
                boolean isBlueWinner = resultSet.getBoolean("is_blue_win");
                String teamBlueName = resultSet.getString("team_blue_name");
                String clubBlueName = resultSet.getString("club_blue_name");
                String teamRedName = resultSet.getString("team_red_name");
                String clubRedName = resultSet.getString("club_red_name");
                ResultMatchData matchData = new ResultMatchData(replayLink, occurrenceDate, isBlueWinner, teamBlueName, clubBlueName, teamRedName, clubRedName);
                resultMatchData.add(matchData);
            }
        } catch (SQLException e) {
            throw new SearchDataAccessException("Error while searching match data");
        }
        return resultMatchData;
    }
    @Override
    public ArrayList<ResultParticipationData> getSearchParticipationData(int matchID) throws SearchDataAccessException {
        ArrayList<ResultParticipationData> resultTeamHistoryData = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement("""
                SELECT
                    pl.first_name,
                    pl.last_name,
                    c.name,
                    c.race,
                    p.kills,
                    p.assists,
                    p.death,
                    p.creep_score,
                    p.damage,
                    p.wards_score,
                    p.golds_earn,
                    p.damage_received,
                    p.role
                FROM `match` m
                JOIN participation p ON m.id = p.id_match
                JOIN champion c ON p.champion = c.name
                Join player pl ON p.id_player = pl.id
                WHERE m.id = ?;
            """);
            preparedStatement.setInt(1, matchID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String championName = resultSet.getString("name");
                String championRace = resultSet.getString("race");
                String role = resultSet.getString("role");
                Integer kills = resultSet.getInt("kills");
                Integer deaths = resultSet.getInt("death");
                Integer assists = resultSet.getInt("assists");
                Integer goldEarned = resultSet.getInt("golds_earn");
                Integer damageReceived = resultSet.getInt("damage_received");
                Integer damage = resultSet.getInt("damage");
                Integer wardScore = resultSet.getInt("wards_score");
                Integer creepScore = resultSet.getInt("creep_score");
                ResultParticipationData participationData = new ResultParticipationData(
                        firstName, lastName, championName, championRace, role,
                        kills, deaths, assists, goldEarned, damageReceived,
                        damage, wardScore, creepScore
                );
                resultTeamHistoryData.add(participationData);
            }
        } catch (SQLException e) {
            throw new SearchDataAccessException("Error while searching participation data" + e.getMessage());
        }
        return resultTeamHistoryData;
    }
    @Override
    public ArrayList<ResultTeamHistoryData> getSearchTeamHistoryData(String teamName, LocalDate beginningDate, LocalDate endingDate) throws SearchDataAccessException {
        ArrayList<ResultTeamHistoryData> resultTeamHistoryData = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement("""
                SELECT th.beginning_date, th.ending_date,
                    pl1.first_name AS player1_first_name, pl1.last_name AS player1_last_name,
                    pl2.first_name AS player2_first_name, pl2.last_name AS player2_last_name,
                    pl3.first_name AS player3_first_name, pl3.last_name AS player3_last_name,
                    pl4.first_name AS player4_first_name, pl4.last_name AS player4_last_name,
                    pl5.first_name AS player5_first_name, pl5.last_name AS player5_last_name,
                    t.name AS team_name, c.name AS club_name, c.nationality
                FROM team_history th
                JOIN player pl1 ON th.id_player_one = pl1.id
                JOIN player pl2 ON th.id_player_two = pl2.id
                JOIN player pl3 ON th.id_player_three = pl3.id
                JOIN player pl4 ON th.id_player_four = pl4.id
                JOIN player pl5 ON th.id_player_five = pl5.id
                JOIN team t ON th.team = t.name
                JOIN club c ON t.club = c.name
                WHERE\s
                    t.name = ?
                    AND th.beginning_date BETWEEN ? AND ?
                    AND th.ending_date BETWEEN ? and ?
                    ORDER by th.beginning_date;
            """);
            preparedStatement.setString(1, teamName);
            preparedStatement.setObject(2, beginningDate);
            preparedStatement.setObject(3, endingDate);
            preparedStatement.setObject(4, beginningDate);
            preparedStatement.setObject(5, endingDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                LocalDate beginning = resultSet.getObject("beginning_date", LocalDate.class);
                LocalDate ending = resultSet.getObject("ending_date", LocalDate.class);
                String player1FirstName = resultSet.getString("player1_first_name");
                String player1LastName = resultSet.getString("player1_last_name");
                String player2FirstName = resultSet.getString("player2_first_name");
                String player2LastName = resultSet.getString("player2_last_name");
                String player3FirstName = resultSet.getString("player3_first_name");
                String player3LastName = resultSet.getString("player3_last_name");
                String player4FirstName = resultSet.getString("player4_first_name");
                String player4LastName = resultSet.getString("player4_last_name");
                String player5FirstName = resultSet.getString("player5_first_name");
                String player5LastName = resultSet.getString("player5_last_name");
                String teamNameResult = resultSet.getString("team_name");
                String clubName = resultSet.getString("club_name");
                String clubNationality = resultSet.getString("nationality");
                ResultTeamHistoryData teamHistoryData = new ResultTeamHistoryData(
                        player1FirstName, player1LastName,
                        player2FirstName, player2LastName,
                        player3FirstName, player3LastName,
                        player4FirstName, player4LastName,
                        player5FirstName, player5LastName,
                        teamNameResult, clubName, clubNationality,
                        beginning, ending
                );
                resultTeamHistoryData.add(teamHistoryData);
            }
        } catch (SQLException e) {
            throw new SearchDataAccessException("Error while searching team history data");
        }
        return resultTeamHistoryData;
    }
}
