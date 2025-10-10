package ga;

import java.util.List;

/**
 * OpCruce
 * Operador de cruce abstracto
 * @author Ines
 * @version 2017.11.*
 */
public abstract class OpCruce<A> {

	/**
	 * apply
	 * @param nchildren
	 * 			the number of children to be generated with crossover
	 * @param parent1
	 * 			the first parent
	 * @param parent2
	 * 			the second parent
	 * @returns the list of children
	 */
	public abstract List<Individuo<A>> apply (int nchildren, Individuo<A> parent1, Individuo<A> parent2 );
	
}
