package dataAccess.AkinatorDataAccess;

import exceptionPackage.TeamHistoryException.ReadTeamHistoryException;
import modelPackage.Team;
import modelPackage.TeamHistory;

import java.util.ArrayList;

public interface AkinatorDataAccess {
    ArrayList<TeamHistory> getTeamsHistory(ArrayList<Team> teams) throws ReadTeamHistoryException;
}
