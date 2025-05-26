package dataAccess.TeamDataAccess;

import exceptionPackage.Team.*;
import modelPackage.Team;

import java.util.ArrayList;

public interface TeamDataAccess {
    void addTeam(Team team) throws AddTeamException;
    Team getTeam(String nameTeam) throws ReadTeamException;
    void updateTeam(Team team) throws UpdateTeamException;

    void deleteTeams(ArrayList<String> nameTeam) throws DeleteTeamsException;
    ArrayList<Team> getAllTeams() throws ReadTeamException;
}
