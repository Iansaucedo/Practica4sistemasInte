package tsp;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase ProblemaTSP
 * Representa una instancia de un problema de TSP: nodos y distancias entre ellos
 * @author Ines
 * @version 2019.10
 */
/**
 * @author Ines
 *
 */
public class ProblemaTSP {
	private Ciudad[] ciudades; // ciudades a visitar (incluyendo ciudad 0, origen)
	private double[][] distancias; // distancias entre ciudades

	// CONSTRUCTORES
	/**
	 * Constructor a partir de array de nombres de ciudades y array de distancias entre ciudades
	 * @param nombresCiudades, vector de nombres
	 * @param distancias, matriz de distancias
	 */
	public ProblemaTSP( String[] nombresCiudades, double[][] distancias ) {
		assert( nombresCiudades.length == distancias.length && nombresCiudades.length == distancias[0].length );
		setCiudades( nombresCiudades );
		setDistancias( distancias );
	}
	
	/**
	 * constructor a partir de fichero
	 * @param nomFich, el nombre del fichero con los datos
	 */

	public ProblemaTSP( String nomFich ) {
		try {
			leeDeFichero( nomFich );
		}
		catch(Exception e) {
			System.out.println("Problemas con la lectura de fichero" + e );
		}
	}
	
	/**
	 * Constructor a partir de otro problema
	 * @param otroP
	 */
	public ProblemaTSP( ProblemaTSP otro ) {
		setCiudades( otro.getCiudades());
		setDistancias(otro.getDistancias());
	}

	// OBSERVADORES Y MODIFICADORES
	/**
	 * @return el vector de Ciudades
	 */
	public Ciudad[] getCiudades() {
		return ciudades;
	}
	
   
	/**
	 * @param i
	 * @return la ciudad i-esima
	 */
	public Ciudad getCiudad( int i ) {
    	return ciudades[i];
    }
	
	/** modificador a partir de un vector con nombres de ciudades
	 * @param nombresCiudades
	 */
	public void setCiudades ( String[] nombresCiudades ) {
		int numCiudadesConOri = nombresCiudades.length;
		ciudades = new Ciudad[numCiudadesConOri];
		for( int i=0; i<numCiudadesConOri; i++ ) {
			ciudades[i]=new Ciudad(i, nombresCiudades[i]);
		}
	}
	
	/** modificador "copia" del vector de Ciudades
	 * @param vCiudades
	 */
	public void setCiudades ( Ciudad[] vCiudades ) {
		int numCiudadesConOri = vCiudades.length;
		ciudades = new Ciudad[numCiudadesConOri];
		for( int i=0; i<numCiudadesConOri; i++ ) {
			ciudades[i]=vCiudades[i];
		}
	}
	
	/** modificador "copia" de la matriz de distancias
	 * @param dist
	 */
	public void setDistancias( double[][] dist ) {
		distancias = new double[dist.length][dist[0].length];
		for( int i=0; i<dist.length; i++)
			for( int j=0; j<dist[0].length; j++ )
				distancias[i][j]=dist[i][j];
	}
	
	/** observador
	 * @return la matriz de distancias
	 */
	public double[][] getDistancias(){
		return distancias;
	}
	
	
	/**
	 * @return el numero de ciudades (sin contar el origen)
	 */
	public int getNumCiudades() {
		return ciudades.length-1; // no contamos el origen
	}
	
	/**
	 * @param i
	 * @param j
	 * @return la distancia entre las ciudades i y j
	 */
	public double getDistEntre(int i, int j) {
		if( 0<=i && i<= getNumCiudades() && 0<=j && j<=getNumCiudades() )
			return distancias[i][j];
		else
			return Integer.MAX_VALUE;
	}
	
	/**
	 * @param i
	 * @param j
	 * @param v
	 * modifica la distancia entre i y j al valor v
	 */
	public void setDistEntre(int i, int j, double v) {
		if( 0<=i && i<= getNumCiudades() && 0<=j && j<=getNumCiudades() )
			distancias[i][j]=v;
	}
	
	/**
	 * @return el origen del tour
	 */
	public Ciudad getOrigen() {
		return ciudades[0];
	}
	
	/**
	 * @return el alfabeto para los cromosomas, es decir, los posibles ids de ciudades
	 */
	public List<Integer> getAlfabeto(){
		int n = getNumCiudades();
		List<Integer> alfabeto = new ArrayList<Integer>(n);
		for( int i=1; i<=n; i++ ) {
			alfabeto.add(i);
		}
		return alfabeto;
	}
	
	// METODOS PRIVADOS (AUXILIARES)

	
	/** Metodo algo chapuza para leer de fichero el problema
	 * Se supone que el fichero sigue el formato de tsplib
	 * (ver http://vrp.atd-lab.inf.puc-rio.br/attachments/article/6/TSPLIB%2095.pdf)
	 * por el momento solo lee distancias explicitas en 2 formatos concretos
	 * @param nomFich
	 * @throws Exception
	 */
	private void leeDeFichero( String nomFich ) throws Exception {
		int dimension = 0;
		try {
			// 
			Scanner dis = new Scanner(new FileInputStream(nomFich));
			String s="";
			// tiene que ser de tipo TSP
			while (dis.hasNext() && !s.contains("TSP")) {
				s = dis.next();
			}
			if (!s.contains("TSP")) {
				dis.close();
				throw new Exception("Mal los datos... no es un problema TSP");
			}
			// LEEMOS DIMENSION (numero de ciudades incluido el origen)
			while (dis.hasNext() && !s.contains("DIMENSION:")) {
				s = dis.next();
			}
			if (s.contains("DIMENSION:")) {
				dimension = dis.nextInt();
				System.out.println("leida dimension: " + dimension);
				s=dis.nextLine(); // terminamos la linea
			}
			else {
				dis.close();
				throw new Exception("Mal los datos... no hay dimension");
			}
			// CREAMOS LISTA DE CIUDADES
			Ciudad[] vCity = new Ciudad [dimension];		    
			for (int i = 0; i<dimension; i++){
				vCity[i] = new Ciudad(i);
			}
			setCiudades(vCity);
			// Comprobamos que se dan las distancias explicitas
			s=dis.nextLine();
			if(!s.contains("EXPLICIT")) {
				dis.close();
				throw new Exception("Mal los datos ... los pesos no son explicitos");
			}
			// leemos el formato en que se dan las distancias
			s=dis.next();
			if(!s.equals("EDGE_WEIGHT_FORMAT:")) {
				dis.close();
				throw new Exception("Mal los datos ... no se da el formato de los pesos");
			}
			String formato=dis.next(); // formato de las distancias
			dis.nextLine();// terminamos la linea
			dis.nextLine();// leemos la siguiente linea "EDGE_WEIGHT_SECTION"
			// LEEMOS DISTANCIAS
			double[][] dist = new double[dimension][dimension];
			switch(formato) {
			case "UPPER_ROW": // matriz triangular superior sin diagonal
				for (int i = 0; i < dimension-1; i++) {
					for (int j = i+1; j < dimension; j++) {
						int x = dis.nextInt();
						dist[i][j] = dist[j][i] = x;
					}
				}
				
				break;
			case "LOWER_DIAG_ROW": // matriz diagonal inferior incluida diagonal		
				for (int i=0; i<dimension; i++){
					for (int j=0; j<i+1; j++){
						int x = dis.nextInt();
						dist[i][j]=dist[j][i]= (double)x; // aniade distancia simetrica
					}
				}
				break;
			default:
				dis.close();
				throw new Exception("Mal los datos ... Formato no soportado");
			
			}
			dis.close();
			setDistancias(dist);
		}// fin try
		catch(Exception e) {
			throw e;

		}// fin catch

	}// fin metodo leer de fichero

}
