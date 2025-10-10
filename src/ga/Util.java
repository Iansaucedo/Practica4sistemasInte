package ga;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase con funcionalidades auxiliares
 * @author Ravi Mohan
 * @author Ruediger Lunde
 * @author ines
 * adapted from Util.java class in AIMA-Java
 */
public class Util {

	private static Random random = new Random();

	private static final double EPSILON = 0.000000000001;

	public static int randomInt(int bound) {
		return random.nextInt(bound);
	}

	public static boolean randomBoolean() {
		return random.nextInt(2) == 1;
	}
	
	public static double randomDouble() {
		return random.nextDouble();
	}

	public static double[] normalize(double[] probDist) {
		int len = probDist.length;
		double total = 0.0;
		for (double d : probDist) {
			total = total + d;
		}

		double[] normalized = new double[len];
		if (total != 0) {
			for (int i = 0; i < len; i++) {
				normalized[i] = probDist[i] / total;
			}
		}

		return normalized;
	}

	public static List<Double> normalize(List<Double> values) {
		double[] valuesAsArray = new double[values.size()];
		for (int i = 0; i < valuesAsArray.length; i++)
			valuesAsArray[i] = values.get(i);
		double[] normalized = normalize(valuesAsArray);
		List<Double> results = new ArrayList<>();
		for (double aNormalized : normalized)
			results.add(aNormalized);
		return results;
	}

	public static int min(int i, int j) {
		return (i > j ? j : i);
	}

	public static int max(int i, int j) {
		return (i < j ? j : i);
	}

	public static int max(int i, int j, int k) {
		return max(max(i, j), k);
	}

	public static int min(int i, int j, int k) {
		return min(min(i, j), k);
	}


	public static int randomNumberBetween(int i, int j) {
		/* i,j bothinclusive */
		return random.nextInt(j - i + 1) + i;
	}

	public static double calculateMean(List<Double> lst) {
		Double sum = 0.0;
		for (Double d : lst) {
			sum = sum + d;
		}
		return sum / lst.size();
	}

	public static double calculateStDev(List<Double> values, double mean) {

		int listSize = values.size();

		Double sumOfDiffSquared = 0.0;
		for (Double value : values) {
			double diffFromMean = value - mean;
			sumOfDiffSquared += ((diffFromMean * diffFromMean) / (listSize - 1));
			// division moved here to avoid sum becoming too big if this
			// doesn't work use incremental formulation

		}
		double variance = sumOfDiffSquared;
		// (listSize - 1);
		// assumes at least 2 members in list.
		return Math.sqrt(variance);
	}

	public static List<Double> normalizeFromMeanAndStdev(List<Double> values, double mean, double stdev) {
		return values.stream().map(d -> (d - mean) / stdev).collect(Collectors.toList());
	}
	
	/**
	 * Generates a random double between two limits. Both limits are inclusive.
	 * @param lowerLimit the lower limit.
	 * @param upperLimit the upper limit.
	 * @return a random double bigger or equals {@code lowerLimit} and smaller or equals {@code upperLimit}.
	 */
	public static double generateRandomDoubleBetween(double lowerLimit, double upperLimit) {
		return lowerLimit + ((upperLimit - lowerLimit) * random.nextDouble());
	}

	/**
	 * Generates a random float between two limits. Both limits are inclusive.
	 * @param lowerLimit the lower limit.
	 * @param upperLimit the upper limit.
	 * @return a random float bigger or equals {@code lowerLimit} and smaller or equals {@code upperLimit}.
	 */
	public static float generateRandomFloatBetween(float lowerLimit, float upperLimit) {
		return lowerLimit + ((upperLimit - lowerLimit) * random.nextFloat());
	}
	
	/**
	 * Compares two doubles for equality.
	 * @param a the first double.
	 * @param b the second double.
	 * @return true if both doubles contain the same value or the absolute deviation between them is below {@code EPSILON}.
	 */
	public static boolean compareDoubles(double a, double b) {
		if(Double.isNaN(a) && Double.isNaN(b)) return true;
		if(!Double.isInfinite(a) && !Double.isInfinite(b)) return Math.abs(a-b) <= EPSILON;
		return a == b;
	}
	
	/**
	 * Compares two floats for equality.
	 * @param a the first floats.
	 * @param b the second floats.
	 * @return true if both floats contain the same value or the absolute deviation between them is below {@code EPSILON}.
	 */
	public static boolean compareFloats(float a, float b) {
		if(Float.isNaN(a) && Float.isNaN(b)) return true;
		if(!Float.isInfinite(a) && !Float.isInfinite(b)) return Math.abs(a-b) <= EPSILON;
		return a == b;
	}
}