package tsp;

/** 
 * Clase para representar una ciudad del TSP
 * @author Ines
 */
public class Ciudad {
	
	private int id;
	private String nombre;

	/**
	 * 
	 */
	public Ciudad( int id, String nombre ) {
		this.id=id;
		this.nombre=nombre;
	}
	
	public Ciudad( int id ) {
		this(id, "Ciudad-"+id);
	}
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String toString() {
		return "Id: "+ id + ", nombre: "+ nombre;
	}
	
	public boolean equals( Object o ) {
		if( o instanceof Ciudad )
			return this.getId()==((Ciudad)o).getId();
		return false;
	}

}
