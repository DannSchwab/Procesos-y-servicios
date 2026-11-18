package panelDeVuelos;

public class ControlPanel {
	
	private int lectores;
	private boolean escribiendo;
	private int maxLectores;
	
	public ControlPanel() {
		this.lectores = 0;
		this.escribiendo = false;
		this.maxLectores = 3;
	}

	// Pantalla
	public synchronized void empezarLectura() throws InterruptedException {
		while (escribiendo || lectores >= maxLectores) {
		    wait();
		}
		lectores++;
	}

	// Pantalla
	public synchronized  void terminarLectura() {
		lectores--;
		if (lectores == 0 || lectores == maxLectores - 1) notifyAll();
	}
	
	
	// Operador
	public synchronized void empezarEscritura() throws InterruptedException {
		while(lectores != 0) {
			wait();
		}
		escribiendo = true;
	}

	// Operador
	public synchronized void terminarEscritura() {
		escribiendo = false;
		notifyAll();
	}
	
	public int getMaxLectores(){
		return this.maxLectores;
	}

}
