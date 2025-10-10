package ga;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 67.
 * 
 * The goal test, which determines whether a given state is a goal state.
 */

/**
 * @author Ravi Mohan
 * @author Ines
 * 
 */
public interface GoalTest<State> {
	boolean test(State state);
}