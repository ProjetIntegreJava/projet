package businessPackage;

import dataAccess.TeamDataAccess.*;
import exceptionPackage.Team.*;
import modelPackage.Team;

import java.util.ArrayList;

public class TeamManager {
    private final TeamDataAccess dao;

    public TeamManager() {
        this.dao = new TeamDBAccess();
    }

    public void addTeam(Team team) throws AddTeamException {
        try {
            if (getTeam(team.getName()) != null){
                throw new AddTeamException("L'équipe existe déjà");
            }
        } catch (ReadTeamException e) {
            dao.addTeam(team);
        }
    }
    public Team getTeam(String name) throws ReadTeamException {
        return dao.getTeam(name);
    }

    public void updateTeam(String name, Team team) throws UpdateTeamException {
        try {
            if (getTeam(name) == null) {
                throw new UpdateTeamException("The team does not exist");
            }
        } catch (ReadTeamException e) {
            throw new UpdateTeamException("An error occurred while reading the teams");
        }
        dao.updateTeam(name, team);
    }
    public void deleteTeam(String nameTeam) throws DeleteTeamsException {
        try {
            if (getTeam(nameTeam) == null) {
                throw new DeleteTeamsException("The team does not exist");
            }
        } catch (ReadTeamException e) {
            throw new DeleteTeamsException("An error occurred while reading the teams");
        }
        dao.deleteTeam(nameTeam);
    }

    public ArrayList<Team> getAllTeams() throws ReadTeamException{
        return dao.getAllTeams();
    }
}
