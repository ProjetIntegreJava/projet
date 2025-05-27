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
        try {
            allPlayer = dao.getPlayerRankingForMatch(matchId);
        } catch (ReadPlayerRankingForMatchException e) {
            System.out.println("Une erreur s'est produite lors de la lecture des participations des joueurs pour le match : ");
        }
        return sortByScore(allPlayer);
    }

    private ArrayList<Participation> sortByScore(ArrayList<Participation> allPlayer) {
        allPlayer.sort((p1, p2) -> {
            Float score1 = calculateScore(p1);
            Float score2 = calculateScore(p2);
            return Float.compare(score2, score1);
        });
        return allPlayer;
    }

    private Float calculateScore(Participation participation) {
        return (float) (participation.getKills() + participation.getAssists() / 2.0 - participation.getDeath());
    }
}
