package dataAccess.PlayerRankingForMatchDataAccess;

import dataAccess.MatchDataAccess.MatchDBAccess;
import dataAccess.PlayerDataAccess.PlayerDBAccess;
import dataAccess.SingletonConnection;
import exceptionPackage.Match.ReadMatchException;
import exceptionPackage.Player.ReadPlayerException;
import exceptionPackage.PlayerRankingForMatch.ReadPlayerRankingForMatchException;
import modelPackage.Champion;
import modelPackage.Participation;
import modelPackage.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerRankingForMatchDBAccess implements PlayerRankingForMatchDataAccess{
    @Override
    public ArrayList<Participation> getPlayerRankingForMatch(int matchId) throws ReadPlayerRankingForMatchException {
        ArrayList<Participation> allPlayerForTheGame = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM participation WHERE match_id = ?");
            preparedStatement.setInt(1, matchId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allPlayerForTheGame.add(resultSetToParticipation(resultSet));
            }
        } catch (SQLException e) {
            throw new ReadPlayerRankingForMatchException("Une erreur s'est produite lors de la lecture des participation des joueurs pour le match");
        }
        return allPlayerForTheGame;
    }

    private Participation resultSetToParticipation(ResultSet resultSet) throws ReadPlayerRankingForMatchException {
        PlayerDBAccess playerDBAccess = new PlayerDBAccess();
        MatchDBAccess matchDBAccess = new MatchDBAccess();
        try {
            return new Participation(
                    playerDBAccess.getPlayerById(resultSet.getInt("player_id")),
                    matchDBAccess.getMatch(resultSet.getInt("match_id")),
                    new Role(resultSet.getString("role")),
                    new Champion(resultSet.getString("champion")),
                    resultSet.getInt("kills"),
                    resultSet.getInt("assists"),
                    resultSet.getInt("deaths"),
                    resultSet.getInt("creep_score"),
                    resultSet.getInt("damage"),
                    resultSet.getInt("ward_score"),
                    resultSet.getInt("gold_earned"),
                    resultSet.getInt("damage_received")
            );
        } catch (SQLException | ReadPlayerException | ReadMatchException e) {
            throw new ReadPlayerRankingForMatchException("Une erreur s'est produite lors de la lecture de la participation du joueur");
        }
    }

}
