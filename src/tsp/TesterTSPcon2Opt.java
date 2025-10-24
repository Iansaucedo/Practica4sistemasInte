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

    // ESTRATEGIA: Aplicar 2-opt solo a ALGUNOS individuos para mantener diversidad
    // - Generación inicial: 10% de probabilidad (mantener diversidad)
    // - Después de cruce: NO aplicar (dejar que el GA explore)
    // - Después de mutación: NO aplicar (dejar que el GA explore)
    double prob2OptGen = 0.1; // 10% en generación inicial
    double prob2OptCruce = 0.0; // 0% después de cruce
    double prob2OptMut = 0.0; // 0% después de mutación

    // OPERADORES CON 2-OPT
    OpGeneracion<Integer> opGen = new OpGenRandNoRepCon2Opt<>(
        prob.getAlfabeto(),
        prob.getNumCiudades(),
        busqueda2Opt,
        prob2OptGen);

    // Usar operadores normales para cruce y mutación
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
    double pc = 0.9; // prob cruce
    double pm = 0.4; // prob mutacion
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
    Solucion solucion = opDecod.apply(cromoSol);

    System.out.println("Solucion final: \n" + solucion);
    System.out.println("Coste: " + ((SolucionTSP) solucion).getCoste());
  }
}
