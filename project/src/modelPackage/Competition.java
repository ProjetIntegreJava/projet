package modelPackage;

public class Competition {
    private String name;
    private int year;
    private Region region;
    private CompetitionLevel level;
    public Competition(String name, int year, Region region, CompetitionLevel level){
        this.name = name;
        this.region = region;
        this.level = level;
        this.year = year;

    }
}
