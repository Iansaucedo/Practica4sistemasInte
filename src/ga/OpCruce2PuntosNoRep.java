package ga;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase OpCruce2PuntosNoRep
 * implements 2-point crossover with no repetitions (OX)
 * 
 * @param A el tipo de los elementos que componen un crosomosoma (genes)
 * @author Ines
 * @version 2018.11.*
 */
public class OpCruce2PuntosNoRep<A> extends OpCruce<A> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ga.OpCruce#apply(int, ga.Individuo, ga.Individuo)
	 */
	@Override
	public List<Individuo<A>> apply(int nchildren, Individuo<A> parent1, Individuo<A> parent2) {

		assert (nchildren > 0 && nchildren <= 2); // can generate 1 or 2 children
		List<Individuo<A>> children = new ArrayList<>(nchildren); // list of children

		// Generate two random crossover points
		int c1 = Util.randomInt(parent1.length() - 1); // first crossover point
		int c2 = c1 + 1 + Util.randomInt(parent1.length() - c1 - 1); // second crossover point

		children.add(0, cross2(c1, c2, parent1, parent2));
		if (nchildren == 2) {
			children.add(1, cross2(c1, c2, parent2, parent1)); // exchange parent roles for 2nd child
		}

		return children;
	}

	/**
	 * Two-point Order Crossover (OX)
	 * 
	 * @param c1 first crossover point
	 * @param c2 second crossover point
	 * @param x  first parent
	 * @param y  second parent
	 * @return child chromosome
	 */
	private Individuo<A> cross2(int c1, int c2, Individuo<A> x, Individuo<A> y) {
		List<A> xChromosome = x.getRepresentation();
		List<A> yChromosome = y.getRepresentation();
		List<A> childChromosome = new ArrayList<>(x.length());

		// Initialize child chromosome with nulls
		for (int i = 0; i < x.length(); i++) {
			childChromosome.add(null);
		}

		// Copy the segment between crossover points from first parent (maintaining
		// position)
		for (int i = c1; i <= c2; i++) {
			childChromosome.set(i, xChromosome.get(i));
		}

		// Fill the remaining positions with genes from second parent (maintaining
		// relative order)
		int k = (c2 + 1) % x.length(); // current position in child
		for (int i = 0; i < y.length(); i++) {
			int ypos = (c2 + 1 + i) % y.length(); // position in second parent
			A gene = yChromosome.get(ypos);

			// Check if the gene is already in the middle segment
			boolean found = false;
			for (int j = c1; j <= c2; j++) {
				if (gene.equals(childChromosome.get(j))) {
					found = true;
					break;
				}
			}

			// If gene not found in middle segment, add it to child
			if (!found) {
				while (childChromosome.get(k) != null) {
					k = (k + 1) % x.length();
				}
				childChromosome.set(k, gene);
				k = (k + 1) % x.length();
			}
		}

		return new Individuo<>(childChromosome);
	}

}
