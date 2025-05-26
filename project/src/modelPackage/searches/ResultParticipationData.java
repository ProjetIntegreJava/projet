package modelPackage.searches;

public class ResultParticipationData {
    public final String firstName, lastName;
    public final String championName, championRace, role;
    public final Integer kills, deaths, assists, goldEarned, damageReceived, damage, wardScore, creepScore;

    public ResultParticipationData(
            String firstName, String lastName, String championName, String championRace, String role,
            Integer kills, Integer deaths, Integer assists, Integer goldEarned, Integer damageReceived,
            Integer damage, Integer wardScore, Integer creepScore
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.championName = championName;
        this.championRace = championRace;
        this.role = role;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.goldEarned = goldEarned;
        this.damageReceived = damageReceived;
        this.damage = damage;
        this.wardScore = wardScore;
        this.creepScore = creepScore;
    }
}
