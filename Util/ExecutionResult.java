package Util;

import java.util.ArrayList;
import java.util.List;

//Clase que almacena los resultados de una ejecución que se utilizarán para crear el .json
public class ExecutionResult {
    private final double finalBestFitness;
    private final int bestCost;
    private final long executionTime;
    private final List<Double> fitnessEvolution;
    private final List<Double> diversityEvolution;
    private final ArrayList<Integer> bestSolution;

    public ExecutionResult(double finalBestFitness, int bestCost, long executionTime,
                           List<Double> fitnessEvolution, List<Double> diversityEvolution, ArrayList<Integer> bestSolution) {
        this.finalBestFitness = finalBestFitness;
        this.bestCost = bestCost;
        this.executionTime = executionTime;
        this.fitnessEvolution = fitnessEvolution;
        this.diversityEvolution = diversityEvolution;
        this.bestSolution = bestSolution;
    }

    public double getFinalBestFitness() { return finalBestFitness; }
    public int getBestCost() { return bestCost; }
    public long getExecutionTime() { return executionTime; }
    public List<Double> getFitnessEvolution() { return fitnessEvolution; }
    public List<Double> getDiversityEvolution() { return diversityEvolution; }
    public ArrayList<Integer> getBestSolution() { return bestSolution; }


    // Calcula el promedio de una lista de valores numéricos
    private static double calculateMean(List<Double> valores) {
        if (valores.isEmpty()) {
            return 0.0;
        }

        double suma = 0.0;
        for (double valor : valores) {
            suma += valor;
        }

        return suma / valores.size();
    }

    // Calcula la desviación estándar de una lista de valores numéricos
    private static double calculateStd(List<Double> valores) {
        if (valores.isEmpty()) {
            return 0.0;
        }

        double promedio = calculateMean(valores);
        double sumaDiferenciasCuadradas = 0.0;

        for (double valor : valores) {
            double diferencia = valor - promedio;
            sumaDiferenciasCuadradas += diferencia * diferencia;
        }

        double varianza = sumaDiferenciasCuadradas / valores.size();
        return Math.sqrt(varianza);
    }

    // Extrae los valores de mejor fitness final de cada ejecución
    private static List<Double> extractFitness(List<ExecutionResult> resultados) {
        List<Double> valores = new ArrayList<>();
        for (ExecutionResult resultado : resultados) {
            valores.add(resultado.getFinalBestFitness());
        }
        return valores;
    }

    // Extrae los valores de costo mínimo de cada ejecución
    private static List<Double> extractCosts(List<ExecutionResult> resultados) {
        List<Double> valores = new ArrayList<>();
        for (ExecutionResult resultado : resultados) {
            valores.add((double) resultado.getBestCost());
        }
        return valores;
    }

    // Extrae los tiempos de ejecución de cada ejecución
    private static List<Double> extractTimes(List<ExecutionResult> resultados) {
        List<Double> valores = new ArrayList<>();
        for (ExecutionResult resultado : resultados) {
            valores.add((double) resultado.getExecutionTime());
        }
        return valores;
    }

    // Calcula el promedio del mejor fitness obtenido en todas las ejecuciones
    public static double calculateFitnessMean(List<ExecutionResult> resultados) {
        return calculateMean(extractFitness(resultados));
    }

    // Calcula el promedio del mejor costo obtenido en todas las ejecuciones
    public static double calcularCostsMean(List<ExecutionResult> resultados) {
        return calculateMean(extractCosts(resultados));
    }

    // Calcula el promedio del tiempo de ejecución de todas las ejecuciones
    public static double calculateTimesMean(List<ExecutionResult> resultados) {
        return calculateMean(extractTimes(resultados));
    }

    // Calcula la desviación estándar del mejor fitness obtenido en todas las ejecuciones
    public static double calculateStdFitness(List<ExecutionResult> resultados) {
        return calculateStd(extractFitness(resultados));
    }

    // Calcula la desviación estándar del mejor costo obtenido en todas las ejecuciones
    public static double calculateStdCosts(List<ExecutionResult> resultados) {
        return calculateStd(extractCosts(resultados));
    }

    // Calcula la desviación estándar del tiempo de ejecución de todas las ejecuciones
    public static double calculateStdTimes(List<ExecutionResult> resultados) {
        return calculateStd(extractTimes(resultados));
    }
}
