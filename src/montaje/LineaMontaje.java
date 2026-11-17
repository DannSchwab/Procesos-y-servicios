package montaje;

public class LineaMontaje {
	
	private int turno;
	private int fases;
	private int bicicletas;
	
	public LineaMontaje(int fases) {
		this.turno = 0;
		this.fases = fases;
		this.bicicletas= 0;
	}
	
	public synchronized void esperarTurno(int miFase) throws InterruptedException {
		while(turno != miFase) {
			System.out.println(Thread.currentThread().getName() + " espera: es el turno de la fase " + turno);
			wait();
		}	
	}
	
	public synchronized void pasarTurno() {
		if(turno != fases - 1) {
			turno++;			
		}else {
			turno = 0;
			bicicletas++;
			System.out.println("=== Bicicleta número " + bicicletas + " terminada ===");
		}
		notifyAll();
	}
	
	public synchronized int getBicicletas() {
		return this.bicicletas;
	}

}
