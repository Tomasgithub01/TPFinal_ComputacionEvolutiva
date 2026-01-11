package Components;

import Model.Path;

import java.util.*;

public class ElitismSurvivorMethod implements SurvivorsSelectionMethod {
    private final int numeroElite;

    public ElitismSurvivorMethod(int numeroElite) {
        this.numeroElite = numeroElite;
    }

    @Override
    public ArrayList<Path> selectSurvivors(ArrayList<Path> generacionVieja, ArrayList<Path> hijos) {
        int tamanoPoblacion = generacionVieja.size();

        // PASO 1: Seleccionar los mejores de la generación vieja (ÉLITE)
        ArrayList<Path> poblacionOrdenada = new ArrayList<>(generacionVieja);
        poblacionOrdenada.sort(Comparator.comparingDouble(Path::getFitness).reversed());

        ArrayList<Path> nuevaPoblacion = new ArrayList<>();
        for (int i = 0; i < Math.min(numeroElite, poblacionOrdenada.size()); i++) {
            nuevaPoblacion.add(poblacionOrdenada.get(i));
        }

        // PASO 2: Completar con los mejores hijos
        ArrayList<Path> hijosOrdenados = new ArrayList<>(hijos);
        hijosOrdenados.sort(Comparator.comparingDouble(Path::getFitness).reversed());

        for (Path hijo : hijosOrdenados) {
            if (nuevaPoblacion.size() >= tamanoPoblacion) {
                break;
            }
            nuevaPoblacion.add(hijo);
        }

        return nuevaPoblacion;
    }

    public int getNumeroElite() {
        return numeroElite;
    }
}
