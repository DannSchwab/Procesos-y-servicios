package brigadaCocina;

public class Cocinero implements Runnable {

	private String plato;
	private int pasos;
	
	
	public Cocinero(String plato, int pasos) {
		
		this.pasos = pasos;
		this.plato = plato;
	}
	
	
	/**
	 * Contener tarea a realizar por el hilo
	 */
	@Override
	public void run() {
		
		String nombreCocinero = Thread.currentThread().getName();
		for(int i = 1; i<=pasos; i++) {
			System.out.println(nombreCocinero + ": paso " + i +"/" + pasos + " de " + plato);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // .sleep() deja el hilo en espera en función del tiempo que se le indique (ms) 1s = 1000
		}
	}

}
