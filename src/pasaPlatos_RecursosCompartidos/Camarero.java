package pasaPlatos_RecursosCompartidos;

public class Camarero implements Runnable {

	private Pasaplatos pase;
	private int cantidad_platos;

	public Camarero(Pasaplatos pase, int cantidad_platos) {
		this.cantidad_platos = cantidad_platos;
		this.pase = pase;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < cantidad_platos; i++) {
				Thread.sleep(300);
				String plato = pase.recoger();
				System.out.println(Thread.currentThread().getName() + " ha dejado el plato: " + plato);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
