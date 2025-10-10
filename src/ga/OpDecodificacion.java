package ga;

/**
 * OpDecodificacion
 * Operador de decodificacion
 * @author Ines
 * @version 2021.11.*
 */
public interface OpDecodificacion<A> {

	/**
	 * apply
	 * @param cromosoma (genotipo)
	 * 			el cromosoma o individuo de la poblacion representando una solucion
	 * @returns la solucion (fenotipo), es decir, el cromosoma decodificado
	 */
	public Solucion apply ( Individuo<A> cromosoma );
	
}
