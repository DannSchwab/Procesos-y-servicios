package meteo;

public class SensorTemperatura implements Runnable {

	private int[] grados;
	private double maxima;
	private double minima;
	private double media;

	public SensorTemperatura(int[] grados) {
		this.grados = grados;
	}

	public double getMaxima() {
		return maxima;
	}

	public double getMinima() {
		return minima;
	}

	public double getMedia() {
		return media;
	}

	@Override
	public void run() {
		Thread hilo = Thread.currentThread();
		String nombre = hilo.getName();
		System.out.println(nombre + " empieza (prioridad " + hilo.getPriority() + ")");

		// La máxima y la mínima empiezan con la primera lectura
		double max = grados[0];
		double min = grados[0];
		double suma = 0;

		for (int h = 0; h < 24; h++) {
			int valor = grados[h];
			if (valor > max) {
				max = valor;
			}
			if (valor < min) {
				min = valor;
			}
			suma += valor;

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(nombre + " interrumpido");
				return;
			}

			if ((h + 1) % 6 == 0) {
				System.out.println(nombre + ": " + (h + 1) + " horas procesadas");
			}
		}

		maxima = max;
		minima = min;
		media = Math.round(suma * 10.0 / 24) / 10.0;

		System.out.println(nombre + " termina: máx " + (int) maxima + ", mín " + (int) minima
				+ ", media " + media);
	}
}
