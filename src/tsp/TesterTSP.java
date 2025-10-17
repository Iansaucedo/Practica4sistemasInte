
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
		// OPERADORES
		// operador para generar cromosomas aleatorios
		OpGeneracion<Integer> opGen = new OpGenRandNoRep<Integer>(prob.getAlfabeto(), prob.getNumCiudades());
		// operador de cruce: (des)comentar segun el que se quiera usar
		OpCruce<Integer> opCruce = new OpCruce1PuntoNoRep<Integer>();
		// OpCruce<Integer> opCruce = new OpCruce2PuntosNoRep<Integer>();
		// operador de mutacion
		OpMutacion<Integer> opMut = new OpMutacionSwap<Integer>();
		// operador de seleccion: (des)comentar segun el que se quiera usar
		// OpSeleccion<Integer> opSel = new OpSelRuleta<Integer>();
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

		// ALGORITMO GENETICO
		AlgoritmoGenetico<Integer> ga = new AlgoritmoGenetico<Integer>(prob.getNumCiudades(), prob.getAlfabeto(), pc, pm,
				tamPob);
		Individuo<Integer> cromoSol = ga.lanzaGA(opGen, maxIter, opCruce, opMut, opSel, opDecod, opReemp);
		Solucion solucion = opDecod.apply(cromoSol);
		System.out.println("Solucion final: \n" + solucion);
		System.out.println("Coste: " + ((SolucionTSP) solucion).getCoste());

	}

}
