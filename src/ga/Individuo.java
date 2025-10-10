package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Individuo de una poblacion en un GA
 * 
 * @author Ciaran O'Reilly
 * @author Ines
 * 
 * @param <A>
 *            the type of the alphabet used in the representation of the
 *            individuals in the population (this is to provide flexibility in
 *            terms of how a problem can be encoded).
 *            
 * @version 2017.11.*
 */
public class Individuo<A> {
	private List<A> representation = new ArrayList<>();
	private double fitness;

	/**
	 * Construct an individual using the provided representation.
	 * 
	 * @param representation
	 *            the individual's representation.
	 */
	public Individuo(List<A> representation) {
		this(representation, 0.0);
	}
	
	/**
	 * Construct an individual using the provided representation and fitness value
	 * 
	 * @param representation
	 *            the individual's representation.
	 */
	public Individuo(List<A> representation, double fitness) {
		this.representation = Collections.unmodifiableList(representation);
		this.fitness=fitness; // default value
	}
	
	/**
	 * @return the individual's fitness
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * @param the individual's fitness
	 */
	public void setFitness(double fv) {
		fitness = fv;
	}

	/**
	 * 
	 * @return the individual's representation.
	 */
	public List<A> getRepresentation() {
		return representation;
	}

	/**
	 * 
	 * @return the length of the individual's representation.
	 */
	public int length() {
		return representation.size();
	}

	

	@Override
	public String toString() {
		return representation.toString();
	}
}