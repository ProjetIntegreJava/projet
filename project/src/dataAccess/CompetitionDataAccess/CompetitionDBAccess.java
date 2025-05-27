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
import java.util.ArrayList;

public class CompetitionDBAccess implements CompetitionDataAccess{
    @Override
    public Competition getCompetition(String name, int year) throws ReadCompetitionException {
        try {
            PreparedStatement statement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM competition WHERE name = ? AND year = ?");
            statement.setString(1, name);
            statement.setInt(2, year);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                try {
                    return resultSetToCompetition(resultSet);
                } catch (ReadCompetitionException e) {
                    throw new ReadCompetitionException("Error while reading competition data");
                }
            } else {
                throw new ReadCompetitionException("The competition with name " + name + " and year " + year + " does not exist.");
            }
        } catch (SQLException e) {
            throw new ReadCompetitionException("An error occurred while reading the competition");
        }
    }
    @Override
    public ArrayList<Competition> getAllCompetitions() throws ReadCompetitionException {
        ArrayList<Competition> competitions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement("SELECT * FROM competition");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                competitions.add(resultSetToCompetition(resultSet));
            }
        } catch (SQLException e) {
            throw new ReadCompetitionException("An error occurred while reading the competition");
        }
        return competitions;
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
            throw new ReadCompetitionException("An error occurred while reading the competition");
        }
    }

}
