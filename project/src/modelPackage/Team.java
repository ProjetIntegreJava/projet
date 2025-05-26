package modelPackage;

import java.time.LocalDate;

public class Team {
    private String name;
    private Club club;
    private Region region;
    private LocalDate creationDate;
    private LocalDate foundingDate;
    private boolean hasBeenWorldChampion;
    private String description;
    private int nbFollower;

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
    public boolean isHasBeenWorldChampion() {
        return hasBeenWorldChampion;
    }
    public String getDescription() {
        return description;
    }
    public int getNbFollowers() {
        return nbFollower;
    }
}
