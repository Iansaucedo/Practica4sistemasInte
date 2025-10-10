package ga;

import java.util.List;

/**
 * OpSelRuleta
 * Clase que implementa el operador de seleccion por ruleta
 * @param A el tipo de los elementos del cromosoma
 * @version 2018.11.*
 * @author Ines
 */
public class OpSelRuleta<A> extends OpSeleccion<A> {

	@Override
	public Individuo<A> apply(List<Individuo<A>> population) {
		// RANDOM-SELECTION(population, FITNESS-FN)
		// roulette selection (probability of being selected is proportional to individual's fitness)
			// Default result is last individual
			// (just to avoid problems with rounding errors)
			Individuo<A> selectedInd = population.get(population.size() - 1);
			// Obtain all the fitness values
			double[] fValues = new double[population.size()];
			for (int i = 0; i < population.size(); i++) {
				fValues[i] = population.get(i).getFitness();
			}
			// Normalize the fitness values
			fValues = Util.normalize(fValues);
			double prob = Util.randomDouble();
			double totalSoFar = 0.0;
			boolean selected = false;
			for (int i = 0; i < fValues.length && !selected; i++) {
				totalSoFar += fValues[i];
				if (prob <= totalSoFar) {
					selectedInd = population.get(i);
					selected = true;
				}
			}
			return selectedInd;
		}

	}

