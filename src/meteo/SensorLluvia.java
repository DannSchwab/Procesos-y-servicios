package meteo;

public class SensorLluvia implements Runnable {

	private int[] litros;
	private int total;
	private int horasConLluvia;
	private int rachaMaxima;

	public SensorLluvia(int[] litros) {
		this.litros = litros;
	}

	public int getTotal() {
		return total;
	}

	public int getHorasConLluvia() {
		return horasConLluvia;
	}

	public int getRachaMaxima() {
		return rachaMaxima;
	}

	@Override
	public void run() {
		Thread hilo = Thread.currentThread();
		String nombre = hilo.getName();
		System.out.println(nombre + " empieza (prioridad " + hilo.getPriority() + ")");

		int suma = 0;
		int horas = 0;
		int rachaActual = 0;
		int racha = 0;

		for (int h = 0; h < 24; h++) {
			int valor = litros[h];
			suma += valor;
			if (valor > 0) {
				horas++;
				rachaActual++;
				if (rachaActual > racha) {
					racha = rachaActual;
				}
			} else {
				rachaActual = 0;
			}

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

		total = suma;
		horasConLluvia = horas;
		rachaMaxima = racha;

		System.out.println(nombre + " termina: " + total + " l/m2, " + horasConLluvia
				+ " horas con lluvia, racha máxima " + rachaMaxima + " h");
	}
}
