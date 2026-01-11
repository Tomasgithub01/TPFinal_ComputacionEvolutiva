package Components;

import Model.Couple;
import Model.Path;

import java.util.*;

public class ArchesBasedCrossingMethod implements CrossMethod {
    private Random random;

    public ArchesBasedCrossingMethod() {
        this.random = new Random();
    }

    @Override
    //Cruce basado en arcos para una pareja. Debido que el algoritmo está pensado para generar un solo hijo, se aplica dos veces intercambiando el rol de los padres
    public ArrayList<Path> crossCouple(Couple couple) {
        Path padre1 = couple.getPadre1();
        Path padre2 = couple.getPadre2();

        ArrayList<Integer> genesPadre = new ArrayList<>(padre1.getCities());
        ArrayList<Integer> genesMadre = new ArrayList<>(padre2.getCities());

        // **Hijo 1: Padres en orden normal**
        Map<Integer, Set<Integer>> tablaArcos1 = construirTablaArcos(genesPadre, genesMadre);
        ArrayList<Integer> hijo1 = generarHijoConArcos(tablaArcos1, genesPadre.size());

        // **Hijo 2: Padres en orden invertido**
        Map<Integer, Set<Integer>> tablaArcos2 = construirTablaArcos(genesMadre, genesPadre);
        ArrayList<Integer> hijo2 = generarHijoConArcos(tablaArcos2, genesPadre.size());

        Path pathHijo1 = new Path(hijo1, padre1.getCosts());
        Path pathHijo2 = new Path(hijo2, padre1.getCosts());

        ArrayList<Path> hijos = new ArrayList<>();
        hijos.add(pathHijo1);
        hijos.add(pathHijo2);
        return hijos;
    }


    /**
     * Construye la tabla de arcos (listas de adyacencia) a partir de ambos padres.
     * El símbolo '+' se representa con múltiples apariciones del mismo elemento.
     */
    private Map<Integer, Set<Integer>> construirTablaArcos(ArrayList<Integer> genesPadre, ArrayList<Integer> genesMadre) {
        Map<Integer, Set<Integer>> tabla = new HashMap<>();
        int n = genesPadre.size();

        // Inicializar tabla vacía
        for (Integer ciudad : genesPadre) {
            tabla.put(ciudad, new HashSet<>());
        }

        // Agregar arcos de genesPadre (considerando tour cíclico)
        for (int i = 0; i < n; i++) {
            int actual = genesPadre.get(i);
            int siguiente = genesPadre.get((i + 1) % n);
            int anterior = genesPadre.get((i - 1 + n) % n);

            tabla.get(actual).add(siguiente);
            tabla.get(actual).add(anterior);
        }

        // Agregar arcos de genesMadre (considerando tour cíclico)
        for (int i = 0; i < n; i++) {
            int actual = genesMadre.get(i);
            int siguiente = genesMadre.get((i + 1) % n);
            int anterior = genesMadre.get((i - 1 + n) % n);

            tabla.get(actual).add(siguiente);
            tabla.get(actual).add(anterior);
        }

        return tabla;
    }

    /**
     * Genera un hijo usando el algoritmo de cruce basado en arcos.
     */
    private ArrayList<Integer> generarHijoConArcos(Map<Integer, Set<Integer>> tablaArcos, int tamanio) {
        ArrayList<Integer> hijo = new ArrayList<>();
        Set<Integer> visitados = new HashSet<>();

        // Paso 1: Elegir un elemento al azar como inicio
        List<Integer> ciudades = new ArrayList<>(tablaArcos.keySet());
        Integer elementoActual = ciudades.get(random.nextInt(ciudades.size()));

        hijo.add(elementoActual);
        visitados.add(elementoActual);

        // Paso 2: Remover todas las referencias al elemento elegido
        removerReferencias(tablaArcos, elementoActual);

        // Continuar construyendo el hijo
        while (hijo.size() < tamanio) {
            // Paso 3: Examinar lista de arcos del elemento actual
            Set<Integer> arcosDisponibles = tablaArcos.get(elementoActual);

            Integer siguienteElemento;

            if (arcosDisponibles != null && !arcosDisponibles.isEmpty()) {
                // Buscar arco común (+) con menor lista de arcos
                siguienteElemento = elegirSiguienteElemento(arcosDisponibles, tablaArcos, visitados);
            } else {
                // Paso 4: Lista vacía - elegir elemento al azar no visitado
                siguienteElemento = elegirElementoAleatorioNoVisitado(ciudades, visitados);
            }

            // Agregar siguiente elemento al hijo
            hijo.add(siguienteElemento);
            visitados.add(siguienteElemento);

            // Paso 2: Remover todas las referencias al nuevo elemento
            removerReferencias(tablaArcos, siguienteElemento);

            elementoActual = siguienteElemento;
        }

        return hijo;
    }

    /**
     * Elige el siguiente elemento según la estrategia del algoritmo:
     * - Arco común (+) a un cierto elemento
     * - Si no, elemento con lista de arcos más corta
     */
    private Integer elegirSiguienteElemento(Set<Integer> arcosDisponibles,
                                            Map<Integer, Set<Integer>> tablaArcos,
                                            Set<Integer> visitados) {
        // Filtrar elementos ya visitados
        Set<Integer> candidatos = new HashSet<>(arcosDisponibles);
        candidatos.removeAll(visitados);

        if (candidatos.isEmpty()) {
            return null;
        }

        // Buscar candidato con lista de arcos más corta
        Integer mejorCandidato = null;
        int menorTamanio = Integer.MAX_VALUE;

        for (Integer candidato : candidatos) {
            Set<Integer> arcosCandidato = tablaArcos.get(candidato);
            int tamanio = (arcosCandidato != null) ? arcosCandidato.size() : 0;

            if (tamanio < menorTamanio) {
                menorTamanio = tamanio;
                mejorCandidato = candidato;
            }
        }

        return mejorCandidato;
    }

    /**
     * Elige un elemento al azar no visitado.
     */
    private Integer elegirElementoAleatorioNoVisitado(List<Integer> ciudades, Set<Integer> visitados) {
        List<Integer> noVisitados = new ArrayList<>();
        for (Integer ciudad : ciudades) {
            if (!visitados.contains(ciudad)) {
                noVisitados.add(ciudad);
            }
        }

        if (noVisitados.isEmpty()) {
            return null;
        }

        return noVisitados.get(random.nextInt(noVisitados.size()));
    }

    /**
     * Remueve todas las referencias a un elemento en la tabla de arcos.
     */
    private void removerReferencias(Map<Integer, Set<Integer>> tablaArcos, Integer elemento) {
        // Remover elemento de todas las listas de adyacencia
        for (Set<Integer> arcos : tablaArcos.values()) {
            arcos.remove(elemento);
        }
    }
}
