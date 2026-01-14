# Computación Evolutiva - TSP (Problema del Viajante de Comercio)

Implementación de un **Algoritmo Evolutivo** para resolver el **Problema del Viajante de Comercio Asimétrico (ATSP)**, desarrollado como trabajo final de la materia Introducción a la Computación Evolutiva (Ingeniería de Sistemas, FCE, UNCPBA).

## 📋 Descripción

Este proyecto implementa un algoritmo genético completo con visualización web interactiva donde:
- **Individuos**: Cada individuo representa un camino (permutación de ciudades)
- **Fitness**: Basado en el costo total de recorrer las ciudades en el orden especificado
- **Operadores Genéticos**: Múltiples métodos de selección, cruce, mutación y reemplazo configurables
- **Entrada**: Archivos en formato ATSP (Asymmetric Traveling Salesman Problem)
- **Visualización**: Gráficos interactivos de evolución y métricas en HTML con Chart.js
- **Logging**: Sistema de registro de ejecuciones y métricas evolutivas

## ✨ Características Principales

### 🧬 Algoritmo Genético
- **Lectura de archivos ATSP**: Soporta el formato estándar TSPLIB
- **Inicialización flexible**: Población aleatoria o mixta con heurística del vecino más cercano
- **Múltiples operadores genéticos configurables**:
  - **Selección de padres**: Torneo y Ruleta
  - **Cruce**: PMX (Partially Mapped Crossover) y cruce basado en arcos
  - **Mutación**: Inversión y desplazamiento (shift)
  - **Selección de supervivientes**: Elitismo y Steady State
- **Parámetros configurables**: Tamaño de población, generaciones, probabilidades de mutación y cruce

### 📊 Visualización y Análisis
- **Gráficos interactivos**: Generación automática de visualizaciones HTML con Chart.js
- **Métricas de evolución**: Tracking de mejor, promedio y peor fitness por generación
- **Análisis de diversidad**: Cálculo y visualización de diversidad poblacional
- **Dashboard de estadísticas**: Tarjetas con métricas clave (mejora total, fitness final, etc.)
- **Exportación de datos**: Logs en CSV y JSON para análisis posterior

### 🔧 Utilidades
- **ExecutionLogger**: Sistema de logging configurable para registrar experimentos
- **EvolutionMetrics**: Seguimiento detallado de métricas evolutivas
- **EvolutionChart**: Generación automática de gráficos HTML interactivos
- **ATSPReader**: Lector robusto de archivos ATSP

## 🏗️ Estructura del Proyecto

```
├── Main.java                              # Punto de entrada, configuración y ejecución del algoritmo
├── evolucion.html                         # Archivo HTML de visualización generado
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
├── Util/
│   ├── ATSPReader.java                    # Lector de archivos ATSP
│   ├── EvolutionMetrics.java              # Seguimiento de métricas evolutivas
│   ├── EvolutionChart.java                # Generador de gráficos HTML
│   ├── ExecutionLogger.java               # Sistema de logging de experimentos
│   ├── br17.atsp                          # Instancia de prueba (17 ciudades)
│   ├── p43.atsp                           # Instancia de prueba (43 ciudades)
│   └── test4.atsp                         # Instancia de prueba (4 ciudades)
└── Tests/                                 # Directorio de pruebas y experimentos
```

## 🚀 Uso

### Compilación y Ejecución

```bash
javac Main.java
java Main
```

### Parámetros de Entrada

El programa solicita interactivamente los siguientes parámetros:

1. **Ruta del archivo ATSP**: Archivo con la matriz de costos (formato TSPLIB)
2. **Número de individuos (N)**: Tamaño de la población (ej: 10, 50, 100)
3. **Número de generaciones (G)**: Iteraciones del algoritmo (ej: 500, 1000)
4. **Probabilidad de mutación**: Valor entre 0.0 y 1.0 (ej: 0.05)
5. **Probabilidad de cruce**: Valor entre 0.0 y 1.0 (ej: 0.6)
6. **Método de inicialización**: 
   - `1` - Población completamente aleatoria
   - `2` - Población mixta (aleatoria + heurística vecino más cercano)
7. **Método de selección de padres**:
   - `1` - Torneo (con tamaño configurable)
   - `2` - Ruleta
8. **Método de cruce**:
   - `1` - PMX (Partially Mapped Crossover)
   - `2` - Basado en arcos
9. **Método de mutación**:
   - `1` - Inversión de subsecuencia
   - `2` - Desplazamiento (shift)
10. **Método de selección de supervivientes**:
    - `1` - Elitismo (con elite configurable)
    - `2` - Steady State (con reemplazo configurable)

### Ejemplo de Ejecución

```
=== CONFIGURACIÓN DEL ALGORITMO GENÉTICO ===

Por favor, ingrese la ruta a un archivo ATSP (sin comillas): 
Util/br17.atsp
Cargando archivo: Util/br17.atsp
Número de ciudades a visitar: 17

--- PARÁMETROS DEL ALGORITMO ---
Número de individuos (ej. 10): 50
Número de generaciones (ej. 1000): 1000
Probabilidad de mutación (ej. 0.05): 0.1
Probabilidad de cruce (ej. 0.6): 0.7

--- MÉTODO DE INICIALIZACIÓN ---
Elija el método: 1) Aleatorio  2) Mixta con vecino más cercano (ingrese 1 o 2): 2
Ingrese el porcentaje de individuos con vecino más cercano (ej. 0.1): 0.2
...
```

### Visualización de Resultados

Al finalizar la ejecución, el programa genera automáticamente:

1. **`evolucion.html`**: Archivo HTML con gráficos interactivos que incluye:
   - 📈 Gráfico de evolución del fitness (mejor, promedio, peor)
   - 🔄 Gráfico de diversidad poblacional
   - 📊 Dashboard con estadísticas clave:
     - Fitness inicial vs. final
     - Mejora porcentual total
     - Número de generaciones ejecutadas
     - Diversidad final de la población

2. **Logs en consola**: Métricas detalladas de cada generación durante la ejecución

Para visualizar los gráficos, simplemente abre el archivo `evolucion.html` en tu navegador web.

## 📊 Métricas y Análisis

### Métricas Registradas

- **Best Fitness**: Mejor fitness en cada generación
- **Average Fitness**: Fitness promedio de la población
- **Worst Fitness**: Peor fitness en cada generación
- **Diversity**: Medida de diversidad poblacional (distancia promedio entre individuos)
- **Execution Time**: Tiempo transcurrido en milisegundos
- **Improvement**: Porcentaje de mejora desde el inicio

### Visualizaciones Disponibles

Los gráficos HTML generados incluyen:
- **Chart.js interactivo**: Zoom, hover tooltips, leyendas dinámicas
- **Diseño responsivo**: Se adapta a diferentes tamaños de pantalla
- **Animaciones suaves**: Transiciones y efectos visuales
- **Tarjetas de estadísticas**: Métricas clave destacadas con gradientes

## 🧪 Archivos de Prueba

El proyecto incluye tres instancias de prueba en formato ATSP:

- **`test4.atsp`**: 4 ciudades (ideal para debugging)
- **`br17.atsp`**: 17 ciudades (problema clásico de Repetto)
- **`p43.atsp`**: 43 ciudades (problema más complejo de Repetto & Pekny)

## 🔬 Detalles de Implementación

### Representación

- **Individuo**: Array de enteros representando una permutación de ciudades
- **Fitness**: Inverso del costo total del camino (maximización)
- **Formato**: Archivos ATSP en formato TSPLIB estándar

### Algoritmo Evolutivo

1. **Inicialización**: Genera población inicial (aleatoria o con heurística)
2. **Evaluación**: Calcula fitness de cada individuo
3. **Registro de métricas**: Almacena estadísticas de la generación
4. **Selección**: Elige padres según estrategia configurada
5. **Reproducción**: Aplica operadores de cruce y mutación
6. **Reemplazo**: Selecciona supervivientes para la siguiente generación
7. **Repetir** pasos 2-6 por G generaciones
8. **Visualización**: Genera gráficos HTML con la evolución

### Operadores Genéticos

- **PMX (Partially Mapped Crossover)**: Preserva posiciones absolutas y orden relativo
- **Cruce basado en arcos**: Hereda conexiones (arcos) de ambos padres
- **Mutación por inversión**: Invierte un segmento aleatorio del camino
- **Mutación por desplazamiento**: Mueve un segmento a otra posición
- **Elitismo**: Preserva los mejores individuos entre generaciones
- **Steady State**: Reemplaza solo un subconjunto de la población

## 🎨 Tecnologías Utilizadas

- **Java 8+**: Lenguaje de programación principal
- **Chart.js**: Biblioteca JavaScript para gráficos interactivos
- **HTML5/CSS3**: Visualización web moderna
- **TSPLIB**: Formato estándar para problemas TSP

## 📈 Composición del Repositorio

- **90.5% HTML**: Archivos de visualización generados
- **9.5% Java**: Código fuente del algoritmo evolutivo

## 📝 Requisitos

- **Java 8 o superior**
- **Navegador web moderno** (para visualización HTML)
- **No requiere librerías externas Java** (solo Chart.js vía CDN para gráficos)

## 👨‍💻 Autor

**Antúnez Monges, Tomás**  
Trabajo final - Introducción a la Computación Evolutiva  
Ingeniería de Sistemas, Facultad de Ciencias Exactas, UNCPBA

## 📄 Licencia

Proyecto académico - UNCPBA

---

## 🚀 Mejoras Futuras

- [ ] Exportación de resultados en formatos adicionales (CSV, JSON)
- [ ] Comparación visual entre múltiples ejecuciones
- [ ] Más operadores genéticos (OX, CX, etc.)
- [ ] Paralelización del algoritmo
- [ ] Interfaz gráfica completa (GUI)
- [ ] Soporte para TSP simétrico (STSP)