package tsp;

import java.util.ArrayList;
import java.util.List;

import ga.Solucion;

/**
 * Clase para representar una solucion del TSP, es decir,
 * una ruta (mas o menos costosa) que pase por todas las ciudades
 * y que solo pase una vez por cada ciudad
 * @author Ines
 * @version 2017.11.*
 */
public class SolucionTSP implements Solucion{
	private static ProblemaTSP p;// problema para el que es solucion
	private List<Ciudad> recorrido; // recorrido/ruta de la solucion
	private double coste; // coste de este recorrido

	/**
	 * Constructor
	 * @param p la instancia del problema de TSP para la que es solucion
	 */
	public SolucionTSP( ProblemaTSP p ) {
		SolucionTSP.p=p;
		recorrido=new ArrayList<Ciudad>(p.getNumCiudades()+2); // origen->ciudades->origen
		recorrido.add(p.getOrigen());
		coste=0;
	}
	
	
	/**
	 * getOrigen observador para conocer el origen
	 * @return la ciudad de origen
	 */
	public Ciudad getOrigen() {
		return p.getOrigen();
	}
	
	/**
	 * aniade
	 * Metodo para aniadir una nueva ciudad a una ruta (y actualiza los costes)
	 * @param sgte la Ciudad a aniadir
	 */
	public void aniade(Ciudad sgte) {
		// aniadimos si no esta ya en el recorrido o si "cerramos" volviendo al origen
		if( !recorrido.contains(sgte) || ( recorrido.size()==p.getNumCiudades()+1 && sgte.equals(getOrigen())) ){
			int ult = recorrido.get(recorrido.size()-1).getId(); // ultima ciudad ya en la ruta
			recorrido.add(sgte); // aniadimos al final del recorrido
			coste = coste + p.getDistEntre(ult, sgte.getId()); // actualizamos costes
		}
	}
	
	// OBSERVADORES
	/**
	 * getRecorrido()
	 * @return el recorrido: la lista (ordenada) de ciudades
	 */
	public List<Ciudad> getRecorrido(){
		return recorrido;
	}

	/**
	 * getCoste()
	 * @return el coste (longitud) del recorrido
	 */
	public double getCoste() {
		return coste;
	}
	
	// METODOS HEREDADOS
	
	// de la interfaz Solucion
	public double getFitness() {
		return ( getCoste()!=0 ? 1.0/getCoste() : Double.MAX_VALUE); // devolvemos el inverso del coste de la solucion (si no es 0)
	}
	
	// de Object
	public String toString() {
		String s="";
		for( Ciudad c: recorrido ) {
			s=s + c.getNombre() +" | ";
		}
		return s;
	}

}
