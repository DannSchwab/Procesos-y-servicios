package meteo;

public class GeneradorAlertas implements Runnable {

	private SensorTemperatura temperatura;
	private SensorLluvia lluvia;
	private String alerta;

	public GeneradorAlertas(SensorTemperatura temperatura, SensorLluvia lluvia) {
		this.temperatura = temperatura;
		this.lluvia = lluvia;
	}

	public String getAlerta() {
		return alerta;
	}

	@Override
	public void run() {
		Thread hilo = Thread.currentThread();
		String nombre = hilo.getName();
		System.out.println(nombre + " empieza (prioridad " + hilo.getPriority() + ")");

		// Se aplica la primera regla que se cumpla, en este orden
		if (lluvia.getTotal() >= 40) {
			alerta = "ALERTA NARANJA: lluvias intensas";
		} else if (lluvia.getRachaMaxima() >= 6) {
			alerta = "ALERTA AMARILLA: lluvia persistente";
		} else if (temperatura.getMaxima() >= 35) {
			alerta = "ALERTA AMARILLA: calor";
		} else if (temperatura.getMinima() <= 0 && lluvia.getTotal() > 0) {
			alerta = "AVISO: posible nieve";
		} else {
			alerta = "Sin alertas";
		}

		System.out.println(nombre + " termina: " + alerta);
	}
}
