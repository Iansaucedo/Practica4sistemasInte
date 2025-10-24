
package tsp;

import ga.*;

/**
 * Clase TesterTSP
 * Clase para probar el GA aplicado al TSP
 * 
 * @author Ines
 * @version 2021.10.*
 */
public class TesterTSP {

	// update

	public static void main(String[] args) {
		ProblemaTSP prob = new ProblemaTSP("gr17.tsp.txt");

		System.out.println("=== EJECUTANDO GA SIN 2-OPT ===");

		// OPERADORES
		// operador para generar cromosomas aleatorios
		OpGeneracion<Integer> opGen = new OpGenRandNoRep<>(prob.getAlfabeto(), prob.getNumCiudades());
		// operador de cruce: (des)comentar segun el que se quiera usar
		OpCruce<Integer> opCruce = new OpCruce1PuntoNoRep<>();
		// OpCruce<Integer> opCruce = new OpCruce2PuntosNoRep<>();
		// operador de mutacion
		OpMutacion<Integer> opMut = new OpMutacionSwap<>();
		// operador de seleccion: (des)comentar segun el que se quiera usar
		// OpSeleccion<Integer> opSel = new OpSelRuleta<>();
		OpSeleccion<Integer> opSel = new OpSelRandom<>();
		// operador de decodificacion
		OpDecodificacion<Integer> opDecod = new OpDecodTSP(prob);
		// operador de reemplazo
		OpReemplazo<Integer> opReemp = new OpReempGenElitista<>();

		// PARAMETROS
		int maxIter = 1000; // criterio de parada
		double pc = 0.9; // prob cruce
		double pm = 0.4; // prob mutacion
		int tamPob = 10000; // tamanio poblacion

		System.out.println("Parametros: maxIter=" + maxIter + ", pc=" + pc + ", pm=" + pm + ", tamPob=" + tamPob);
		System.out.println();

		// ALGORITMO GENETICO
		AlgoritmoGenetico<Integer> ga = new AlgoritmoGenetico<>(prob.getNumCiudades(), prob.getAlfabeto(), pc, pm, tamPob);
		Individuo<Integer> cromoSol = ga.lanzaGA(opGen, maxIter, opCruce, opMut, opSel, opDecod, opReemp);
		Solucion solucion = opDecod.apply(cromoSol);
		double costeGA = ((SolucionTSP) solucion).getCoste();

		System.out.println("\n=== SOLUCION ENCONTRADA POR EL GA ===");
		System.out.println(solucion);
		System.out.println("Coste GA: " + costeGA);

		// Aplicar 2-opt para comparar
		System.out.println("\n=== APLICANDO MEJORA LOCAL 2-OPT ===");
		BusquedaLocal2Opt busqueda2Opt = new BusquedaLocal2Opt(prob);
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
