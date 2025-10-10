/**
 * 
 */
package ga;

import java.util.List;

/**Clase que implementa el reemplazo generacional, es decir, la poblacion de hijos sustituye a la poblacion original
CON ELITISMO, es decir, se mantiene al mejor individuo de la generacion original
 * @author Ines
 * @param <A> el tipo de los elementos del cromosoma
 * @version 2021.10.*
 *
 */
public class OpReempGenElitista<A> extends OpReemplazo<A> {



	@Override
	public List<Individuo<A>> apply(List<Individuo<A>> oldPopulation, List<Individuo<A>> offspring) {
		Individuo<A> mejor = getMejorIndividuo(oldPopulation); 	// calculamos el mejor de la poblacion anterior
		offspring.set(offspring.size()-1,mejor); 				// machacamos el ultimo individuo de la nueva poblacion con el mejor de la anterior
		return offspring;
	}
	
	/**
	 * getBestIndividual
	 * Metodo para encontrar el mejor individuo de una poblacion
	 * @param population la poblacion
	 * @return el mejor individuo (mayor fitness)
	 */
	public Individuo<A> getMejorIndividuo(List<Individuo<A>> population) {
		Individuo<A> popBestIndividual = null;
		double bestSoFarFValue = Double.NEGATIVE_INFINITY;
		for ( int i=0; i<population.size(); i++ ) {
			double fValue = population.get(i).getFitness();
			if (fValue > bestSoFarFValue) {
				popBestIndividual = population.get(i);
				bestSoFarFValue = fValue;
			}
		}
		return popBestIndividual;
	}

}


