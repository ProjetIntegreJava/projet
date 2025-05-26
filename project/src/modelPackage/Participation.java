package modelPackage;

import javax.sound.midi.spi.MidiDeviceProvider;

public class Participation {
    private Player idPlayer;
    private Match idMatch;
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

    public  Participation(Player idPlayer, Match idMatch, Role role, Champion champion, int kills, int assists, int death, int creepScore, int damage, int wardScore, int goldEarn, int damageReceived){
        this.idPlayer = idPlayer;
        this.idMatch =idMatch;
        this.role = role;
        this.champion = champion;
        this.kills = kills;
        this.assists = assists;
        this.death = death;
        this.creepScore = creepScore;
        this.damage = damage;
        this.wardScore = wardScore;
        this.goldEarn = goldEarn;
        this.damageReceived  = damageReceived;

    }

}
