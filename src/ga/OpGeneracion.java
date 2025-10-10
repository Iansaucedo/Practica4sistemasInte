package ga;

import java.util.List;

/**
 * OpGeneracion
 * Clase abstracta para representar un operador para crear individuos
 * @param A el tipo de los elementos del cromosoma
 * @author Ines
 * @version 2017.11.*
 */
public abstract class OpGeneracion<A> {
	@SuppressWarnings("unused")
	protected List<A> finiteAlphabet; // alphabet of symbols
	@SuppressWarnings("unused")
	protected int individualLength; // length of an individual

	/**
	 * Constructor
	 */
	public OpGeneracion( List<A> alphabet, int indLength ) {
		this.finiteAlphabet = alphabet;
		this.individualLength = indLength;
	}
	
	/**
	 * Metodo para aplicar el operador
	 * @return un individuo (cromosoma) de la poblacion
	 */
	abstract public Individuo<A> apply();

}
