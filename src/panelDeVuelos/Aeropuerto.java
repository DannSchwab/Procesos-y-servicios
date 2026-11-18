package panelDeVuelos;

public class Aeropuerto {
	
	final static int LECTURAS = 6;
	final static int MS_PAUSA = 150;
	final static int ACTUALIZACIONES = 4;
	
	public static void main(String[] args) {
		
		long inicio = System.currentTimeMillis();
		
		Panel miPanel = new Panel(15);
		ControlPanel control = new ControlPanel();
		
		Pantalla pantalla1 = new Pantalla(miPanel, control, LECTURAS, MS_PAUSA);
		Pantalla pantalla2 = new Pantalla(miPanel, control, LECTURAS, MS_PAUSA);
		Pantalla pantalla3 = new Pantalla(miPanel, control, LECTURAS, MS_PAUSA);
		
		Pantalla[] pantallas = {pantalla1, pantalla2, pantalla3};
		Thread[] hilosPantallas = new Thread[pantallas.length];
		
		// 1. Creamos y arrancamos los hilos de las pantallas
		for (int i = 0; i < pantallas.length; i++) {
			hilosPantallas[i] = new Thread(pantallas[i], "Pantalla-" + (i + 1));
			hilosPantallas[i].start();
		}
		
		// 2. Creamos y arrancamos el hilo del operador
		Thread operador = new Thread(new Operador(miPanel, control, ACTUALIZACIONES), "manolo");
		operador.start();
		
		// 3. Esperamos a que todos los hilos terminen antes de imprimir el resumen
		try {
			for (Thread h : hilosPantallas) {
				h.join();
			}
			operador.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		
		long tiempoTotal = System.currentTimeMillis() - inicio;
		
		// 4. Sumamos las lecturas incoherentes de las 3 pantallas
		int totalIncoherentes = 0;
		for (Pantalla p : pantallas) {
			totalIncoherentes += p.getIncoherentes();
		}
		
		System.out.println("=== Fin de la prueba del panel ===\n"
				+ "Lecturas incoherentes: " + totalIncoherentes
				+ ", Máximo de pantallas leyendo a la vez: " + control.getMaxLectores()
				+ ", Panel final: retraso " + miPanel.getRetraso()
				+ " min, salida " + miPanel.getSalida()
				+ " y Tiempo: " + tiempoTotal + " ms.");
	}
}