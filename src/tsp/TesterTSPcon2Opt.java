package tsp;

import ga.*;

/**
 * Clase TesterTSPcon2Opt
 * Clase para probar el GA aplicado al TSP con mejora local 2-opt (Lamarckismo)
 * 
 * @author Ian Saucedo
 * @version 2025.10
 */
public class TesterTSPcon2Opt {

  public static void main(String[] args) {
    ProblemaTSP prob = new ProblemaTSP("gr17.tsp.txt");

    // Instancia compartida de busqueda local 2-opt
    BusquedaLocal2Opt busqueda2Opt = new BusquedaLocal2Opt(prob);

    // ESTRATEGIA: NO aplicar 2-opt durante el GA, solo al final al mejor individuo
    // Esto permite que el GA explore libremente sin convergencia prematura

    // OPERADORES SIN 2-OPT (dejar que el GA explore primero)
    OpGeneracion<Integer> opGen = new OpGenRandNoRep<>(
        prob.getAlfabeto(),
        prob.getNumCiudades());

    OpCruce<Integer> opCruce = new OpCruce1PuntoNoRep<>();
    OpMutacion<Integer> opMut = new OpMutacionSwap<>();

    // operador de seleccion
    OpSeleccion<Integer> opSel = new OpSelRandom<Integer>();

    // operador de decodificacion
    OpDecodificacion<Integer> opDecod = new OpDecodTSP(prob);

    // operador de reemplazo
    OpReemplazo<Integer> opReemp = new OpReempGenElitista<Integer>();

    // PARAMETROS
    int maxIter = 1000; // criterio de parada
    double pc = 0.9; // prob cruce (ALTA para explorar)
    double pm = 0.1; // prob mutacion (BAJA para no destruir buenas soluciones)
    int tamPob = 10000; // tamanio poblacion

    System.out.println("Ejecutando GA con busqueda local 2-opt...");
    System.out.println("Parametros: maxIter=" + maxIter + ", pc=" + pc + ", pm=" + pm + ", tamPob=" + tamPob);

    // ALGORITMO GENETICO
    AlgoritmoGenetico<Integer> ga = new AlgoritmoGenetico<Integer>(
        prob.getNumCiudades(),
        prob.getAlfabeto(),
        pc,
        pm,
        tamPob);

    Individuo<Integer> cromoSol = ga.lanzaGA(opGen, maxIter, opCruce, opMut, opSel, opDecod, opReemp);

    System.out.println("\n=== SOLUCION ENCONTRADA POR EL GA ===");
    Solucion solucionGA = opDecod.apply(cromoSol);
    double costeGA = ((SolucionTSP) solucionGA).getCoste();
    System.out.println(solucionGA);
    System.out.println("Coste GA: " + costeGA);

    // AHORA aplicar 2-opt para mejorar localmente la mejor solucion del GA
    System.out.println("\n=== APLICANDO MEJORA LOCAL 2-OPT ===");
    Individuo<Integer> cromoMejorado = busqueda2Opt.mejorar(cromoSol);
    Solucion solucionMejorada = opDecod.apply(cromoMejorado);
    double coste2Opt = ((SolucionTSP) solucionMejorada).getCoste();

    System.out.println("\n=== RESUMEN ===");
    System.out.println("Coste GA:          " + costeGA);
    System.out.println("Coste GA + 2-opt:  " + coste2Opt);
    System.out.println("Mejora con 2-opt:  " + (costeGA - coste2Opt));

    System.out.println("\nSolucion final:");
    System.out.println(solucionMejorada);
  }
}
