package apuntesSincronización;

public class Restaurante {
	
	public static void main(String[]args) {
		
		LibroComandas libro = new LibroComandas();
		
		Thread ana = new Thread (new Cocinero(libro, 100), "ana");
		Thread luis = new Thread (new Cocinero(libro, 100), "luis");
		Thread marta = new Thread (new Cocinero(libro, 100), "marta");
		Thread juan = new Thread (new Cocinero(libro, 100), "juan");
	
		ana.start();
		luis.start();
		marta.start();
		juan.start();
		try {
			ana.join();
			luis.join();
			marta.join();
			juan.join();
		} catch (InterruptedException e) {
			System.out.println("Han interrumpido el hilo");
		}
		System.out.println("Platos esperados: 400");
		System.out.println("Platos en el libro: " + libro.getPlatos());
		
	}
	
	// La condición de carrera se da cuando varios hilos intentan acceder a algo a la vez y no hay exclusión mutua (sin synchronized)

	// El método getPlatos() no necesita synchronized porque no se ejecuta en el run() de la clase, por lo que no acceden a la vez
}
