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
        int tamanoPoblacion = generacionVieja.size();

        // Paso 1: Ordenar población vieja por fitness (peor a mejor = menor a mayor fitness)
        ArrayList<Path> poblacionOrdenada = new ArrayList<>(generacionVieja);
        poblacionOrdenada.sort(Comparator.comparingDouble(Path::getFitness));

        // Paso 2: Ordenar hijos por fitness (mejor a peor = mayor a menor fitness)
        ArrayList<Path> hijosOrdenados = new ArrayList<>(hijos);
        hijosOrdenados.sort(Comparator.comparingDouble(Path::getFitness).reversed());

        // Paso 3: Seleccionar los n mejores hijos (sin duplicados)
        ArrayList<Path> mejoresHijos = seleccionarMejoresHijosSinDuplicados(
                hijosOrdenados,
                numReemplazo,
                poblacionOrdenada
        );

        // Paso 4: Crear nueva población eliminando los n peores
        ArrayList<Path> nuevaPoblacion = new ArrayList<>();

        // Mantener los mejores (desde posición numReemplazo hasta el final)
        for (int i = numReemplazo; i < poblacionOrdenada.size(); i++) {
            nuevaPoblacion.add(poblacionOrdenada.get(i));
        }

        // Paso 5: Agregar los mejores hijos
        nuevaPoblacion.addAll(mejoresHijos);

        return nuevaPoblacion;
    }

    /**
     * Selecciona los n mejores hijos evitando duplicados con la población existente.
     */
    private ArrayList<Path> seleccionarMejoresHijosSinDuplicados(
            ArrayList<Path> hijosOrdenados,
            int cantidad,
            ArrayList<Path> poblacionExistente
    ) {
        ArrayList<Path> seleccionados = new ArrayList<>();
        Set<String> representacionesExistentes = new HashSet<>();

        // Agregar representaciones de la población existente (excluyendo los que se eliminarán)
        for (int i = cantidad; i < poblacionExistente.size(); i++) {
            representacionesExistentes.add(getRepresentacion(poblacionExistente.get(i)));
        }

        // Seleccionar hijos únicos
        for (Path hijo : hijosOrdenados) {
            if (seleccionados.size() >= cantidad) {
                break;
            }

            String representacion = getRepresentacion(hijo);
            if (!representacionesExistentes.contains(representacion)) {
                seleccionados.add(hijo);
                representacionesExistentes.add(representacion);
            }
        }

        // Si no hay suficientes hijos únicos, completar con los mejores disponibles
        if (seleccionados.size() < cantidad) {
            for (Path hijo : hijosOrdenados) {
                if (seleccionados.size() >= cantidad) {
                    break;
                }
                if (!contienePathExacto(seleccionados, hijo)) {
                    seleccionados.add(hijo);
                }
            }
        }

        return seleccionados;
    }

    /**
     * Obtiene una representación string única del path para comparaciones.
     */
    private String getRepresentacion(Path path) {
        return path.getCities().toString();
    }

    /**
     * Verifica si un path exacto ya existe en la lista.
     */
    private boolean contienePathExacto(ArrayList<Path> lista, Path path) {
        String representacion = getRepresentacion(path);
        for (Path p : lista) {
            if (getRepresentacion(p).equals(representacion)) {
                return true;
            }
        }
        return false;
    }

    public int getNumReemplazo() {
        return numReemplazo;
    }
}
