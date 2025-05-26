package modelPackage.searches;

public class ResultParticipationData {
    private final String firstName, lastName;
    private final String championName, championRace, role;
    private final Integer kills, deaths, assists, goldEarned, damageReceived, damage, wardScore, creepScore;

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getChampionName() {
        return championName;
    }

    public String getChampionRace() {
        return championRace;
    }

    public String getRole() {
        return role;
    }

    public Integer getKills() {
        return kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public Integer getGoldEarned() {
        return goldEarned;
    }

    public Integer getDamageReceived() {
        return damageReceived;
    }

    public Integer getDamage() {
        return damage;
    }

    public Integer getWardScore() {
        return wardScore;
    }

    public Integer getCreepScore() {
        return creepScore;
    }
}
