package Model;

public class Couple{
    private final Path padre1;
    private final Path padre2;

    public Couple(Path padre1, Path padre2) {
        this.padre1 = padre1;
        this.padre2 = padre2;
    }

    public Path getPadre1() { return padre1; }
    public Path getPadre2() { return padre2; }
}
