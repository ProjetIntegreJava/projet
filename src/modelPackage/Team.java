package modelPackage;

import java.time.LocalDate;
import java.util.Objects;

public class Team {
    private String name;
    private Club club;
    private Region region;
    private LocalDate creationDate;
    private LocalDate foundingDate;
    private Boolean hasBeenWorldChampion;
    private String description;
    private Integer nbFollower;

    public Team(String name, Club club, Region region, LocalDate creationDate, LocalDate foundingDate, boolean hasBeenWorldChampion, String description, Integer nbFollower){
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
    }

    public Team() {

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
    public void setName(String name) {
        this.name = name;
    }
    public void setClub(Club club) {
        this.club = club;
    }
    public void setRegion(Region region) {
        this.region = region;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }
    public void setHasBeenWorldChampion(Boolean hasBeenWorldChampion) {
        this.hasBeenWorldChampion = hasBeenWorldChampion;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setNbFollower(Integer nbFollower) {
        if (nbFollower == null) {
            this.nbFollower = null; // champ facultatif = NULL
        } else if (nbFollower < 0) {
            throw new IllegalArgumentException("Le nombre de followers ne peut pas être négatif.");
        } else {
            this.nbFollower = nbFollower;
        }
    }
    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Object getNbFollower() {
        return null;
    }
}
