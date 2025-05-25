package dataAccess.MatchDataAccess;

import exceptionPackage.Match.*;
import modelPackage.Match;

import java.util.ArrayList;

public interface MatchDataAccess {
    Match getMatch(int matchId) throws ReadMatchException;

    void addMatch(Match match) throws AddMatchException;

    void updateMatch(Match match) throws UpdateMatchException;

    void deleteMatchs(ArrayList<Integer> matchId) throws DeleteMatchException;

    ArrayList<Match> getAllMatchs() throws ReadMatchException;
}
