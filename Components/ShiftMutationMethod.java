package Components;

import Model.Path;

import java.util.ArrayList;
import java.util.Random;

public class ShiftMutationMethod implements MutationMethod {
    private Random random;

    public ShiftMutationMethod() {
        this.random = new Random();
    }

    @Override
    public Path mutate(Path sujeto) {
        ArrayList<Integer> ciudades = sujeto.getCities();
        int tamanio = ciudades.size();

        if (tamanio < 3) {
            return sujeto; // No se puede hacer desplazamiento con menos de 3 elementos
        }

        // Paso 1: Elegir punto de inicio y fin del segmento a mover
        int inicio = random.nextInt(tamanio);
        int fin = random.nextInt(tamanio);

        // Asegurar que inicio <= fin y que haya al menos un elemento
        if (inicio > fin) {
            int temp = inicio;
            inicio = fin;
            fin = temp;
        }

        // Evitar que el segmento sea toda la lista
        if (inicio == 0 && fin == tamanio - 1) {
            fin = tamanio - 2;
        }

        // Paso 2: Elegir posición destino (fuera del segmento)
        int destino;
        do {
            destino = random.nextInt(tamanio);
        } while (destino >= inicio && destino <= fin);

        // Paso 3: Extraer el segmento a mover
        ArrayList<Integer> segmento = new ArrayList<>();
        for (int i = inicio; i <= fin; i++) {
            segmento.add(ciudades.get(i));
        }

        // Paso 4: Remover el segmento de su posición original
        for (int i = fin; i >= inicio; i--) {
            ciudades.remove(i);
        }

        // Paso 5: Insertar el segmento en la nueva posición
        // Ajustar la posición destino si está después del segmento removido
        if (destino > inicio) {
            destino -= segmento.size();
        }

        ciudades.addAll(destino, segmento);

        return sujeto;
    }
}
