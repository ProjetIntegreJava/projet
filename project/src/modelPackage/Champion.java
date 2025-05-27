package modelPackage;

public class Champion {
    private final String name, race;
    public Champion(String name, String race){
        this.name = name;
        this.race = race;
    }
    public String getName() {
        return name;
    }
    public String getRace() {
        return this.race;
    }

}
