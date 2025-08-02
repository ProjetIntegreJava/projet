package modelPackage;

public class Region {
    private final String name;
    public Region(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
