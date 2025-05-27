package businessPackage;

import dataAccess.TeamDataAccess.*;
import exceptionPackage.Team.*;
import modelPackage.Team;

import java.util.ArrayList;

public class TeamManager {
    private TeamDataAccess dao;

    public TeamManager() {
        this.dao = new TeamDBAccess();
    }

    public void addTeam(Team team) throws AddTeamException {
        try {
            if (getTeam(team.getName()) != null){
                throw new AddTeamException("L'équpe existe déjà");
            }
        } catch (ReadTeamException e) {
            throw new AddTeamException("Une erreur c'est produite");
        }
        dao.addTeam(team);
    }
    public Team getTeam(String name) throws ReadTeamException {
        try {
            return dao.getTeam(name);
        } catch (ReadTeamException e) {
            throw new ReadTeamException("Une erreur s'est produite lors de la lecture de l'équipe");
        }
    }

    public void updateTeam(String name, Team team) throws UpdateTeamException {
        try {
            if (getTeam(name) == null) {
                throw new UpdateTeamException("Équipe inexistant");
            }
        } catch (ReadTeamException e) {
            throw new UpdateTeamException("Une erreur s'est produite");
        }
        dao.updateTeam(name, team);
    }
    public void deleteTeams(ArrayList<String> nameTeam) throws DeleteTeamsException {
        ArrayList<String> nameTeamList = new ArrayList<>();
        for (String name : nameTeam) {
            try {
                if (getTeam(name) == null) {
                    throw new DeleteTeamsException("Équipe inexistant");
                }
                nameTeamList.add(name);
            } catch (ReadTeamException e) {
                throw new DeleteTeamsException("Une erreur s'est produite");
            }
        }
        dao.deleteTeams(nameTeamList);
    }

    public ArrayList<Team> getAllTeams() throws ReadTeamException{
        return dao.getAllTeams();
    }
}
