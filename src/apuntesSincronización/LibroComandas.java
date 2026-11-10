package apuntesSincronización;

public class LibroComandas {
	
	private int platos = 0;
	
	// La condición de carrera solo se da cuando synchronized 
	public synchronized void apuntar() {
		platos++;
	}
	
	public int getPlatos(){
		return platos;
	}

}
