package pasaPlatos_RecursosCompartidos;

public class Cocinero implements Runnable {

	private Pasaplatos pase;
	private String[] platos;

	public Cocinero(Pasaplatos pase, String[] platos) {
		this.platos = platos;
		this.pase = pase;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < platos.length; i++) {
				Thread.sleep(300);
				pase.dejar(platos[i]);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
