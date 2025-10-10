package ga;

import java.util.List;

/**
 * OpSeleccion
 * Clase abstracta para el operador de seleccion
 * @param A el tipo de los elementos del cromosoma
 * @version 2018.11.*
 * @author Ines
 */
public abstract class OpSeleccion<A> {

	abstract public Individuo<A> apply(List<Individuo<A>> population);

}
