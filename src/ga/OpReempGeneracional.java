/**
 * OpReemplazoGeneracional
 */
package ga;

import java.util.List;

/**Clase que implementa el reemplazo generacional, es decir, la poblacion de hijos sustituye a la poblacion original (sin mas)
 * @author Ines
 * @param <A> el tipo de los elementos del cromosoma
 * @version 2021.10.*
 *
 */
public class OpReempGeneracional<A> extends OpReemplazo<A> {

	@Override
	public List<Individuo<A>> apply(List<Individuo<A>> oldPopulation, List<Individuo<A>> offspringPop) {
		return offspringPop;
	}

}
