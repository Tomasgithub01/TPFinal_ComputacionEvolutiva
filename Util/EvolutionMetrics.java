package Util;

import Model.Population;
import Model.Path;
import java.util.ArrayList;
import java.util.List;

public class EvolutionMetrics {
    private List<Double> bestFitnessList = new ArrayList<>();
    private List<Double> diversityList = new ArrayList<>();
    private List<Integer> generationList = new ArrayList<>();
    private long tiempoEjecucion;

    //Registra el mejor fitness y la diversidad de cada generación
    public void recordGeneration(int generation, Population population) {
        generationList.add(generation);

        double best = population.getBestPath().getFitness();
        double diversity = calculateDiversity(population);

        bestFitnessList.add(best);
        diversityList.add(diversity);
    }

    public void startTracking(){
        tiempoEjecucion = System.currentTimeMillis();
    }

    public void stopTracking(long fin){
        tiempoEjecucion = fin - tiempoEjecucion;
    }


    //Calcula la diversidad de una generación basándose en la distancia promedio entre los caminos de la población
    private double calculateDiversity(Population population) {
        List<Path> paths = population.getPaths();
        int n = paths.size();
        double sumDistances = 0.0;
        int comparisons = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sumDistances += calculatePathDistance(paths.get(i), paths.get(j));
                comparisons++;
            }
        }

        return comparisons > 0 ? sumDistances / comparisons : 0.0;
    }

    // Calcula la diversidad promedio de todas las generaciones
    public double getAverageDiversity() {
        if (diversityList.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (double diversity : diversityList) {
            sum += diversity;
        }
        return sum / diversityList.size();
    }

    // Calcula la distancia entre dos caminos
    private double calculatePathDistance(Path p1, Path p2) {
        int differences = 0;
        List<Integer> path1 = p1.getCities();
        List<Integer> path2 = p2.getCities();

        for (int i = 0; i < path1.size(); i++) {
            //Si difieren en una ciudad, se suma 1 a la diferencia
            if (!path1.get(i).equals(path2.get(i))) {
                differences++;
            }
        }
        return (double) differences / path1.size();
    }

    // Obtiene el mejor fitness de la generación final
    public double getFinalBestFitness() {
        if (bestFitnessList.isEmpty()){
            return 0.0;
        }
        else {
            return bestFitnessList.getLast();
        }
    }


    public List<Double> getBestFitnessList() { return new ArrayList<Double>(bestFitnessList); }
    public List<Double> getDiversityList() { return new ArrayList<Double>(diversityList); }
    public List<Integer> getGenerationList() { return new ArrayList<Integer>(generationList); }
    public long getExecutionTime(){return tiempoEjecucion;}

    // Método para cargar datos de fitness desde una lista externa
    public void loadFitnessData(List<Double> fitnessData) {
        bestFitnessList.clear();
        bestFitnessList.addAll(fitnessData);
        generationList.clear();
        for (int i = 0; i < fitnessData.size(); i++) {
            generationList.add(i);
        }
    }

    // Método para cargar datos de diversidad desde una lista externa
    public void loadDiversityData(List<Double> diversityData) {
        diversityList.clear();
        diversityList.addAll(diversityData);
    }
}
