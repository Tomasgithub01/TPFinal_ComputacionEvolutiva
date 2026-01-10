package Model;

public class Couple{
    private final Path dad;
    private final Path mom;

    public Couple(Path padre1, Path padre2) {
        this.dad = padre1;
        this.mom = padre2;
    }

    public Path getPadre1() { return dad; }
    public Path getPadre2() { return mom; }
    public void mostrarPareja() {
        System.out.println("  ");
        System.out.println("========================================");
        System.out.println("Pareja:");
        System.out.println("  Papá:");
        System.out.println("    Ruta   : " + dad.getCities());
        System.out.println("    Coste  : " + dad.getPathCost());
        System.out.println("    Fitness: " + String.format("%.8f", dad.getFitness()));
        System.out.println("  Mamá:");
        System.out.println("    Ruta   : " + mom.getCities());
        System.out.println("    Coste  : " + mom.getPathCost());
        System.out.println("    Fitness: " + String.format("%.8f", mom.getFitness()));
    }

}
