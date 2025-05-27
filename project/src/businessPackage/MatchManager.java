package businessPackage;

import dataAccess.MatchDataAccess.MatchDBAccess;
import dataAccess.MatchDataAccess.MatchDataAccess;
import exceptionPackage.Match.*;
import modelPackage.Match;

import java.util.ArrayList;

public class MatchManager {
    private final MatchDataAccess dao;

    public MatchManager() {
        dao = new MatchDBAccess();
    }
    public void addMatch(Match match) throws AddMatchException {
        try {
            if (match.getId() != null)
                if (getMatch(match.getId()) != null ){
                    throw new AddMatchException("The match already exists");
                }
        } catch (ReadMatchException e) {
            throw new AddMatchException("An error occurred while reading the match");
        }
        dao.addMatch(match);
    }
    public Match getMatch(Integer matchId) throws ReadMatchException {
        try {
            return dao.getMatch(matchId);
        } catch (ReadMatchException e) {
            throw new ReadMatchException("An error occurred while reading the match");
        }
    }

    public void updateMatch(Match match) throws UpdateMatchException {
        try {
            if (getMatch(match.getId()) == null) {
                throw new UpdateMatchException("The match does not exist");
            }
        } catch (ReadMatchException e) {
            throw new UpdateMatchException("An error occurred while reading the match");
        }
        dao.updateMatch(match);
    }
    public void deleteMatch(Integer matchId) throws DeleteMatchException {
        try {
            if (getMatch(matchId) == null) {
                throw new DeleteMatchException("The match does not exist");
            }
        } catch (ReadMatchException e) {
            throw new DeleteMatchException("An error occurred while reading the match");
        }
        dao.deleteMatch(matchId);
    }

    public ArrayList<Match> getAllMatches() throws ReadMatchException{
        return dao.getAllMatchs();
    }
}
