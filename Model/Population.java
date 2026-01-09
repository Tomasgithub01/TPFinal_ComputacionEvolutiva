package Model;

import java.util.ArrayList;
import java.util.Random;

public class Population {
    private final ArrayList<Path> possiblePaths;
    private final double mutProb;
    private final double crossProb;
    private final int generations;

    public enum FitnessRegime {
        MUY_SIMILARES,
        VARIACION_MEDIA_ALTA
    }

    public Population(ArrayList<Path> possiblePaths, double mutProb, double crossProb, int generations) {
        this.possiblePaths = possiblePaths;
        this.mutProb = mutProb;
        this.crossProb = crossProb;
        this.generations = generations;
    }

    public static Population createMixedPopulation(int N, int C, int[][] costs, double mutProb, double crossProb, int generations, double porcentajeNN) {
        int nNN = (int) Math.round(N * porcentajeNN);
        if (nNN > N) nNN = N;
        int nRandom = N - nNN;

        ArrayList<Path> paths = new ArrayList<>(N);
        Random rnd = new Random();

        // 1) individuos generados por vecino más cercano
        for (int i = 0; i < nNN; i++) {
            int startCity = rnd.nextInt(C);
            Path p = Path.createNearestNeighborPath(startCity, costs);
            paths.add(p);
        }

        // 2) individuos generados de forma aleatoria
        for (int i = 0; i < nRandom; i++) {
            Path p = new Path(C, costs);
            paths.add(p);
        }

        return new Population(paths, mutProb, crossProb, generations);
    }

    public void printPaths() {
        int i = 1;
        for (Path path : possiblePaths) {
            String name = "Model.Path " + i + ": ";
            System.out.println(name + path.toString());
            i++;
        }
    }

    public FitnessRegime determinarRegimenFitnessPorCosto() {
        if (possiblePaths == null || possiblePaths.isEmpty()) {
            return FitnessRegime.MUY_SIMILARES;
        }

        int n = possiblePaths.size();
        double sum = 0.0;
        double sumSquares = 0.0;

        for (Path p : possiblePaths) {
            double c = p.getPathCost();
            sum += c;
            sumSquares += c * c;
        }

        double mean = sum / n;
        double variance = (sumSquares / n) - (mean * mean);
        if (variance < 0) variance = 0;
        double stdDev = Math.sqrt(variance);

        double cv = (mean != 0.0) ? (stdDev / mean) * 100.0 : 0.0;
        System.out.printf("Los fitness difieren aproximadamente un %.2f%%%n", cv);
        if (cv < 10.0) {
            return FitnessRegime.MUY_SIMILARES;
        } else {
            return FitnessRegime.VARIACION_MEDIA_ALTA;
        }
    }


    public void aplicarFitnessSegunRegimen() {
        FitnessRegime regime = determinarRegimenFitnessPorCosto();


        if (regime == FitnessRegime.MUY_SIMILARES) {
            System.out.println("Los costos de los caminos son muy similares. Aplicando transformación lineal al fitness.");
            // 1) calcular fitness base y hallar el peor (mínimo)
            double peorFitness = Double.MAX_VALUE;
            for (Path p : possiblePaths) {
                double fitness = 1.0 / p.getPathCost();
                p.setFitness(fitness);
                if (fitness < peorFitness) {
                    peorFitness = fitness;
                }
            }

            // 2) aplicar transformación lineal f'(i) = f(i) - beta
            for (Path p : possiblePaths) {
                double f = p.getFitness();
                double fTransformado = f - peorFitness;
                p.setFitness(fTransformado);
            }
        } else if (regime == FitnessRegime.VARIACION_MEDIA_ALTA) {
            System.out.println("Los costos de los caminos tienen variación media/alta. Aplicando fitness inverso al costo.");
            for (Path p : possiblePaths) {
                double fitness = 1.0 / p.getPathCost();
                p.setFitness(fitness);
            }
        }
    }

    public ArrayList<Path> getPaths(){
        return new ArrayList<>(possiblePaths);
    }



}
