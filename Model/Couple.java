package Model;

public class Couple{
    private final Path dad;
    private final Path mom;

    public Couple(Path padre1, Path padre2) {
        this.dad = padre1;
        this.mom = padre2;
    }

    public Path getFather() { return dad; }
    public Path getMother() { return mom; }
}
