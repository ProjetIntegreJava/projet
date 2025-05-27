package modelPackage;

import javax.sound.midi.spi.MidiDeviceProvider;

public class Participation {
    private Player player;
    private Match match;
    private Role role;
    private Champion champion;
    private int kills;
    private int assists;
    private int death;
    private int creepScore;
    private int damage;
    private int wardScore;
    private int goldEarn;
    private int damageReceived;

    public Participation(Player player, Match match, Role role, Champion champion, int kills, int assists, int death, int creepScore, int damage, int wardScore, int goldEarn, int damageReceived){
        setPlayer(player);
        setMatch(match);
        setRole(role);
        setChampion(champion);
        setKills(kills);
        setAssists(assists);
        setDeath(death);
        setCreepScore(creepScore);
        setDamage(damage);
        setWardScore(wardScore);
        setGoldEarn(goldEarn);
        setDamageReceived(damageReceived);
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setMatch(Match match) {
        this.match = match;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setChampion(Champion champion) {
        this.champion = champion;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }
    public void setAssists(int assists) {
        this.assists = assists;
    }
    public void setDeath(int death) {
        this.death = death;
    }
    public void setCreepScore(int creepScore) {
        this.creepScore = creepScore;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setWardScore(int wardScore) {
        this.wardScore = wardScore;
    }
    public void setGoldEarn(int goldEarn) {
        this.goldEarn = goldEarn;
    }
    public void setDamageReceived(int damageReceived) {
        this.damageReceived = damageReceived;
    }
    public Player getPlayer() {
        return player;
    }

    public Match getMatch() {
        return match;
    }
    public Role getRole() {
        return role;
    }
    public Champion getChampion() {
        return champion;
    }
    public int getKills() {
        return kills;
    }

    public int getAssists() {
        return assists;
    }
    public int getDeath() {
        return death;
    }
    public int getCreepScore() {
        return creepScore;
    }
    public int getDamage() {
        return damage;
    }
    public int getWardScore() {
        return wardScore;
    }
    public int getGoldEarn() {
        return goldEarn;
    }
    public int getDamageReceived() {
        return damageReceived;
    }
}
