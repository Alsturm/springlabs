package lab.model;


public class Squishee {
    private String name;

    @SuppressWarnings("WeakerAccess")
    public Squishee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}