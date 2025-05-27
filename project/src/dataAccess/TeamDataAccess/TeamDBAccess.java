package dataAccess.TeamDataAccess;

import dataAccess.ClubDataAccess.ClubDBAccess;
import dataAccess.ClubDataAccess.ClubDataAccess;
import dataAccess.SingletonConnection;
import exceptionPackage.Club.ReadClubException;
import exceptionPackage.Team.*;
import modelPackage.Club;
import modelPackage.Region;
import modelPackage.Team;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamDBAccess implements TeamDataAccess{
    public TeamDBAccess(){ }

    @Override
    public void addTeam(Team team) throws AddTeamException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "INSERT INTO team(`name`, club, region, founding_date, has_been_world_champion, `description`, nb_followers) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, team.getName());
            preparedStatement.setString(2, team.getClub().getName());
            preparedStatement.setString(3, team.getRegion().getName());
            preparedStatement.setDate(4, Date.valueOf(team.getFoundingDate()));
            preparedStatement.setBoolean(5, team.hasBeenWorldChampion());
            preparedStatement.setString(6, team.getDescription());
            preparedStatement.setInt(7, team.getNbFollowers());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new AddTeamException("Une erreur s'est produite lors de l'ajout de l'équipe");
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
                } catch (ReadTeamException | ReadClubException e) {
                    throw new ReadTeamException("Erreur lors de la lecture de l'équipe");
                }
            } else {
                throw new ReadTeamException("Équipe non trouvée");
            }
        } catch (SQLException e) {
            throw new ReadTeamException("Une erreur s'est produite lors de la lecture de l'équipe: " + e.getMessage());
        }
    }

    @Override
    public void updateTeam(Team team) throws UpdateTeamException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "UPDATE team SET club = ?, region = ?, founding_date = ?, has_been_world_champion = ?, description = ?, nb_followers = ? WHERE name = ?");
            preparedStatement.setString(1, team.getClub().getName());
            preparedStatement.setString(2, team.getRegion().getName());
            preparedStatement.setDate(3, Date.valueOf(team.getFoundingDate()));
            preparedStatement.setBoolean(4, team.hasBeenWorldChampion());
            preparedStatement.setString(5, team.getDescription());
            preparedStatement.setInt(6, team.getNbFollowers());
            preparedStatement.setString(7, team.getName());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new UpdateTeamException("Aucune équipe mise à jour");
            }
        } catch (SQLException e) {
            throw new UpdateTeamException("Une erreur s'est produite lors de la mise à jour de l'équipe");
        }
    }

    @Override
    public void deleteTeams(ArrayList<String> nameTeam) throws DeleteTeamsException {
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    "DELETE FROM team WHERE name = ?");
            for (String name : nameTeam) {
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DeleteTeamsException("Aucune équipe supprimée pour le nom: " + name);
                }
            }
        } catch (SQLException e) {
            throw new DeleteTeamsException("Une erreur s'est produite lors de la suppression des équipes");
        }
    }

    @Override
    public ArrayList<Team> getAllTeams() throws ReadTeamException {
        ArrayList<Team> teams = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement(
                    """
                            SELECT\s
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
                            JOIN club c ON t.club = c.name;""");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                try {
                    teams.add(resultSetToTeam(resultSet));
                } catch (ReadTeamException | ReadClubException e) {
                    throw new ReadTeamException("Erreur lors de la lecture d'une équipe");
                }
            }
        } catch (SQLException e) {
            throw new ReadTeamException("Une erreur s'est produite lors de la lecture des équipes: " + e.getMessage());
        }
        return teams;
    }
    private Team resultSetToTeam(ResultSet resultSet) throws ReadTeamException, ReadClubException {
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
            throw new ReadTeamException("Erreur lors de la lecture d'un compte");
        }
    }
}
