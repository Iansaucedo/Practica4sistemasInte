package ga;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase OpCruce1PuntoNoRep
 * implements 1-point crossover with no repetitions
 * @param A el tipo de los elementos que componen un crosomosoma (genes)
 * @author Ines
 * @version 2017.11.*
 */
public class OpCruce1PuntoNoRep<A> extends OpCruce<A> {


	/* (non-Javadoc)
	 * @see ga.OpCruce#apply(int, ga.Individuo, ga.Individuo)
	 */
	@Override
	public List<Individuo<A>> apply(int nchildren, Individuo<A> parent1, Individuo<A> parent2) {
		
		assert(nchildren>0 && nchildren<=2); // can generate 1 or 2 children
		List<Individuo<A>> children = new ArrayList<Individuo<A>>(nchildren); // list of children
		int c = Util.randomInt(parent1.length()); // crossover point
		children.add(0, cross1(c,parent1,parent2));
		if( nchildren == 2 ) {
			children.add(1, cross1(c,parent2,parent1)); // exchange parent roles to produce 2nd child
		}		
		return children;	
	}
	
	/**
	 * Operador de cruce: CRUCE EN UN PUNTO
	 * @param x, un cromosoma
	 * @param y, otro cromosoma
	 * @return el hijo de x e y
	 */
	private Individuo<A> cross1(int c, Individuo<A> x, Individuo<A> y) {
		// n <- LENGTH(x);
		// c <- random number from 1 to n
		List<A> xChromosome = x.getRepresentation(); // chromosome x
		List<A> yChromosome = y.getRepresentation(); // chromosome y
		List<A> childChromosome = new ArrayList<A>(x.length()); // child chromosome
		// The first substring from the first parent, order and position
		for (int k= 0; k<c; k++)
			childChromosome.add(k, xChromosome.get(k));
		// The remaining genes from the second parent, relative order
		int k = c; // need to find value of k-th position in child
		for (int i = 0; i<y.length(); i++) { // traverse y
			int j = 0;
			while (j<c && yChromosome.get(i) != xChromosome.get(j)) j++; // check gene in y is not already copied from x
			if (j == c) {
				childChromosome.add(k, yChromosome.get(i));
				k++;
			}
		}
		Individuo<A> child = new Individuo<A>(childChromosome);
		return child;
	}

}
