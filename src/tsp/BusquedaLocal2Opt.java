package tsp;  

import java.util.ArrayList;
import java.util.List;
import ga.Individuo;

/**
 * Clase BusquedaLocal2Opt
 * Implementa la busqueda local 2-opt para mejorar las soluciones del TSP.
 * El metodo 2-opt consiste en tomar dos aristas no adyacentes del tour actual
 * y reconectarlas de manera diferente para formar un nuevo tour.
 * 
 * @author Ian Saucedo
 */
public class BusquedaLocal2Opt {
  private final ProblemaTSP problema;

  /**
   * Constructor
   * 
   * @param problema la instancia del TSP sobre la que se aplicara la busqueda
   *                 local
   */
  public BusquedaLocal2Opt(ProblemaTSP problema) {
    this.problema = problema;
  }

  /**
   * Mejora una solucion aplicando el algoritmo 2-opt
   * 
   * @param individuo el individuo a mejorar
   * @return un nuevo individuo con la solucion mejorada
   */
  public Individuo<Integer> mejorar(Individuo<Integer> individuo) {
    List<Integer> mejorRuta = new ArrayList<>(individuo.getRepresentation());
    boolean mejora;

    do {
      mejora = false;
      double mejorDistancia = calcularDistanciaTotal(mejorRuta);

      // Probamos todas las posibles combinaciones de 2-opt
      for (int i = 0; i < mejorRuta.size() - 1 && !mejora; i++) {
        for (int j = i + 2; j < mejorRuta.size(); j++) {
          List<Integer> nuevaRuta = new ArrayList<>(mejorRuta);

          // Invertimos el segmento entre i+1 y j
          invertirSegmento(nuevaRuta, i + 1, j);

          double nuevaDistancia = calcularDistanciaTotal(nuevaRuta);
          if (nuevaDistancia < mejorDistancia) {
            mejorRuta = nuevaRuta;
            mejorDistancia = nuevaDistancia;
            mejora = true;
            break;
          }
        }
      }
    } while (mejora);

    return new Individuo<>(mejorRuta);
  }

  /**
   * Invierte el orden de los elementos en la ruta entre las posiciones inicio y
   * fin
   */
  private void invertirSegmento(List<Integer> ruta, int inicio, int fin) {
    while (inicio < fin) {
      Integer temp = ruta.get(inicio);
      ruta.set(inicio, ruta.get(fin));
      ruta.set(fin, temp);
      inicio++;
      fin--;
    }
  }

  /**
   * Calcula la distancia total de una ruta
   */
  private double calcularDistanciaTotal(List<Integer> ruta) {
    double distancia = 0;
    // Distancia desde el origen a la primera ciudad
    distancia += problema.getDistEntre(0, ruta.get(0));

    // Distancia entre ciudades intermedias
    for (int i = 0; i < ruta.size() - 1; i++) {
      distancia += problema.getDistEntre(ruta.get(i), ruta.get(i + 1));
    }

    // Distancia desde la ultima ciudad al origen
    distancia += problema.getDistEntre(ruta.get(ruta.size() - 1), 0);

    return distancia;
  }
}
