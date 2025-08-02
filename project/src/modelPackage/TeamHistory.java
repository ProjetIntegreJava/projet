package modelPackage;

import java.time.LocalDate;

public class TeamHistory {
    public static final int MAX_PLAYER = 5;
    private Player[] player = new Player[MAX_PLAYER];
    private Team team;
    private LocalDate beginningDate;
    private LocalDate endingDate;
    public TeamHistory(Player[] player, Team team, LocalDate beginningDate, LocalDate endingDate){
        setPlayer(0, player[0]);
        setPlayer(1, player[1]);
        setPlayer(2, player[2]);
        setPlayer(3, player[3]);
        setPlayer(4, player[4]);
        setTeam(team);
        setBeginningDate(beginningDate);
        setEndingDate(endingDate);
    }
    public Player getPlayer(int index) {
        return this.player[index];
    }
    public Player[] getAllPlayers() {
        return this.player;
    }
    public Team getTeam() {
        return this.team;
    }
    public LocalDate getBeginningDate() {
        return this.beginningDate;
    }
    public LocalDate getEndingDate() {
        return this.endingDate;
    }
    public void setPlayer(int index, Player player) {
        this.player[index] = player;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }
    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }
}
