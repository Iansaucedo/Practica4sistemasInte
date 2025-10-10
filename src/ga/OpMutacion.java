package ga;

/**
 * Abstract class for mutation operator
 * @param A el tipo de los elementos del cromosoma
 * @author Ines
 * @version 2017.11.*
 */
public abstract class OpMutacion<A> {

	/**
	 * apply
	 * @param ind
	 * 			the individual
	 * @returns the mutated individual
	 */
	public abstract Individuo<A> apply (Individuo<A> ind );

}
