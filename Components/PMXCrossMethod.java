package Components;

import Model.Path;
import Model.Couple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PMXCrossMethod implements CrossMethod {
    private Random random;

    public PMXCrossMethod() {
        this.random = new Random();
    }

    @Override
    public ArrayList<Path> crossCouple(Couple couple) {
        Path padre = couple.getPadre1();
        Path madre = couple.getPadre2();

        ArrayList<Integer> P1 = new ArrayList<>(padre.getCities());
        ArrayList<Integer> P2 = new ArrayList<>(madre.getCities());

        int tamanio = P1.size();

        if (tamanio != P2.size()) {
            throw new IllegalArgumentException("Los padres deben tener la misma longitud");
        }

        // Paso 1: Elegir dos puntos de cruce al azar
        int corte1 = random.nextInt(tamanio);
        int corte2 = random.nextInt(tamanio);

        if (corte1 > corte2) {
            int temp = corte1;
            corte1 = corte2;
            corte2 = temp;
        }

        // Crear hijo1 a partir de P1 y P2
        ArrayList<Integer> hijo1 = crearHijo(P1, P2, corte1, corte2);

        // Crear hijo2 invirtiendo roles (P2 como base, P1 como donante)
        ArrayList<Integer> hijo2 = crearHijo(P2, P1, corte1, corte2);

        // Retornar hijos como objetos Path
        Path pathHijo1 = new Path(hijo1, padre.getCosts());
        Path pathHijo2 = new Path(hijo2, padre.getCosts());

        ArrayList<Path> hijos = new ArrayList<>();
        hijos.add(pathHijo1);
        hijos.add(pathHijo2);
        return hijos;
    }

    /**
     * Crea un hijo usando PMX según el procedimiento especificado.
     * @param base Padre del cual se copia el segmento entre cortes (P1 para hijo1)
     * @param donante Padre del cual se completan los huecos (P2 para hijo1)
     */
    private ArrayList<Integer> crearHijo(ArrayList<Integer> base, ArrayList<Integer> donante, int corte1, int corte2) {
        int tamanio = base.size();
        Integer[] hijo = new Integer[tamanio];
        Arrays.fill(hijo, -1); // -1 indica posición vacía

        // Paso 1: Copiar segmento entre puntos de cruce del padre base
        for (int i = corte1; i <= corte2; i++) {
            hijo[i] = base.get(i);
        }

        // Pasos 2-5: Aplicar mapeo PMX
        for (int i = corte1; i <= corte2; i++) {
            Integer elementoDonante = donante.get(i);

            // Paso 2: Revisar si el elemento del donante ya está en el hijo
            if (contiene(hijo, elementoDonante)) {
                continue; // Ya está incluido, pasar al siguiente
            }

            // Pasos 3-5: Seguir cadena de mapeos para encontrar posición correcta
            int posicion = i;
            while (posicion >= corte1 && posicion <= corte2) {
                // Paso 3: Elemento que ocupa esta posición en el hijo
                Integer elementoEnHijo = hijo[posicion];

                // Paso 4-5: Buscar dónde está ese elemento en el donante
                posicion = donante.indexOf(elementoEnHijo);
            }

            // Ubicar elemento en la posición encontrada fuera del segmento
            hijo[posicion] = elementoDonante;
        }

        // Paso 6: Llenar posiciones restantes con elementos del donante en orden
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

    private boolean contiene(Integer[] arr, Integer valor) {
        for (Integer v : arr) {
            if (v != null && v.equals(valor)) {
                return true;
            }
        }
        return false;
    }
}
