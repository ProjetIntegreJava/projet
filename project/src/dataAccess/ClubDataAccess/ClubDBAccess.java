package dataAccess.ClubDataAccess;

import dataAccess.SingletonConnection;
import exceptionPackage.Club.*;
import modelPackage.Club;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClubDBAccess implements ClubDataAccess {
    public ClubDBAccess() { }
    @Override
    public Club getClub(String nameClub) throws ReadClubException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM club WHERE name = ?");
            preparedStatement.setString(1, nameClub);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSetToClub(resultSet);
            }
            throw new ReadClubException("No club found with the name: " + nameClub);
        } catch (SQLException e) {
            throw new ReadClubException("An error occurred while reading the clubs");
        }
    }
    @Override
    public ArrayList<Club> getClubs() throws ReadClubException {
        ArrayList<Club> clubs = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT * FROM club");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clubs.add(resultSetToClub(resultSet));
            }
            if (clubs.isEmpty()) {
                throw new ReadClubException("The club list is empty");
            }
        } catch (SQLException e) {
            throw new ReadClubException("An error occurred while reading the clubs");
        }
        return clubs;
    }

    @Override
    public void addClub(Club club) throws AddClubException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "INSERT INTO club(name, nationality, ceo) VALUES (?, ?, ?)");
            preparedStatement.setString(1, club.getName());
            preparedStatement.setString(2, club.getNationality());
            preparedStatement.setString(3, club.getCEO());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AddClubException("An error occurred while reading the clubs");
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
            throw new ReadClubException("An error occurred while converting data");
        }
    }

}
