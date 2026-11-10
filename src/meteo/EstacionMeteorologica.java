package meteo;

public class EstacionMeteorologica {

	public static void main(String[] args) {
		int[] grados = {8, 7, 6, 6, 5, 5, 6, 8, 11, 14, 17, 19,
				21, 22, 23, 23, 22, 20, 17, 15, 13, 11, 10, 9};
		int[] litros = {0, 0, 2, 3, 1, 0, 0, 0, 0, 0, 0, 4,
				6, 5, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0};

		// Se instancian las clases
		SensorTemperatura temperatura = new SensorTemperatura(grados);
		SensorLluvia lluvia = new SensorLluvia(litros);
		GeneradorAlertas alertas = new GeneradorAlertas(temperatura, lluvia);

		// Se instancian los hilos en función de los objetos creados
		Thread hTemperatura = new Thread(temperatura, "Temperatura");
		Thread hLluvia = new Thread(lluvia, "Lluvia");
		Thread hAlertas = new Thread(alertas, "Alertas");

		// Prioridades antes de start()
		hTemperatura.setPriority(Thread.MAX_PRIORITY);
		hLluvia.setPriority(Thread.MIN_PRIORITY);
		hAlertas.setPriority(Thread.NORM_PRIORITY);

		// Inician ambos hilos
		hTemperatura.start();
		hLluvia.start();

		try {
			// Se espera a que terminen los hilos
			hTemperatura.join();
			hLluvia.join();
		} catch (InterruptedException e) {
			System.out.println("El main ha sido interrumpido");
		}

		System.out.println("Sensores: " + hTemperatura.getState() + " y " + hLluvia.getState() + ".");
		System.out.println(hAlertas.getName() + " está " + hAlertas.getState() + ": se le da la orden.");

		hAlertas.start();

		try {
			hAlertas.join();
		} catch (InterruptedException e) {
			System.out.println("El main ha sido interrumpido");
		}

		System.out.println("=== Parte del día ===");
		System.out.println("Temperatura: máx " + (int) temperatura.getMaxima() + ", mín "
				+ (int) temperatura.getMinima() + ", media " + temperatura.getMedia());
		System.out.println("Lluvia: " + lluvia.getTotal() + " l/m2, racha máxima "
				+ lluvia.getRachaMaxima() + " h");
		System.out.println("Alerta: " + alertas.getAlerta());
	}
}
