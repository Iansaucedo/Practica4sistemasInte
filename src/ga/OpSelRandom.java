package ga;

import java.util.List;

/**
 * OpSelRandom
 * Clase que implementa un operador de seleccion aleatorio
 * @param A el tipo de los elementos del cromosoma
 * @version 2018.11.*
 * @author Ines
 */

public class OpSelRandom<A> extends OpSeleccion<A> {
	
	@Override
	public Individuo<A> apply(List<Individuo<A>> population) {
		// RANDOM-SELECTION(population, FITNESS-FN)
		int r = Util.randomInt(population.size());
		return population.get(r);
	}

	}

