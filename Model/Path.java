package Model;// java
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Path {
    private final ArrayList<Integer> cities;
    private int pathCost;
    private double fitness;
    private final Random random = new Random();
    private final int[][] costs;

    public Path(int numberOfCities, int[][] costs) {
        if (numberOfCities <= 0) {
            throw new IllegalArgumentException("La cantidad de ciudades debe ser mayor a 0");
        }
        if (costs == null || costs.length != numberOfCities) {
            throw new IllegalArgumentException("La matriz de costos debe ser cuadrada");
        }
        for (int i = 0; i < numberOfCities; i++) {
            if (costs[i] == null || costs[i].length != numberOfCities) {
                throw new IllegalArgumentException("La matriz de costos debe ser cuadrada");
            }
        }

        this.costs = costs;

        cities = new ArrayList<>(numberOfCities);
        for (int i = 1; i <= numberOfCities; i++) {
            cities.add(i);
        }
        shuffleCities();
        calculateCost();
    }

    private void shuffleCities() {
        for (int i = cities.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = cities.get(i);
            cities.set(i, cities.get(j));
            cities.set(j, tmp);
        }
    }

    private void calculateCost() {
        int total = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            int from = cities.get(i) - 1;
            int to = cities.get(i + 1) - 1;
            total += costs[from][to];
        }
        if (!cities.isEmpty()) {
            int last = cities.get(cities.size() - 1) - 1;
            int first = cities.get(0) - 1;
            total += costs[last][first];
        }
        pathCost = total;
    }

    public static Path createNearestNeighborPath(int startCityIndex0, int[][] costs) {
        int n = costs.length;
        boolean[] visited = new boolean[n];
        List<Integer> tour = new ArrayList<>(n);

        int current = startCityIndex0;
        visited[current] = true;
        tour.add(current + 1);

        for (int k = 1; k < n; k++) {
            int next = -1;
            int bestCost = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && costs[current][j] < bestCost) {
                    bestCost = costs[current][j];
                    next = j;
                }
            }
            current = next;
            visited[current] = true;
            tour.add(current + 1);
        }

        Path p = new Path(n, costs);
        p.cities.clear();
        p.cities.addAll(tour);
        p.calculateCost();
        return p;
    }

    public int getPathCost() {
        return pathCost;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        String fitnessStr = String.format("%.8f", fitness);
        return cities.toString() + " | Cost: " + pathCost + "  | Fitness: " + fitnessStr;
    }

    public boolean equals(Path p2){
        for (int i = 0; i < cities.size(); i++){
            if(!Objects.equals(cities.get(i), p2.cities.get(i))){
                return false;
            }
        }
        return true;
    }
}
