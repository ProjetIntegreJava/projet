package businessPackage;

import dataAccess.PlayerRankingForMatchDataAccess.PlayerRankingForMatchDBAccess;
import dataAccess.PlayerRankingForMatchDataAccess.PlayerRankingForMatchDataAccess;
import exceptionPackage.PlayerRankingForMatch.ReadPlayerRankingForMatchException;
import modelPackage.Participation;

import java.util.ArrayList;

public class PlayerRankingForMatchManager {
    private PlayerRankingForMatchDataAccess dao;
    public PlayerRankingForMatchManager() {
        this.dao = new PlayerRankingForMatchDBAccess();
    }
    public ArrayList<Participation> getPlayerRankingForMatch(int matchId) throws ReadPlayerRankingForMatchException {
        ArrayList<Participation> allPlayer = new ArrayList<>();
        allPlayer = dao.getPlayerRankingForMatch(matchId);
        return sortByScore(allPlayer);
    }

    private ArrayList<Participation> sortByScore(ArrayList<Participation> allPlayer) {
        for (Participation participation : allPlayer) {
            participation.setScore(calculateScore(participation));
        }
        allPlayer.sort((p1, p2) -> {
            Float score1 =  p1.getScore();
            Float score2 = p2.getScore();
            return Float.compare(score2, score1);
        });
        return allPlayer;
    }

    private Float calculateScore(Participation participation) {
        return (float) (participation.getKills() + participation.getAssists() / 2.0 - participation.getDeath());
    }
}
