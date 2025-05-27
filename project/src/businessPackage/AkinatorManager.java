package businessPackage;

import dataAccess.AkinatorDataAccess.AkinatorDBAccess;
import dataAccess.AkinatorDataAccess.AkinatorDataAccess;
import dataAccess.TeamDataAccess.TeamDBAccess;
import dataAccess.TeamDataAccess.TeamDataAccess;
import exceptionPackage.Team.ReadTeamException;
import exceptionPackage.TeamHistoryException.ReadTeamHistoryException;
import modelPackage.Team;
import modelPackage.TeamHistory;

import java.util.ArrayList;

public class AkinatorManager {
    private ArrayList<Team> teams;
    private AkinatorDataAccess daoTeamHistory;
    private TeamDataAccess daoTeam;

    public AkinatorManager() {
        this.teams = new ArrayList<>();
        this.daoTeamHistory = new AkinatorDBAccess();
        this.daoTeam = new TeamDBAccess();
    }

    private void getAllTeams() throws ReadTeamException {
        teams = daoTeam.getAllTeams();
    }

    private ArrayList<TeamHistory> getTeamsHistory(ArrayList<TeamHistory> teamHistories) throws ReadTeamHistoryException {
        return daoTeamHistory.getTeamsHistory(teams);
    }
    public int getNextQuestion(Boolean answer){
        if (answer){
            return 1; // Assuming 1 is the next question ID for a "yes" answer
        } else {
            return 2; // Assuming 2 is the next question ID for a "no" answer
        }

    }
}

