package controllerPackage;

import businessPackage.PlayerRankingForMatchManager;
import exceptionPackage.PlayerRankingForMatch.ReadPlayerRankingForMatchException;
import modelPackage.Participation;

import java.util.ArrayList;

public class PlayerRankingForMatchController {
    private PlayerRankingForMatchManager rankingManager;
    public ArrayList<Participation> getPlayerRankingForMatch(int matchId) throws ReadPlayerRankingForMatchException {
        return rankingManager.getPlayerRankingForMatch(matchId);
    }
}
