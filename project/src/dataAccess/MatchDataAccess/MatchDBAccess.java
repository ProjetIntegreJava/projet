package dataAccess.MatchDataAccess;

import dataAccess.CompetitionDataAccess.CompetitionDBAccess;
import dataAccess.SingletonConnection;
import dataAccess.TeamDataAccess.TeamDBAccess;
import exceptionPackage.Competition.ReadCompetitionException;
import exceptionPackage.Match.*;
import exceptionPackage.Team.ReadTeamException;
import modelPackage.Match;
import modelPackage.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MatchDBAccess implements MatchDataAccess{
    @Override
    public void addMatch(Match match) throws AddMatchException {
        try{
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "INSERT INTO `match`(team_blue, team_red, competition_name, competition_year, occurence_date, is_blue_win, replay_link, summary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, match.getTeamBlue().getName());
            preparedStatement.setString(2, match.getTeamRed().getName());
            preparedStatement.setString(3, match.getCompetition().getName());
            preparedStatement.setInt(4, match.getCompetition().getYear());
            preparedStatement.setDate(5, java.sql.Date.valueOf(match.getOccurrenceDate()));
            preparedStatement.setBoolean(6, match.isBlueWin());
            preparedStatement.setString(7, match.getReplayLink());
            preparedStatement.setString(8, match.getSummary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AddMatchException("Une erreur s'est produite lors de l'ajout du match");
        }
    }
    @Override
    public Match getMatch(int matchId) throws ReadMatchException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM `match` WHERE id = ?");
            preparedStatement.setInt(1, matchId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSetToMatch(resultSet);
            } else {
                throw new ReadMatchException("Aucun match trouvé");
            }
        } catch (SQLException e) {
            throw new ReadMatchException("Une erreur s'est produite lors de la lecture du match");
        } catch (ReadTeamException e) {
            throw new ReadMatchException("Erreur lors de la lecture de l'équipe");
        } catch (ReadCompetitionException e) {
            throw new ReadMatchException("Erreur lors de la lecture de la compétition");
        }
    }



    @Override
    public void updateMatch(Match match) throws UpdateMatchException {
        // Implementation for updating a match in the database
    }

    @Override
    public void deleteMatchs(ArrayList<Integer> matchId) throws DeleteMatchException {
        // Implementation for deleting matches from the database
    }

    @Override
    public ArrayList<Match> getAllMatchs() throws ReadMatchException {
        ArrayList<Match> matches = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT `match`.occurrence_date, `match`.id, `match`.team_blue, `match`.team_red FROM `match`"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String teamBlueName = resultSet.getString("team_blue");
                String teamRedName = resultSet.getString("team_red");
                int matchID = resultSet.getInt("id");
                LocalDate occurrenceDate = resultSet.getDate("occurrence_date").toLocalDate();
                Match match = new Match(
                        matchID,
                        new Team(teamBlueName),
                        new Team(teamRedName),
                        null, // Competition will be set later
                        null, // Creation date will be set later
                        occurrenceDate,
                        false, // isBlueWin will be set later
                        null, // replayLink will be set later
                        null  // summary will be set later
                );
                matches.add(match);
            }
        } catch (SQLException e) {
            throw new ReadMatchException("Une erreur s'est produite lors de la lecture des matchs");
        }
        // Implementation for getting all matches from the database
        return matches; // Placeholder return
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
                    resultSet.getDate("occurence_date").toLocalDate(),
                    resultSet.getBoolean("is_blue_win"),
                    resultSet.getString("replay_link"),
                    resultSet.getString("summary")
            );
        } catch (SQLException e) {
            throw new ReadMatchException("Une erreur s'est produite lors de la lecture du match");
        } catch (ReadTeamException e) {
            throw new ReadTeamException("Erreur lors de la lecture de l'équipe");
        } catch (ReadCompetitionException e) {
            throw new ReadCompetitionException("Erreur lors de la lecture de la compétition");
        }
    }

}
