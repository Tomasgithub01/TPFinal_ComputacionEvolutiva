package Components;

import Model.Path;

import java.util.*;

public class SteadyStateSurvivorSelectionMethod implements SurvivorsSelectionMethod {
    private final int numReemplazo;

    public SteadyStateSurvivorSelectionMethod(int n) {
        this.numReemplazo = n;
    }

    @Override
    public ArrayList<Path> selectSurvivors(ArrayList<Path> generacionVieja, ArrayList<Path> hijos) {

        // Ordenar población vieja por fitness (peor a mejor = menor a mayor fitness)
        ArrayList<Path> poblacionOrdenada = new ArrayList<>(generacionVieja);
        poblacionOrdenada.sort(Comparator.comparingDouble(Path::getFitness));

        // Ordenar hijos por fitness
        ArrayList<Path> hijosOrdenados = new ArrayList<>(hijos);
        hijosOrdenados.sort(Comparator.comparingDouble(Path::getFitness).reversed());

        //Seleccionar los n mejores hijos (sin duplicados para evitar empeorar la diversidad de la población)
        ArrayList<Path> mejoresHijos = chooseBestChildren(hijosOrdenados, numReemplazo, poblacionOrdenada);

        // Crear nueva población eliminando los n peores
        ArrayList<Path> nuevaPoblacion = new ArrayList<>();

        // Mantener los mejores (desde n hasta el final)
        for (int i = numReemplazo; i < poblacionOrdenada.size(); i++) {
            nuevaPoblacion.add(poblacionOrdenada.get(i));
        }

        // Agregar los mejores hijos
        nuevaPoblacion.addAll(mejoresHijos);

        return nuevaPoblacion;
    }

    // Selecciona los n mejores hijos
    private ArrayList<Path> chooseBestChildren(ArrayList<Path> hijosOrdenados, int cantidad, ArrayList<Path> poblacionExistente) {
        ArrayList<Path> seleccionados = new ArrayList<>();
        Set<Path> pathsExistentes = new HashSet<>();

        // Agregar población existente (excluyendo los que se eliminarán)
        for (int i = cantidad; i < poblacionExistente.size(); i++) {
            pathsExistentes.add(poblacionExistente.get(i));
        }

        // Seleccionar hijos únicos
        for (Path hijo : hijosOrdenados) {
            if (seleccionados.size() >= cantidad) {
                break;
            }

            if (!pathsExistentes.contains(hijo)) {
                seleccionados.add(hijo);
                pathsExistentes.add(hijo);
            }
        }

        // Si no hay suficientes hijos únicos, completar con los mejores disponibles
        if (seleccionados.size() < cantidad) {
            for (Path hijo : hijosOrdenados) {
                if (seleccionados.size() >= cantidad) {
                    break;
                }
                if (!seleccionados.contains(hijo)) {
                    seleccionados.add(hijo);
                }
            }
        }

        return seleccionados;
    }


    @Override
    public String getName(){
        return "Steady-State";
    }
}
