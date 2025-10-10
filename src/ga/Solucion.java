/**
 * Interfaz "dummy" para representar una solucion del problema
 */
package ga;

/**
 * @author Ines
 * @version 2021.10.*
 */
public interface Solucion {
	
	
	/** Observador que devuelve el fitness/calidad de una solucion:
	 * - si se trata de un problema de maximizacion, coincidira con el valor de la funcion objetivo
	 * - si se trata de un problema de minimizacion, debera crecer a medida que decrece la funcion objetivo
	 * Suponemos que se trata de un valor no negativo
	 * @return un valor numerico, mayor cuanto mayor sea la calidad de la solucion
	 */
	double getFitness();
}
