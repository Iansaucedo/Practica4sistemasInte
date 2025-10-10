package ga;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase OpGenRandNoRep
 * generates a new random chromosome without repetitions
 * @param A el tipo de los elementos del cromosoma
 * @author Ines
 * @version 2018.11.*
 */

public class OpGenRandNoRep<A> extends OpGeneracion<A> {

	public OpGenRandNoRep(List<A> alphabet, int indLength) {
		super(alphabet, indLength);
	}

	@Override
	public Individuo<A> apply() {
		// it only makes sense if the individual's length equals the number of distinct elements in the alphabet
		assert(this.finiteAlphabet.size()==this.individualLength);
		// Genera un cromosoma SIN repeticiones
		List<A> ind = new ArrayList<A>(this.finiteAlphabet);
		for (int i = 0; i < this.individualLength; i++) {// shuffle
			int j = Util.randomInt(this.individualLength); // obtain a random position j
			// swap elements at positions i and j
			A a = ind.get(i);
			ind.set(i,ind.get(j));
			ind.set(j,a);
		}
		return new Individuo<A>(ind);
	}
}

