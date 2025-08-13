package dataAccess.TeamDataAccess;

import dataAccess.SingletonConnection;
import exceptionPackage.Team.*;
import modelPackage.Club;
import modelPackage.Region;
import modelPackage.Team;

import java.sql.*;
import java.util.ArrayList;

public class TeamDBAccess implements TeamDataAccess{
    public TeamDBAccess(){ }

    @Override
    public void addTeam(Team team) throws AddTeamException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "INSERT INTO team(`name`, club, region, founding_date, has_been_world_champion, `description`, nb_followers) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, team.getName());
            if (team.getClub() == null || team.getClub().getName() == null || team.getClub().getName().isEmpty()) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setString(2, team.getClub().getName());
            }
            if (team.getRegion() == null || team.getRegion().getName() == null || team.getRegion().getName().isEmpty()) {
                preparedStatement.setNull(3, Types.NULL);
            } else {
                preparedStatement.setString(3, team.getRegion().getName());
            }
            preparedStatement.setDate(4, Date.valueOf(team.getFoundingDate()));
            preparedStatement.setBoolean(5, team.hasBeenWorldChampion());
            preparedStatement.setString(6, team.getDescription());
            if (team.getNbFollowers() == null) {
                preparedStatement.setNull(7, Types.NULL);
            }
            else {
                preparedStatement.setInt(7, team.getNbFollowers());
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new AddTeamException("An error occurred while adding a player");
        }
    }

    @Override
    public Team getTeam(String nameTeam) throws ReadTeamException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "SELECT \n" +
                            "    t.name,\n" +
                            "    t.region,\n" +
                            "    t.creation_date,\n" +
                            "    t.founding_date,\n" +
                            "    t.has_been_world_champion,\n" +
                            "    t.description,\n" +
                            "    t.nb_followers,\n" +
                            "    c.name AS club_name,\n" +
                            "    c.CEO,\n" +
                            "    c.nationality\n" +
                            "FROM team t\n" +
                            "JOIN club c ON t.club = c.name\n" +
                            "WHERE t.name = ?;\n");
            preparedStatement.setString(1, nameTeam);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                try {
                    return resultSetToTeam(resultSet);
                } catch (ReadTeamException e) {
                    throw new ReadTeamException("An error occurred while deleting the player data");
                }
            } else {
                throw new ReadTeamException("Team not found: " + nameTeam);
            }
        } catch (SQLException e) {
            throw new ReadTeamException("An error occurred while reading the player data");
        }
    }

    @Override
    public void updateTeam(String name, Team team) throws UpdateTeamException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "UPDATE team SET club = ?, region = ?, founding_date = ?, has_been_world_champion = ?, description = ?, nb_followers = ?, name = ? WHERE name = ?");
            preparedStatement.setString(1, team.getClub().getName());
            preparedStatement.setString(2, team.getRegion().getName());
            preparedStatement.setDate(3, Date.valueOf(team.getFoundingDate()));
            preparedStatement.setBoolean(4, team.hasBeenWorldChampion());
            preparedStatement.setString(5, team.getDescription());
            preparedStatement.setInt(6, team.getNbFollowers());
            preparedStatement.setString(7, team.getName());
            preparedStatement.setString(8, name);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new UpdateTeamException("Couldn't update team: " + name + ". Team not found.");
            }
        } catch (SQLException e) {
            throw new UpdateTeamException("An error occurred while updating the team");
        }
    }

    @Override
    public void deleteTeam(String nameTeam) throws DeleteTeamsException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "DELETE FROM team WHERE `name` = ?");
            preparedStatement.setString(1, nameTeam);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DeleteTeamsException("No team found with the name: " + nameTeam);
            }
        } catch (SQLException e) {
            throw new DeleteTeamsException("An error occurred while deleting the player data");
        }
    }

    @Override
    public ArrayList<Team> getAllTeams() throws ReadTeamException {
        ArrayList<Team> teams = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement("""
                                SELECT
                                t.name,
                                t.region,
                                t.creation_date,
                                t.founding_date,
                                t.has_been_world_champion,
                                t.description,
                                t.nb_followers,
                                c.name AS club_name,
                                c.CEO,
                                c.nationality
                            FROM team t
                            JOIN club c ON t.club = c.name;
                            """);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                try {
                    teams.add(resultSetToTeam(resultSet));
                } catch (ReadTeamException e) {
                    throw new ReadTeamException("An error occurred while reading the player data");
                }
            }
        } catch (SQLException e) {
            throw new ReadTeamException("An error occurred while reading the player data");
        }
        return teams;
    }

    private Team resultSetToTeam(ResultSet resultSet) throws ReadTeamException{
        try{
            return new Team(
                    resultSet.getString("name"),
                    new Club(resultSet.getString("club_name"), resultSet.getString("CEO"), resultSet.getString("nationality")),
                    new Region(resultSet.getString("region")),
                    resultSet.getDate("creation_date").toLocalDate(),
                    resultSet.getDate("founding_date").toLocalDate(),
                    resultSet.getBoolean("has_been_world_champion"),
                    resultSet.getString("description"),
                    resultSet.getInt("nb_followers")
            );
        }catch (SQLException e){
            throw new ReadTeamException("An error occurred while deleting the player");
        }
    }



}
