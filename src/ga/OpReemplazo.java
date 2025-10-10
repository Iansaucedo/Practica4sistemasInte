/**
 * Operador de reemplazo
 */
package ga;

import java.util.List;

/** OpReemplazo
 * Clase abstracta para el operador de reemplazo
 * @author Ines
 * @param A el tipo de los elementos del cromosoma
 * @version 2021.10.
 */
public abstract class OpReemplazo<A> {

	abstract public List<Individuo<A>> apply(List<Individuo<A>> oldPopulation, List<Individuo<A>> offspring);

}
