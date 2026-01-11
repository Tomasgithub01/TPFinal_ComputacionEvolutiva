# Computación Evolutiva - TSP

Desarrollo de un **Algoritmo Evolutivo** para el problema del **Viajante de Comercio Asimétrico (ATSP)**, como trabajo final de la materia Intro. Computación Evolutiva (Ingeniería de Sistemas, FCE, UNCPBA).

## Descripción

El proyecto implementa un algoritmo evolutivo donde cada individuo representa un camino (permutación de ciudades). El fitness está basado en el costo total de recorrer todas las ciudades según una matriz de costos asimétrica. El algoritmo permite personalizar múltiples estrategias evolutivas mediante componentes intercambiables.

## Características Principales

- **Lectura de archivos ATSP**: Soporta el formato estándar TSPLIB
- **Inicialización flexible**: Población aleatoria o mixta con heurística del vecino más cercano
- **Múltiples operadores genéticos**:
  - **Selección de padres**: Torneo y Ruleta
  - **Cruce**: PMX (Partially Mapped Crossover) y cruce basado en arcos
  - **Mutación**: Inversión y desplazamiento (shift)
  - **Selección de supervivientes**: Elitismo y Steady State
- **Parámetros configurables**: Tamaño de población, generaciones, probabilidades de mutación y cruce
- **Estadísticas en tiempo real**: Tracking del mejor, peor y promedio fitness por generación

## Estructura del Proyecto

```
├── Main.java                              # Punto de entrada, configuración y ejecución del algoritmo
├── Model/
│   ├── Population.java                    # Manejo de la población y evolución
│   ├── Path.java                          # Representación de un camino y cálculo de fitness
│   └── Couple.java                        # Par de padres para reproducción
├── Components/
│   ├── FatherSelectionMethod.java         # Interfaz para selección de padres
│   ├── TournamentMethod.java              # Selección por torneo
│   ├── RouletteWheelMethod.java           # Selección por ruleta
│   ├── CrossMethod.java                   # Interfaz para operadores de cruce
│   ├── PMXCrossMethod.java                # Cruce PMX
│   ├── ArchesBasedCrossingMethod.java     # Cruce basado en arcos
│   ├── MutationMethod.java                # Interfaz para operadores de mutación
│   ├── InvertMutationMethod.java          # Mutación por inversión
│   ├── ShiftMutationMethod.java           # Mutación por desplazamiento
│   ├── SurvivorsSelectionMethod.java      # Interfaz para selección de supervivientes
│   ├── ElitismSurvivorMethod.java         # Supervivencia por elitismo
│   └── SteadyStateSurvivorSelectionMethod.java # Supervivencia steady state
└── Util/
    ├── ATSPReader.java                    # Lector de archivos ATSP
    ├── br17.atsp                          # Instancia de prueba (17 ciudades)
    └── p43.atsp                           # Instancia de prueba (43 ciudades)
```

## Uso

### Compilación y Ejecución

```bash
javac Main.java
java Main
```

### Parámetros de Entrada

El programa solicita interactivamente los siguientes parámetros:

1. **Ruta del archivo ATSP**: Archivo con la matriz de costos (formato TSPLIB)
2. **Número de individuos (N)**: Tamaño de la población
3. **Número de generaciones (G)**: Iteraciones del algoritmo
4. **Probabilidad de mutación**: Valor entre 0.0 y 1.0
5. **Probabilidad de cruce**: Valor entre 0.0 y 1.0
6. **Método de inicialización**: 
   - `1` - Población aleatoria
   - `2` - Población mixta (aleatoria + vecino más cercano)
7. **Método de selección de padres**:
   - `1` - Torneo
   - `2` - Ruleta
8. **Método de cruce**:
   - `1` - PMX
   - `2` - Basado en arcos
9. **Método de mutación**:
   - `1` - Inversión
   - `2` - Desplazamiento
10. **Método de selección de supervivientes**:
    - `1` - Elitismo
    - `2` - Steady State

### Ejemplo de Ejecución

```
Por favor, ingrese la ruta a un archivo ATSP (sin comillas): 
Util/br17.atsp
Cargando archivo: Util/br17.atsp
Número de ciudades a visitar: 17
Ingrese los parámetros para el algoritmo genético...
-----------------------------------
Número de individuos (ej. 10): 
50
Número de generaciones (ej. 1000): 
500
Probabilidad de mutación (ej. 0.05): 
0.1
Probabilidad de cruce (ej. 0.6): 
0.7
...
```

## Detalles de Implementación

### Representación

- **Individuo**: Array de enteros representando una permutación de ciudades
- **Fitness**: Inverso del costo total del camino (maximización)

### Algoritmo Evolutivo

1. **Inicialización**: Genera población inicial (aleatoria o con heurística)
2. **Evaluación**: Calcula fitness de cada individuo
3. **Selección**: Elige padres según estrategia configurada
4. **Reproducción**: Aplica cruce y mutación
5. **Reemplazo**: Selecciona supervivientes para la siguiente generación
6. **Repetir** pasos 2-5 por G generaciones

### Operadores Genéticos

- **PMX (Partially Mapped Crossover)**: Preserva posiciones y orden parcial
- **Cruce basado en arcos**: Hereda arcos (conexiones) de los padres
- **Mutación por inversión**: Invierte un segmento del camino
- **Mutación por desplazamiento**: Desplaza un segmento a otra posición

## Archivos de Prueba

El proyecto incluye dos instancias de prueba en formato ATSP:

- `br17.atsp`: 17 ciudades
- `p43.atsp`: 43 ciudades

## Requisitos

- Java 8 o superior
- No requiere librerías externas

## Autor

Trabajo final - Intro. Computación Evolutiva  
Ingeniería de Sistemas, FCE, UNCPBA