package dataAccess.PlayerDataAccess;

import dataAccess.SingletonConnection;
import exceptionPackage.Match.ReadMatchException;
import exceptionPackage.Player.ReadPlayerException;
import modelPackage.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDBAccess implements PlayerDataAccess {

    @Override
    public Player getPlayerById(int playerId) throws ReadPlayerException {
        try{
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM player WHERE id = ?");
            preparedStatement.setInt(1, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSetToPlayer(resultSet);
            } else {
                throw new ReadPlayerException("Aucun match trouvé");
            }
        } catch (SQLException e) {
            throw new ReadPlayerException("An error occurred while reading the match");
        }
    }

    private Player resultSetToPlayer(ResultSet resultSet) throws ReadPlayerException {
        try {
            return new Player(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("pseudo"),
                    resultSet.getDate("birthdate").toLocalDate(),
                    resultSet.getString("nationality")
            );
        } catch (SQLException e) {
            throw new ReadPlayerException("An error occurred while reading the match");
        }
    }
}
