# Implementación de Búsqueda Local 2-opt para TSP

## Resumen

Se ha implementado la búsqueda local 2-opt como mejora opcional al algoritmo genético para resolver el Problema del Viajante (TSP). La implementación sigue el enfoque Lamarckiano, donde los individuos mejorados sustituyen a los originales en la población.

## Verificación de Conformidad con el Algoritmo 2-opt

La implementación cumple completamente con el algoritmo 2-opt descrito en la literatura científica (Rego & Glover, 2017):

### ✅ **1. Reemplazar dos aristas no adyacentes**

El algoritmo debe reemplazar dos aristas no adyacentes `(vi, vi+1)` y `(vj, vj+1)` por `(vi, vj)` y `(vi+1, vj+1)`.

**Implementación:**

```java
for (int i = 0; i < mejorRuta.size() - 1; i++) {
    for (int j = i + 2; j < mejorRuta.size(); j++) {  // j = i + 2 asegura no adyacencia
```

✅ Correcto: `j = i + 2` garantiza que hay al menos una ciudad entre i y j.

### ✅ **2. Invertir el subpath**

El algoritmo debe invertir el segmento `(vi+1,...,vj)` para mantener la estructura del tour.

**Implementación:**

```java
invertirSegmento(nuevaRuta, i + 1, j);  // Invierte (vi+1...vj)
```

✅ Correcto: Invierte exactamente el segmento apropiado.

### ✅ **3. Aceptar solo mejoras (Δij < 0)**

El algoritmo debe calcular el cambio de costo `Δij = c(vi, vj) + c(vi+1, vj+1) − c(vi, vi+1) − c(vj, vj+1)` y aceptar solo si Δij < 0.

**Implementación:**

```java
if (nuevaDistancia < mejorDistancia) {  // Equivalente a Δij < 0
    mejorRuta = nuevaRuta;
    mejora = true;
}
```

✅ Correcto: La comparación es matemáticamente equivalente.

### ✅ **4. Iterar hasta 2-optimalidad**

El algoritmo debe continuar hasta que ningún movimiento 2-opt mejore la solución.

**Implementación:**

```java
do {
    mejora = false;
    // ... prueba todos los movimientos ...
} while (mejora);  // Continúa hasta que no haya mejoras
```

✅ Correcto: Alcanza un óptimo local (solución 2-opt).

### ✅ **5. Complejidad O(n²)**

Para k=2, la complejidad debe ser O(n²) por iteración.

**Implementación:**

```java
for (int i ...) {          // O(n)
    for (int j ...) {      // O(n)
```

✅ Correcto: Dos bucles anidados = O(n²) por iteración.

## Archivos Creados

### 1. `BusquedaLocal2Opt.java` (paquete `tsp`)

Esta clase implementa el algoritmo de búsqueda local 2-opt tal como se describe en la literatura.

**Características principales:**

- **Método `mejorar(Individuo<Integer> individuo)`**: Aplica iterativamente el algoritmo 2-opt hasta que no se encuentren más mejoras.

- **Algoritmo 2-opt**:

  - Selecciona dos aristas no adyacentes `(vi, vi+1)` y `(vj, vj+1)`
  - Las reemplaza por `(vi, vj)` y `(vi+1, vj+1)`
  - Invierte el segmento de ruta entre `i+1` y `j` para mantener la orientación del tour
  - Calcula el cambio en el costo: Δij = c(vi, vj) + c(vi+1, vj+1) - c(vi, vi+1) - c(vj, vj+1)
  - Si Δij < 0, acepta el movimiento (mejora)
  - Continúa hasta alcanzar un óptimo local (solución 2-opt)

- **Método `invertirSegmento()`**: Invierte un segmento de la ruta para mantener la estructura del tour.

- **Método `calcularDistanciaTotal()`**: Calcula el costo total de una ruta, incluyendo las distancias desde/hacia el origen.

**Complejidad temporal**: O(n²) por iteración, donde n es el número de ciudades.

### 2. `OpGenRandNoRepCon2Opt.java` (paquete `ga`)

Operador de generación que combina la generación aleatoria con la mejora 2-opt.

**Características:**

- Extiende `OpGeneracion<A>` para integrarse con el framework del GA
- Genera individuos aleatorios sin repetición (permutaciones)
- Aplica 2-opt con probabilidad configurable (enfoque Lamarckiano)
- Permite controlar la intensidad de la búsqueda local mediante el parámetro `probabilidad2Opt`

### 3. `TesterTSPcon2Opt.java` (paquete `tsp`)

Clase de prueba que demuestra cómo integrar 2-opt con el algoritmo genético.

**Parámetros configurables:**

- `probabilidad2Opt`: 1.0 (aplica 2-opt a todos los individuos generados)
- Se puede ajustar para aplicar 2-opt solo a una fracción de los individuos

## Integración con el Algoritmo Genético

La búsqueda local 2-opt se puede integrar en diferentes momentos:

1. **En la generación inicial** (implementado): Mejora los individuos al crearlos
2. **Después del cruce**: Mejora los hijos resultantes
3. **Después de la mutación**: Mejora los individuos mutados
4. **Al final de cada generación**: Mejora toda la población o una parte

## Ventajas del Enfoque Híbrido (GA + 2-opt)

1. **Intensificación**: El GA explora globalmente el espacio de búsqueda, mientras que 2-opt intensifica localmente
2. **Balance exploración-explotación**: Combina la diversidad del GA con la convergencia rápida de 2-opt
3. **Lamarckismo**: Los individuos mejorados transmiten sus características a la siguiente generación
4. **Mejor calidad de soluciones**: Típicamente produce tours de menor costo que GA puro

## Desventajas

1. **Costo computacional**: 2-opt añade O(n²) operaciones por individuo mejorado
2. **Convergencia prematura**: Si se aplica con probabilidad muy alta, puede reducir la diversidad

## Uso

```java
// Crear instancia del problema
ProblemaTSP prob = new ProblemaTSP("gr17.tsp.txt");

// Crear operador de generación con 2-opt
BusquedaLocal2Opt bl2opt = new BusquedaLocal2Opt(prob);
OpGeneracion<Integer> opGen = new OpGenRandNoRepCon2Opt<>(
    prob.getAlfabeto(),
    prob.getNumCiudades(),
    bl2opt,
    1.0  // probabilidad de aplicar 2-opt
);

// Usar en el GA como cualquier otro operador de generación
AlgoritmoGenetico<Integer> ga = new AlgoritmoGenetico<>(...);
Individuo<Integer> solucion = ga.lanzaGA(opGen, ...);
```

## Experimentos Sugeridos

1. **Variar probabilidad2Opt**: 0.0, 0.25, 0.5, 0.75, 1.0
2. **Comparar con GA puro**: Medir calidad de soluciones y tiempo de ejecución
3. **Diferentes instancias**: Probar en gr17.tsp.txt y brazil58.tsp.txt
4. **Aplicar 2-opt en diferentes etapas**: Generación, después de cruce, después de mutación

## Referencias

- Lin, S. (1965). Computer solutions of the traveling salesman problem. Bell System Technical Journal.
- Rego, C., Glover, F. (2017). Local Search and Metaheuristics. In: The Traveling Salesman Problem and Its Variations.
