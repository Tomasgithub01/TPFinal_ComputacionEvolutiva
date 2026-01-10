package Components;

import java.util.Random;
import java.util.ArrayList;
import Model.Path;

public class RouletteWheelMethod implements FatherSelectionMethod{
    private Random random;

    public RouletteWheelMethod() {
        this.random = new Random();
    }

    public Path selectFather(ArrayList<Path> paths) {
        // Calcular la suma total de fitness
        double totalFitness = 0.0;
        for (Path path : paths) {
            totalFitness += path.getFitness();
        }

        // Crear vector de probabilidades acumuladas
        double[] probabilidadesAcumuladas = new double[paths.size()];
        double sumaAcumulada = 0.0;

        for (int i = 0; i < paths.size(); i++) {
            sumaAcumulada += paths.get(i).getFitness() / totalFitness;
            probabilidadesAcumuladas[i] = sumaAcumulada;
        }

        // Generar número aleatorio entre 0 y 1
        double randomValue = random.nextDouble();

        // Buscar el primer individuo cuya probabilidad acumulada sea >= randomValue
        for (int i = 0; i < probabilidadesAcumuladas.length; i++) {
            if (randomValue <= probabilidadesAcumuladas[i]) {
                return paths.get(i);
            }
        }

        //Return solo para que no de problemas, no debería llegar acá, por lo que pongo un warning por las dudas
        System.out.println("Algo falló al seleccionar un camino por el método de la rueda de ruleta");
        return paths.getLast();
    }
}

