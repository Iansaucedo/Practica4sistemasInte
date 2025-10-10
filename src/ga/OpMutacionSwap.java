package ga;

import java.util.LinkedList;
import java.util.List;

/**
 * OpMutacionSwap
 * Clase del operador de mutacion "swap" para cromosomas sin repeticion
 * @param A el tipo de los elementos del cromosoma
 * @author Ines
 * @version 2018.11.*
 */
public class OpMutacionSwap<A> extends OpMutacion<A> {
	
	/* (non-Javadoc)
	 * @see ga.OpMutacion#apply(java.util.List, ga.Individuo)
	 */
	@Override
	public Individuo<A> apply(Individuo<A> individual ) {			
			List<A> x = new LinkedList<A>(individual.getRepresentation());
			int pos1 = Util.randomInt(individual.length());// posicion del primer elemento a intercambiar
			int pos2 = Util.randomInt(individual.length());// posicion del segundo elemento a intercambiar			
			// swap elements at positions pos2 and pos2
			A aux = x.get(pos1);// nuevo valor aleatorio de entre todos los posibles
			x.set(pos1, x.get(pos2));
			x.set(pos2, aux);
			return (new Individuo<A>(x));
		}
	}
