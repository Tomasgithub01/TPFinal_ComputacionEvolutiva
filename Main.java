import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import Util.ATSPReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // PREGUNTAR RUTA DEL ARCHIVO AL USUARIO
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor, ingrese la ruta a un archivo ATSP (sin comillas): ");
        String filePath = scanner.nextLine();

         if (!filePath.endsWith(".atsp") || !new File(filePath).isFile()) {
             System.err.println("Archivo inválido o inexistente. Saliendo.");
             scanner.close();
             return;
         }
        System.out.println("Cargando archivo: " + filePath);
        File atspFile = new File(filePath);
        int[][] costs = ATSPReader.read(atspFile);
        int C = costs.length; //Number of cities to visit
        System.out.println("Número de ciudades a visitar: " + C);


        //--------------------------------------------------------------------------------------------------------------------


        //PREGUNTAR PARÁMETROS AL USUARIO
        System.out.println("Ingrese los parámetros para el algoritmo genético...");
        System.out.println("-----------------------------------");
        System.out.println("Número de individuos (ej. 10): ");
        int N = Integer.parseInt(scanner.nextLine());
        if (N <= 1) {
            System.err.println("El número de individuos debe ser mayor que 1. Saliendo.");
            scanner.close();
            return;
        }

        System.out.println("Número de generaciones (ej. 1000): ");
        int G = Integer.parseInt(scanner.nextLine());
        if (G <= 0) {
            System.err.println("El número de generaciones debe ser mayor que 0. Saliendo.");
            scanner.close();
            return;
        }

        System.out.println("Probabilidad de mutación (ej. 0.05): ");
        double mutProb = Double.parseDouble(scanner.nextLine());
        if (mutProb < 0.0 || mutProb > 1.0) {
            System.err.println("La probabilidad de mutación debe estar entre 0 y 1. Saliendo.");
            scanner.close();
            return;
        }

        System.out.println("Probabilidad de cruce (ej. 0.6): ");
        double crossProb = Double.parseDouble(scanner.nextLine());
        if (crossProb < 0.0 || crossProb > 1.0) {
            System.err.println("La probabilidad de cruce debe estar entre 0 y 1. Saliendo.");
            scanner.close();
            return;
        }


        // ----------------------------------------------------------------------------------------------------

        // INICIALIZAR PRIMERA POBLACIÓN Y MOSTRAR RESULTADOS
        System.out.println("Ingrese el método de inicialización de la población: 1) Aleatorio 2) Mixta con vecino más cercano (ingrese 1 o 2): ");
        String opcion = scanner.nextLine();

        Population p;

        switch (opcion) {
            case "1":
                System.out.println("Inicializando población de forma aleatoria...");
                // porcentajeNN = 0 -> todos aleatorios
                p = Population.createMixedPopulation(N, C, costs, mutProb, crossProb, G, 0.0);
                break;

            case "2":
                System.out.println("Inicializando población usando mezcla con vecino más cercano...");
                System.out.println("Ingrese el porcentaje de individuos a inicializar con vecino más cercano (ej. 0.1): ");
                double porcentaje = Double.parseDouble(scanner.nextLine());
                if (porcentaje < 0.0 || porcentaje > 1.0) {
                    System.err.println("El porcentaje debe estar entre 0 y 1. Saliendo.");
                    scanner.close();
                    return;
                }
                p = Population.createMixedPopulation(N, C, costs, mutProb, crossProb, G, porcentaje);
                break;

            default:
                System.err.println("Opción inválida. Saliendo.");
                scanner.close();
                return;
        }

        p.aplicarFitnessSegunRegimen();
        System.out.println("-----------------------------------");
        p.printPaths();


    }
}
