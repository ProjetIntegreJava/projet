package dataAccess.PlayerRankingForMatchDataAccess;

import exceptionPackage.Player.ReadPlayerException;
import exceptionPackage.PlayerRankingForMatch.*;
import modelPackage.Participation;

import java.util.ArrayList;

public interface PlayerRankingForMatchDataAccess {
    ArrayList<Participation> getPlayerRankingForMatch(int matchId) throws ReadPlayerRankingForMatchException;
}
