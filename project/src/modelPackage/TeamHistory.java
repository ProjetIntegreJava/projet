package modelPackage;

import java.time.LocalDate;

public class TeamHistory {
    public static final int MAX_PLAYER = 5;
    private int[] idPlayer = new int[MAX_PLAYER];
    private Team team;
    private LocalDate beginningDate;
    private LocalDate endingDate;
    public TeamHistory(int[] idPlayer, Team team, LocalDate beginningDate, LocalDate endingDate){
        this.idPlayer = idPlayer;
        this.team = team;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;

    }




}
