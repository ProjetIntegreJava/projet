package controllerPackage;

import businessPackage.MatchManager;
import exceptionPackage.Match.AddMatchException;
import exceptionPackage.Match.DeleteMatchException;
import exceptionPackage.Match.ReadMatchException;
import exceptionPackage.Match.UpdateMatchException;
import modelPackage.Match;

import java.util.ArrayList;

public class MatchController {
    MatchManager matchManager;
    public MatchController() {
        matchManager = new MatchManager();
    }
    public Match getMatch(int matchId) throws ReadMatchException {
        return matchManager.getMatch(matchId);
    }

    public void addMatch(Match match) throws AddMatchException {
        matchManager.addMatch(match);
    }
    public void updateMatch(Match match) throws UpdateMatchException {
        matchManager.updateMatch(match);
    }
    public void deleteMatches(ArrayList<Integer> matchId) throws DeleteMatchException {
        matchManager.deleteMatches(matchId);
    }
    public ArrayList<Match> getAllMatches() throws ReadMatchException{
        return matchManager.getAllMatches();
    }
}
