package panelDeVuelos;

public class Pantalla implements Runnable {

	private Panel panel;
	private ControlPanel control;
	private int lecturas;
	private int msPausa;
	private int incoherentes;

	public Pantalla(Panel panel, ControlPanel control, int lecturas, int msPausa) {
		this.panel = panel;
		this.control = control;
		this.lecturas = lecturas;
		this.msPausa = msPausa;
		this.incoherentes = 0;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < this.lecturas; i++) {
				control.empezarLectura();
				
				int retraso = panel.getRetraso();
				Thread.sleep(msPausa); // Pausa el hilo sin soltar el permiso de lectura
				String salida = panel.getSalida();
				
				// Comprobamos coherencia convirtiendo los minutos al formato "H:M"
				String salidaEsperada = panel.hora(panel.PREVISTA + retraso);
				if (!salidaEsperada.equals(salida)) {
					incoherentes++;
				}
				
				control.terminarLectura();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

	public int getIncoherentes() {
		return incoherentes;
	}
}