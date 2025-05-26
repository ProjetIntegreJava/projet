package dataAccess.CompetitionDataAccess;

import dataAccess.SingletonConnection;
import exceptionPackage.Club.ReadClubException;
import exceptionPackage.Competition.*;
import exceptionPackage.Team.ReadTeamException;
import modelPackage.Competition;
import modelPackage.CompetitionLevel;
import modelPackage.Region;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompetitionDBAccess implements CompetitionDataAccess{
    @Override
    public Competition getCompetition(String name, int year) throws ReadCompetitionException {
        try {
            PreparedStatement statement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM competitions WHERE name = ? AND year = ?");
            statement.setString(1, name);
            statement.setInt(2, year);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                try {
                    return resultSetToCompetition(resultSet);
                } catch (ReadCompetitionException e) {
                    throw new ReadCompetitionException("Erreur lors de la lecture de l'équipe");
                }
            } else {
                throw new ReadCompetitionException("Équipe non trouvée");
            }
        } catch (SQLException e) {
            throw new ReadCompetitionException("Une erreur s'est produite lors de la lecture de l'équipe: " + e.getMessage());
        }
    }

    private Competition resultSetToCompetition(ResultSet resultSet) throws ReadCompetitionException{
        try {
            return new Competition(
            resultSet.getString("name"),
            resultSet.getInt("year"),
            new Region("region"),
            new CompetitionLevel("level")
            );
        } catch (SQLException e) {
            throw new ReadCompetitionException("Une erreur s'est produite lors de la lecture de la compétition");
        }
    }

}
