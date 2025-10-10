package ga;

/**
 * Goal Test based on the ga reaching a maximum number of iterations
 * @author Ines
 * @version 2017.11.*
 */
public class GoalTestMaxIter<A> implements GoalTest<Individuo<A>> {
	private int maxIter;
	private AlgoritmoGenetico<A> ga;

	/**
	 * 
	 */
	public GoalTestMaxIter(AlgoritmoGenetico<A> genalg, final int maxIterations) {
		ga=genalg;
		maxIter = maxIterations;
	}

	/* (non-Javadoc)
	 * @see ga.GoalTest#isGoalState(java.lang.Object)
	 */
	@Override
	public boolean test(Individuo<A> state) {
		return ga.getIterations()>=maxIter;
	}

}
