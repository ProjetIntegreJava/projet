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
            preparedStatement.setDate(5, java.sql.Date.valueOf(match.getOccurenceDate()));
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
    }

    @Override
    public void deleteMatchs(ArrayList<Integer> matchId) throws DeleteMatchException {
    }

    @Override
    public ArrayList<Match> getAllMatchs() throws ReadMatchException {
        return new ArrayList<>();
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
