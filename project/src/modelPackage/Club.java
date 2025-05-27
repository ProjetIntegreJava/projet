package modelPackage;

public class Club {
    private String name;
    private String CEO;
    private String nationality;
    public Club(String name, String CEO , String nationality) {
        this.name = name;
        this.CEO = CEO;
        this.nationality = nationality;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCEO() {
        return CEO;
    }
    public void setCEO(String CEO) {
        this.CEO = CEO;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
