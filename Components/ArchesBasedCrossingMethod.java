package Components;

import Model.Couple;
import Model.Path;

import java.util.*;

public class ArchesBasedCrossingMethod implements CrossMethod {
    private final Random random;

    public ArchesBasedCrossingMethod(Random random) {
        this.random = random;
    }

    @Override
    //Cruce basado en arcos para una pareja. Debido que el algoritmo está pensado para generar un solo hijo,
    // se aplica dos veces intercambiando el rol de los padres
    public ArrayList<Path> crossCouple(Couple couple) {
        Path padre1 = couple.getFather();
        Path padre2 = couple.getMother();

        ArrayList<Integer> genesPadre = new ArrayList<>(padre1.getCities());
        ArrayList<Integer> genesMadre = new ArrayList<>(padre2.getCities());

        // Hijo 1: Padres en orden normal
        Map<Integer, Set<Integer>> tablaArcos1 = buildArchesTable(genesPadre, genesMadre);
        ArrayList<Integer> hijo1 = generateChild(tablaArcos1, genesPadre.size());

        // Hijo 2: Padres en orden invertido
        Map<Integer, Set<Integer>> tablaArcos2 = buildArchesTable(genesMadre, genesPadre);
        ArrayList<Integer> hijo2 = generateChild(tablaArcos2, genesPadre.size());

        Path pathHijo1 = new Path(hijo1, padre1.getCosts());
        Path pathHijo2 = new Path(hijo2, padre1.getCosts());

        ArrayList<Path> hijos = new ArrayList<>();
        hijos.add(pathHijo1);
        hijos.add(pathHijo2);
        return hijos;
    }


    // Construye la tabla de arcos (listas de adyacencia) a partir de ambos padres. Un elemento repetido se representa con '+'.
    private Map<Integer, Set<Integer>> buildArchesTable(ArrayList<Integer> genesPadre, ArrayList<Integer> genesMadre) {
        Map<Integer, Set<Integer>> tabla = new HashMap<>();
        int n = genesPadre.size();

        // Inicializar tabla vacía
        for (Integer ciudad : genesPadre) {
            tabla.put(ciudad, new HashSet<>());
        }

        // Agregar arcos de genesPadre (considerando camino cíclico)
        for (int i = 0; i < n; i++) {
            int actual = genesPadre.get(i);
            int siguiente = genesPadre.get((i + 1) % n);
            int anterior = genesPadre.get((i - 1 + n) % n);

            tabla.get(actual).add(siguiente);
            tabla.get(actual).add(anterior);
        }

        // Agregar arcos de genesMadre (considerando camino cíclico)
        for (int i = 0; i < n; i++) {
            int actual = genesMadre.get(i);
            int siguiente = genesMadre.get((i + 1) % n);
            int anterior = genesMadre.get((i - 1 + n) % n);

            tabla.get(actual).add(siguiente);
            tabla.get(actual).add(anterior);
        }

        return tabla;
    }

    // Genera un hijo con la tabla de arcos generada previamente
    private ArrayList<Integer> generateChild(Map<Integer, Set<Integer>> tablaArcos, int tamanio) {
        ArrayList<Integer> hijo = new ArrayList<>();
        Set<Integer> visitados = new HashSet<>();

        // Elegir un elemento al azar como inicio
        List<Integer> ciudades = new ArrayList<>(tablaArcos.keySet());
        Integer elementoActual = ciudades.get(random.nextInt(ciudades.size()));

        hijo.add(elementoActual);
        visitados.add(elementoActual);

        // Remover todas las referencias al elemento elegido
        removeReferences(tablaArcos, elementoActual);

        // Continuar construyendo el hijo
        while (hijo.size() < tamanio) {
            // Examinar lista de arcos del elemento actual
            Set<Integer> arcosDisponibles = tablaArcos.get(elementoActual);

            Integer siguienteElemento;

            if (arcosDisponibles != null && !arcosDisponibles.isEmpty()) {
                // Buscar arco común (+) con menor lista de arcos
                siguienteElemento = chooseNextElement(arcosDisponibles, tablaArcos, visitados);
            } else {
                // Lista vacía - elegir elemento al azar no visitado
                siguienteElemento = chooseRandomElement(ciudades, visitados);
            }

            // Agregar siguiente elemento al hijo
            hijo.add(siguienteElemento);
            visitados.add(siguienteElemento);

            // Remover todas las referencias al nuevo elemento
            removeReferences(tablaArcos, siguienteElemento);

            elementoActual = siguienteElemento;
        }

        return hijo;
    }

    // Elige el siguiente elemento según la estrategia del algoritmo: 1) Arco común (+) a un cierto elemento 2) Si no, elemento con lista de arcos más corta
    private Integer chooseNextElement(Set<Integer> arcosDisponibles, Map<Integer, Set<Integer>> tablaArcos, Set<Integer> visitados) {
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

    // Elige elemento al azar que no se haya visitado todavía
    private Integer chooseRandomElement(List<Integer> ciudades, Set<Integer> visitados) {
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

    //Remueve todas las referencias a un elemento en la tabla de arcos.
    private void removeReferences(Map<Integer, Set<Integer>> tablaArcos, Integer elemento) {
        for (Set<Integer> arcos : tablaArcos.values()) {
            arcos.remove(elemento);
        }
    }

    @Override
    public String getName(){
        return "Cruce basado en arcos";
    }
}
