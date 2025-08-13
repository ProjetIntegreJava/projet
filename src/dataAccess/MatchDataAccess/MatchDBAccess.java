package dataAccess.MatchDataAccess;

import dataAccess.CompetitionDataAccess.CompetitionDBAccess;
import dataAccess.SingletonConnection;
import dataAccess.TeamDataAccess.TeamDBAccess;
import exceptionPackage.Competition.ReadCompetitionException;
import exceptionPackage.Match.*;
import exceptionPackage.Team.ReadTeamException;
import modelPackage.Match;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MatchDBAccess implements MatchDataAccess{
    @Override
    public void addMatch(Match match) throws AddMatchException {
        try{
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "INSERT INTO `match`(team_blue, team_red, competition_name, competition_year, occurrence_date, is_blue_win, replay_link, summary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, match.getTeamBlue().getName());
            preparedStatement.setString(2, match.getTeamRed().getName());
            preparedStatement.setString(3, match.getCompetition().getName());
            preparedStatement.setInt(4, match.getCompetition().getYear());
            preparedStatement.setDate(5, java.sql.Date.valueOf(match.getOccurrenceDate()));
            preparedStatement.setBoolean(6, match.isBlueWin());
            if (match.getReplayLink() == null || match.getReplayLink().isEmpty()) {
                preparedStatement.setNull(7, Types.NULL);
            } else {
                preparedStatement.setString(7, match.getReplayLink());
            }
            if (match.getSummary() == null || match.getSummary().isEmpty()) {
                preparedStatement.setNull(8, Types.NULL);
            } else {
                preparedStatement.setString(8, match.getSummary());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AddMatchException("An error occurred while reading the match");
        }
    }
    @Override
    public Match getMatch(Integer matchId) throws ReadMatchException {
        if (matchId == null) {
            throw new ReadMatchException("L'ID du match ne peut pas être nul");
        }
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM `match` WHERE id = ?");
            preparedStatement.setInt(1, matchId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSetToMatch(resultSet);
            } else {
                throw new ReadMatchException("No match found with the id: " + matchId);
            }
        } catch (SQLException e) {
            throw new ReadMatchException("An error occurred while reading the match" );
        } catch (ReadTeamException e) {
            throw new ReadMatchException("An error occurred while reading the team");
        } catch (ReadCompetitionException e) {
            throw new ReadMatchException("An error occurred while reading the competition");
        }
    }

    @Override
    public void updateMatch(Match match) throws UpdateMatchException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "UPDATE `match` SET team_blue = ?, team_red = ?, competition_name = ?, competition_year = ?, occurrence_date = ?, is_blue_win = ?, replay_link = ?, summary = ? WHERE id = ?");
            preparedStatement.setString(1, match.getTeamBlue().getName());
            preparedStatement.setString(2, match.getTeamRed().getName());
            preparedStatement.setString(3, match.getCompetition().getName());
            preparedStatement.setInt(4, match.getCompetition().getYear());
            preparedStatement.setDate(5, java.sql.Date.valueOf(match.getOccurrenceDate()));
            preparedStatement.setBoolean(6, match.isBlueWin());
            if (match.getReplayLink() == null || match.getReplayLink().isEmpty()) {
                preparedStatement.setNull(7, Types.NULL);
            } else {
                preparedStatement.setString(7, match.getReplayLink());
            }
            preparedStatement.setString(8, match.getSummary());
            preparedStatement.setInt(9, match.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new UpdateMatchException("No match found with the id: " + match.getId());
            }
        } catch (SQLException e) {
            throw new UpdateMatchException("An error occurred while updating the match" );
        }
    }

    @Override
    public void deleteMatch(Integer matchId) throws DeleteMatchException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "DELETE FROM `match` WHERE id = ?");
            preparedStatement.setInt(1, matchId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DeleteMatchException("No match found with the id: " + matchId);
            }
        } catch (SQLException e) {
            throw new DeleteMatchException("An error occurred while deleting the match");
        }
    }

    @Override
    public ArrayList<Match> getAllMatchs() throws ReadMatchException {
        ArrayList<Match> matches = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM `match`"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String teamBlueName = resultSet.getString("team_blue");
                String teamRedName = resultSet.getString("team_red");
                int matchID = resultSet.getInt("id");
                LocalDate creationDate = resultSet.getDate("creation_date").toLocalDate();
                LocalDate occurrenceDate = resultSet.getDate("occurrence_date").toLocalDate();
                String competitionName = resultSet.getString("competition_name");
                int competitionYear = resultSet.getInt("competition_year");
                boolean isBlueWin = resultSet.getBoolean("is_blue_win");
                String replayLink = resultSet.getString("replay_link");
                String summary = resultSet.getString("summary");
                Match match = new Match(
                        matchID,
                        teamBlueName,
                        teamRedName,
                        competitionName,
                        competitionYear,
                        creationDate,
                        occurrenceDate,
                        isBlueWin,
                        replayLink,
                        summary
                );
                matches.add(match);
            }
        } catch (SQLException e) {
            throw new ReadMatchException("An error occurred while reading the matchs");
        }
        return matches;
    }

    private Match resultSetToMatch(ResultSet resultSet) throws ReadMatchException, ReadTeamException, ReadCompetitionException{
        TeamDBAccess teamDBAccess = new TeamDBAccess();
        CompetitionDBAccess competitionDBAccess = new CompetitionDBAccess();
        try {
            return new Match(
                    resultSet.getInt("id"),
                    teamDBAccess.getTeam(resultSet.getString("team_blue")),
                    teamDBAccess.getTeam(resultSet.getString("team_red")),
                    competitionDBAccess.getCompetition(resultSet.getString("competition_name"), resultSet.getInt("competition_year")),
                    resultSet.getDate("creation_date").toLocalDate(),
                    resultSet.getDate("occurrence_date").toLocalDate(),
                    resultSet.getBoolean("is_blue_win"),
                    resultSet.getString("replay_link"),
                    resultSet.getString("summary")
            );
        } catch (SQLException e) {
            throw new ReadMatchException("An error occurred while reading the match" );
        } catch (ReadTeamException e) {
            throw new ReadTeamException("Erreur lors de la lecture de l'équipe");
        } catch (ReadCompetitionException e) {
            throw new ReadCompetitionException("An error occurred while reading the match");
        }
    }

}
