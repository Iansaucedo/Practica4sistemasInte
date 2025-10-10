/**
 * OpMutacionAltNoRep<A>
 * applies mutation by altering one alele: randomly changes its value to another element of the alphabet
 */
package ga;

import java.util.LinkedList;
import java.util.List;

/**
 * Clase del operador de mutacion "flip" para cromosomas sin repeticion
 * @param A el tipo de los elementos del cromosoma
 * @author Ines
 * @version 2018.11.*
 */
public class OpMutacionAltNoRep<A> extends OpMutacion<A> {
	
	private List<A> finiteAlphabet;
	
	// CONSTRUCTOR
	public OpMutacionAltNoRep ( List<A> finiteAlphabet ){
		this.finiteAlphabet = finiteAlphabet;
	}

	/* (non-Javadoc)
	 * @see ga.OpMutacion#apply(java.util.List, ga.Individuo)
	 */
	@Override
	public Individuo<A> apply(Individuo<A> individual ) {			
			List<A> x = new LinkedList<A>(individual.getRepresentation());
			int pos = Util.randomInt(individual.length());// posicion a modificar
			int nValPos = Util.randomInt(finiteAlphabet.size());
			A nVal = finiteAlphabet.get(nValPos);// nuevo valor aleatorio de entre todos los posibles
			A oldVal = x.get(pos);
			x.set(pos,nVal);
			// reparar para evitar repeticiones
			int i=0;
			while(x.get(i)!=nVal) i++;
			x.set(i,oldVal);
			return (new Individuo<A>(x));
		}
	}
