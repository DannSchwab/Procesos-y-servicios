package panelDeVuelos;

public class Operador implements Runnable{

	private Panel panel;
	private ControlPanel control;
	private int actualizaciones;
		
	public Operador(Panel panel, ControlPanel control, int actualizaciones) {
		this.panel = panel;
		this.control = control;
		this.actualizaciones = actualizaciones;
	}

	@Override
	public void run() {
		for(int i = 0; i < actualizaciones; i++) {
			try {
				Thread.sleep(250);
				control.empezarEscritura();
				int retraso = panel.getRetraso() + 15;
				Thread.sleep(300);
				panel.setSalida(retraso);
				control.terminarEscritura();
				System.out.println("Operador actualiza: Retraso " + retraso + "min, salida " + panel.getSalida());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
