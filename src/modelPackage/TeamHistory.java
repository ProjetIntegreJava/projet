package modelPackage;

import java.time.LocalDate;

public class TeamHistory {
    private Player player;
    private Team team;
    private LocalDate beginningDate;
    private LocalDate endingDate;
    public TeamHistory(Player[] player, Team team, LocalDate beginningDate, LocalDate endingDate){
        setPlayer(player);
        setTeam(team);
        setBeginningDate(beginningDate);
        setEndingDate(endingDate);
    }
    public Player getPlayer() {
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
    public void setPlayer(Player[] player) {
        this.player = this.player;
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
