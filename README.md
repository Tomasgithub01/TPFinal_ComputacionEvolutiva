# Computacion Evolutiva \- TSP

Desarrollo de un Algoritmo Evolutivo para el problema del ***Viajante de Comercio (TSP)***, como trabajo final de la materia Intro. Computación Evolutiva (Ingeniería de Sistemas, FCE, UNCPBA).

## Descripcion

El proyecto implementa un algoritmo evolutivo donde cada individuo es un camino (permutación de ciudades), con fitness basado en el costo de trasladarse de una ciudad a la otra, usando operadores de seleccion, cruce y mutacion para la generación de nuevas poblaciones a lo largo del tiempo, simulando la ***Teoría de la Evolución*** planteada por ***Charles Darwin***.

## Estructura basica

 `Main.java`: configura y ejecuta el algoritmo.  
 `Population.java`: maneja la poblacion y las estadisticas de fitness.  
 `Path.java`: representa un camino y calcula costo/fitness.  
 `InstancesTSP`: lector ATSP y archivos de instancias.
