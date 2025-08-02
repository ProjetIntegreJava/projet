package dataAccess.AkinatorDataAccess;

import dataAccess.PlayerDataAccess.PlayerDBAccess;
import dataAccess.PlayerDataAccess.PlayerDataAccess;
import dataAccess.SingletonConnection;
import dataAccess.TeamDataAccess.TeamDBAccess;
import dataAccess.TeamDataAccess.TeamDataAccess;
import exceptionPackage.Player.ReadPlayerException;
import exceptionPackage.Team.ReadTeamException;
import exceptionPackage.TeamHistoryException.ReadTeamHistoryException;
import modelPackage.Player;
import modelPackage.Team;
import modelPackage.TeamHistory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AkinatorDBAccess implements AkinatorDataAccess{
public AkinatorDBAccess() { }

    @Override
    public ArrayList<TeamHistory> getTeamsHistory(ArrayList<Team> teams) throws ReadTeamHistoryException {
        ArrayList<TeamHistory> teamHistories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM team_history WHERE team_name = ?");
            for (Team team : teams) {
                preparedStatement.setString(1, team.getName());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    try {
                        teamHistories.add(resultSetToTeamHistory(resultSet));
                    } catch (ReadTeamHistoryException e) {
                        throw new ReadTeamHistoryException("Error converting ResultSet to TeamHistory for team: " + team.getName());
                    }
                }
            }

        }catch (SQLException e) {
            throw new ReadTeamHistoryException("Error reading team history for team: ");
        }
        return teamHistories;
    }

    private TeamHistory resultSetToTeamHistory(ResultSet resultSet) throws ReadTeamHistoryException{
        PlayerDataAccess playerDataAccess = new PlayerDBAccess();
        TeamDataAccess teamDataAccess = new TeamDBAccess();
        Player[] players = new Player[5];
        try{
            players[0] = playerDataAccess.getPlayerById(resultSet.getInt("id_player_one"));
            players[1] = playerDataAccess.getPlayerById(resultSet.getInt("id_player_two"));
            players[2] = playerDataAccess.getPlayerById(resultSet.getInt("id_player_three"));
            players[3] = playerDataAccess.getPlayerById(resultSet.getInt("id_player_four"));
            players[4] = playerDataAccess.getPlayerById(resultSet.getInt("id_player_five"));
            return new TeamHistory(
                    players,
                    teamDataAccess.getTeam(resultSet.getString("team")),
                    resultSet.getDate("beginning_date").toLocalDate(),
                    resultSet.getDate("ending_date").toLocalDate()
            );
        } catch (SQLException | ReadPlayerException | ReadTeamException e) {
            throw new ReadTeamHistoryException("Error converting ResultSet to TeamHistory");
        }
    }
}
