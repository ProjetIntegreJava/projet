package modelPackage;

import java.time.LocalDate;

public class Team {
    private String name;
    private Club club;
    private Region region;
    private LocalDate creationDate;
    private LocalDate foundingDate;
    private Boolean hasBeenWorldChampion;
    private String description;
    private Integer nbFollower;

    public Team(String name, Club club, Region region, LocalDate creationDate, LocalDate foundingDate, boolean hasBeenWorldChampion, String description, int nbFollower){
        this.name = name;
        this.club = club;
        this.region = region;
        this.creationDate = creationDate;
        this.foundingDate = foundingDate;
        this.hasBeenWorldChampion = hasBeenWorldChampion;
        this.description = description;
        this.nbFollower = nbFollower;

    }
    public Team(String name, Club club, Region region, LocalDate creationDate, LocalDate foundingDate, boolean hasBeenWorldChampion){
        this.name = name;
        this.club = club;
        this.region = region;
        this.creationDate = creationDate;
        this.foundingDate = foundingDate;
        this.hasBeenWorldChampion = hasBeenWorldChampion;
    }
    public Team(String name) {
        this.name = name;
        this.club = null;
        this.region = null;
        this.creationDate = null;
        this.foundingDate = null;
        this.hasBeenWorldChampion = null;
        this.description = null;
        this.nbFollower = null;
    }

    public String getName() {
        return name;
    }
    public Club getClub() {
        return club;
    }
    public Region getRegion() {
        return region;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public LocalDate getFoundingDate() {
        return foundingDate;
    }
    public Boolean hasBeenWorldChampion() {
        return hasBeenWorldChampion;
    }
    public String getDescription() {
        return description;
    }
    public Integer getNbFollowers() {
        return nbFollower;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
