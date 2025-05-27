package modelPackage;

public class Competition {
    private String name;
    private int year;
    private Region region;
    private CompetitionLevel level;
    public Competition(String name, int year, Region region, CompetitionLevel level){
        this.name = name;
        this.year = year;
        this.region = region;
        this.level = level;
    }
    public Competition(String name, int year) {
        this(name, year, new Region("unknown"), new CompetitionLevel("unknown"));
    }
    public String getName() {
        return name;
    }
    public int getYear() {
        return year;
    }
    public Region getRegion() {
        return region;
    }
    public CompetitionLevel getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setRegion(Region region) {
        this.region = region;
    }
    public void setLevel(CompetitionLevel level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return this.name + "-" + this.year;
    }
}
