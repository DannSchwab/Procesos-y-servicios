package apuntesSincronización;

public class Cocinero implements Runnable {
	
	private LibroComandas libro;
	private int platos;
	public Cocinero(LibroComandas libro, int platos) {
		this.libro = libro;
		this.platos = platos;
	}

	@Override
	public void run() {
		for (int i = 1; i <= platos; i++){
			libro.apuntar();
		}
		System.out.println(Thread.currentThread().getName() + " ha apuntado " + platos + " platos");
	}

}
