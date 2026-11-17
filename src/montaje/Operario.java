package montaje;

public class Operario implements Runnable {

	private LineaMontaje linea;
	private int miFase;
	private String trabajo;
	private int cantidad;
	private int ms;

	public Operario(LineaMontaje linea, int miFase, String trabajo, int cantidad, int ms) {
		super();
		this.linea = linea;
		this.miFase = miFase;
		this.trabajo = trabajo;
		this.cantidad = cantidad;
		this.ms = ms;
	}

	@Override
	public void run() {
		String nombre = Thread.currentThread().getName();
		try {
			for (int i = 1; i <= cantidad; i++) {
				linea.esperarTurno(miFase);
				System.out.println(nombre + " " + trabajo +  " la bicicleta " + i);
				Thread.sleep(ms);
				linea.pasarTurno();
			}
		System.out.println(nombre + "termina su jornada");
		} catch (InterruptedException e) {
			System.out.println(nombre + "ha sido interrumpido");
		}
	}

}
