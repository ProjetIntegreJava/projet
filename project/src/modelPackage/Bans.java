package modelPackage;

public class Bans {
    private Champion champion;
    private Team team;
    private Match idMatch;
    public Bans(Champion champion, Team team, Match idMatch){
        this.champion = champion;
        this.team = team;
        this.idMatch = idMatch;
    }
}
