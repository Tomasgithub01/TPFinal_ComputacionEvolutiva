package Components;

import Model.Path;
import Model.Couple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PMXCrossMethod implements CrossMethod {
    private final Random random;

    public PMXCrossMethod(Random random) {
        this.random = random;
    }

    @Override
    public ArrayList<Path> crossCouple(Couple couple) {
        Path padre = couple.getFather();
        Path madre = couple.getMother();

        ArrayList<Integer> P1 = new ArrayList<>(padre.getCities());
        ArrayList<Integer> P2 = new ArrayList<>(madre.getCities());

        int tamanio = P1.size();

        if (tamanio != P2.size()) {
            throw new IllegalArgumentException("Los padres deben tener la misma longitud");
        }

        // Elegir dos puntos de cruce al azar
        int corte1 = random.nextInt(tamanio);
        int corte2 = random.nextInt(tamanio);

        if (corte1 > corte2) {
            int temp = corte1;
            corte1 = corte2;
            corte2 = temp;
        }

        // Crear hijo1 a partir de P1 y P2
        ArrayList<Integer> hijo1 = bornChild(P1, P2, corte1, corte2);

        // Crear hijo2 invirtiendo roles (P2 como base, P1 como donante)
        ArrayList<Integer> hijo2 = bornChild(P2, P1, corte1, corte2);

        // Retornar hijos como objetos Path
        Path pathHijo1 = new Path(hijo1, padre.getCosts());
        Path pathHijo2 = new Path(hijo2, padre.getCosts());

        ArrayList<Path> hijos = new ArrayList<>();
        hijos.add(pathHijo1);
        hijos.add(pathHijo2);
        return hijos;
    }

    // Crea un hijo, con un padre como base y otro que cede algunos de sus genes
    private ArrayList<Integer> bornChild(ArrayList<Integer> base, ArrayList<Integer> donante, int corte1, int corte2) {
        int tamanio = base.size();
        Integer[] hijo = new Integer[tamanio];
        Arrays.fill(hijo, -1); // -1 indica posición vacía

        // Copiar segmento entre puntos de cruce del padre base
        for (int i = corte1; i <= corte2; i++) {
            hijo[i] = base.get(i);
        }

        // Aplicar mapeo PMX
        for (int i = corte1; i <= corte2; i++) {
            Integer elementoDonante = donante.get(i);

            // Revisar si el elemento del donante ya está en el hijo
            if (contiene(hijo, elementoDonante)) {
                continue; // Ya está incluido, pasar al siguiente
            }

            // Seguir cadena de mapeos para encontrar posición correcta
            int posicion = i;
            while (posicion >= corte1 && posicion <= corte2) {
                // Elemento que ocupa esta posición en el hijo
                Integer elementoEnHijo = hijo[posicion];

                // Buscar dónde está ese elemento en el padre donante
                posicion = donante.indexOf(elementoEnHijo);
            }

            // Ubicar elemento en la posición encontrada fuera del segmento
            hijo[posicion] = elementoDonante;
        }

        // Llenar posiciones restantes con elementos del donante en orden
        int indiceDonante = 0;
        for (int i = 0; i < tamanio; i++) {
            if (hijo[i] == -1) {
                // Buscar siguiente elemento del donante que no esté en el hijo
                while (contiene(hijo, donante.get(indiceDonante))) {
                    indiceDonante++;
                }
                hijo[i] = donante.get(indiceDonante);
                indiceDonante++;
            }
        }

        return new ArrayList<>(Arrays.asList(hijo));
    }

    //Determina si un arreglo tiene o no un cierto valor
    private boolean contiene(Integer[] arr, Integer valor) {
        for (Integer v : arr) {
            if (v != null && v.equals(valor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName(){
        return "PMX";
    }
}
