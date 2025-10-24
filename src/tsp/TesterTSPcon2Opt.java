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

    // OPERADORES
    // operador para generar cromosomas aleatorios con mejora 2-opt
    OpGeneracion<Integer> opGen = new OpGenRandNoRepCon2Opt<Integer>(
        prob.getAlfabeto(),
        prob.getNumCiudades(),
        new BusquedaLocal2Opt(prob),
        1.0 // probabilidad de aplicar 2-opt (1.0 = siempre)
    );

    // operador de cruce
    OpCruce<Integer> opCruce = new OpCruce1PuntoNoRep<Integer>();

    // operador de mutacion
    OpMutacion<Integer> opMut = new OpMutacionSwap<Integer>();

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
