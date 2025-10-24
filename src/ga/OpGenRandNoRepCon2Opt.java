package ga;

import java.util.List;
import tsp.BusquedaLocal2Opt;

/**
 * Clase OpGenRandNoRepCon2Opt
 * Genera un nuevo cromosoma aleatorio sin repeticiones y opcionalmente aplica
 * busqueda local 2-opt para mejorarlo (Lamarckismo)
 * 
 * @param A el tipo de los elementos del cromosoma
 * @author Ian Saucedo
 * @version 2025.10
 */
public class OpGenRandNoRepCon2Opt<A> extends OpGeneracion<A> {
  private BusquedaLocal2Opt busquedaLocal;
  private double probabilidad2Opt;

  /**
   * Constructor
   * 
   * @param alphabet         alfabeto de simbolos
   * @param indLength        longitud del individuo
   * @param busquedaLocal    instancia de busqueda local 2-opt
   * @param probabilidad2Opt probabilidad de aplicar 2-opt (entre 0 y 1)
   */
  public OpGenRandNoRepCon2Opt(List<A> alphabet, int indLength, BusquedaLocal2Opt busquedaLocal,
      double probabilidad2Opt) {
    super(alphabet, indLength);
    this.busquedaLocal = busquedaLocal;
    this.probabilidad2Opt = probabilidad2Opt;
  }

  @Override
  public Individuo<A> apply() {
    // Solo funciona si el tipo es Integer (para TSP)
    assert (this.finiteAlphabet.size() == this.individualLength);

    // Genera un cromosoma SIN repeticiones
    java.util.List<A> ind = new java.util.ArrayList<A>(this.finiteAlphabet);

    // Mezcla aleatoria (shuffle)
    for (int i = 0; i < this.individualLength; i++) {
      int j = Util.randomInt(this.individualLength);
      // Intercambia elementos en posiciones i y j
      A a = ind.get(i);
      ind.set(i, ind.get(j));
      ind.set(j, a);
    }

    Individuo<A> individuo = new Individuo<A>(ind);

    // Aplica busqueda local 2-opt con cierta probabilidad
    if (Math.random() < probabilidad2Opt && busquedaLocal != null) {
      @SuppressWarnings("unchecked")
      Individuo<Integer> individuoInt = (Individuo<Integer>) individuo;
      Individuo<Integer> mejorado = busquedaLocal.mejorar(individuoInt);
      @SuppressWarnings("unchecked")
      Individuo<A> individuoMejorado = (Individuo<A>) mejorado;
      return individuoMejorado;
    }

    return individuo;
  }
}
