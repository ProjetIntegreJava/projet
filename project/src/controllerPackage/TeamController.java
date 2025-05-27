package controllerPackage;

import businessPackage.TeamManager;
import exceptionPackage.Team.*;
import modelPackage.Team;

import java.util.ArrayList;

public class TeamController {
    TeamManager teamManager;
    public TeamController() {
        teamManager = new TeamManager();
    }
    public void addTeam(Team team) throws AddTeamException {
        teamManager.addTeam(team);
    }
    public void updateTeam(String name, Team team) throws UpdateTeamException{
        teamManager.updateTeam(name, team);
    }
    public void deleteTeams(ArrayList<String> nameTeam) throws DeleteTeamsException{
        teamManager.deleteTeams(nameTeam);
    }
    public Team getTeam(String name) throws ReadTeamException{
        return teamManager.getTeam(name);
    }

    public ArrayList<Team> getAllTeams() throws ReadTeamException{
        return teamManager.getAllTeams();
    }
}
