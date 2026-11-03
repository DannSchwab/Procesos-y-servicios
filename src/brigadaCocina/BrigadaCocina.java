package brigadaCocina;

public class BrigadaCocina {
	
	public static void main(String[]args) {
		
		System.out.println("Abre la cocina el hilo: " + Thread.currentThread().getName());
		
		// Paso 2 Creamos los hilos 
		Thread Ana = new Thread(new Cocinero("Paellita", 8), "Ana");
		Thread Luis = new Thread(new Cocinero("Gazpacho", 5), "Luis");
		Thread Marta = new Thread(new Cocinero("Tarta", 6), "Marta");
		
		Ana.setPriority(Thread.MAX_PRIORITY);
		Luis.setPriority(Thread.NORM_PRIORITY);
		Marta.setPriority(Thread.MIN_PRIORITY);
		
		
		// Paso 3 Lanzar los hilos
		Ana.start();
		Luis.start();
		Marta.start();
		
		
		try {
			// Pone a dormir el hilo principal (main)
			Thread.sleep(1200);
			
			// Hace que espere a que los cocineros termien antes de empezar
			Ana.join();
			Luis.join();
			Marta.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("");
		System.out.println("==============================");
		System.out.println("Todos los cocineros terminaron");
		System.out.println("==============================");

		System.out.println("Segunda Prueba:");
		
		// Todo esto no funcuionaría porque solo se puede lanzar una vez por programa un hilo
		/*
		 Ana.start();
		Luis.start();

		try {
			// espera a que Luis termine, aunque al mismo tiempo también trabaja Ana
			Luis.join();
			System.out.println("El estado de Ana: " + Ana.getState().toString() + "|| El estado de Luis" + Luis.getState().toString());
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al controlar la espera del proceso");			
		}
		
		System.out.println("");
		System.out.println("==============================");
		System.out.println("Todos los cocineros terminaron");
		System.out.println("==============================");	 
		 
		 */
		
	}

	/*
	 * SetPriority solo sirve cuando hay más hilos que núcleos.
	 * En situaciones normales se debe jugar con el start y el join
	 */
	
	/*
	 * Un hilo solo se puede arrancar UNA vez.
	 * Ciclo de vida: NEW -> RUNNABLE -> (BLOCKED / WAITING / TIMED_WAITING) -> TERMINATED
	 * Si llamas a start() sobre un hilo que no está en NEW -> IllegalThreadStateException.
	 * Para repetir la tarea hay que crear un Thread nuevo (el Runnable sí se puede reutilizar).
	 */
	
}
