package dataAccess.ClubDataAccess;

import dataAccess.SingletonConnection;
import exceptionPackage.Club.*;
import modelPackage.Club;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubDBAccess implements ClubDataAccess{
    public ClubDBAccess() { }
    @Override
    public Club getClub(String nameClub) throws ReadClubException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM Club WHERE name = ?");
            preparedStatement.setString(1, nameClub);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                try {
                    return resultSetToClub(resultSet);
                } catch (ReadClubException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }catch (SQLException e) {
            throw new ReadClubException("Une erreur s'est produite lors de la lecture du club");
        }
    }

    private Club resultSetToClub(ResultSet resultSet) throws ReadClubException {
        try {
            return new Club(
                    resultSet.getString("name"),
                    resultSet.getString("CEO"),
                    resultSet.getString("nationality")
            );
        } catch (SQLException e) {
            throw new ReadClubException("Une erreur s'est produite lors de la conversion du résultat en club");
        }
    }

}
