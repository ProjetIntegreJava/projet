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
            if (getMatch(match.getId()) != null ){
                throw new AddMatchException("L'équpe existe déjà");
            }
        } catch (ReadMatchException e) {
            throw new AddMatchException("Une erreur c'est produite");
        }
        dao.addMatch(match);
    }
    public Match getMatch(int matchId) throws ReadMatchException {
        try {
            return dao.getMatch(matchId);
        } catch (ReadMatchException e) {
            throw new ReadMatchException("Une erreur s'est produite lors de la lecture du match");
        }
    }

    public void updateMatch(Match match) throws UpdateMatchException {
        try {
            if (getMatch(match.getId()) == null) {
                throw new UpdateMatchException("Match inexistant");
            }
        } catch (ReadMatchException e) {
            throw new UpdateMatchException("Une erreur s'est produite");
        }
        dao.updateMatch(match);
    }
    public void deleteMatches(ArrayList<Integer> matchId) throws DeleteMatchException {
        for (int id : matchId) {
            try {
                if (getMatch(id) == null) {
                    throw new DeleteMatchException("Équipe inexistant");
                }
            } catch (ReadMatchException e) {
                throw new DeleteMatchException("Une erreur s'est produite");
            }
        }
        dao.deleteMatchs(matchId);
    }

    public ArrayList<Match> getAllMatches() throws ReadMatchException{
        return dao.getAllMatchs();
    }
}
