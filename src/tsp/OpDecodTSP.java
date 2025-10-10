package tsp;

import ga.Individuo;
import ga.OpDecodificacion;
import ga.Solucion;

/**
 * Clase OpDecodTSP
 * Clase que decodifica un cromosoma y obtiene un fenotipo (un tour o solucion al problema)
 * @author Ines
 * @version 2018.11.*
 */
public class OpDecodTSP implements OpDecodificacion<Integer>{
	// ATRIBUTO
	private static ProblemaTSP p; // los datos: ciudades, distancias...

	/**
	 * Constructor
	 * @param p el problema
	 */
	public OpDecodTSP(ProblemaTSP p) {
		OpDecodTSP.p = p;
	}
	
	
	/** metodo para decodificar un cromosoma a una solucion del TSP
	 * @param ind el individuo (cromosoma o genotipo): permutacion de (1,...,N) donde N es el numero de ciudades
	 * @return la solucion que representa (fenotipo): el tour que pasa por todas las ciudades una unica vez
	 */
	public Solucion apply(Individuo<Integer> cromosoma) {
		// TODO Auto-generated method stub
		return decodificar( cromosoma );
	}
	
	
	/**
	 * 
	 */
	private SolucionTSP decodificar( Individuo<Integer> ind) {
		// inicia solucion con solo el origen
		SolucionTSP sol = new SolucionTSP(p);
		// aniade las ciudades en el orden marcado por el individuo
		for( Integer i: ind.getRepresentation() ) {
			sol.aniade( p.getCiudad(i) ); // aniade ciudad i-esima
		}
		// "cierra" la ruta de la solucion volviendo al origen
		sol.aniade(p.getOrigen());
		return sol;
	}



}
