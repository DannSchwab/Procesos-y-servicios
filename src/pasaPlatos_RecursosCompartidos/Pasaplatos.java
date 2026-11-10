package pasaPlatos_RecursosCompartidos;

public class Pasaplatos {

	private String plato = null;
	
	public synchronized void dejar(String nuevo) throws InterruptedException {
		while(plato!= null) {
			System.out.println("Pasaplatos lleno");
			wait(); // Cocinero 
		}
		plato = nuevo;
		System.out.println("Se deja plato nuevo");
		notify(); // Camarero
	}
	
	public synchronized String recoger() throws InterruptedException {
		
		while(plato == null) {
			System.out.println("Pasaplatos vacío, espera");
			wait(); // Camarero
		}
		String recogido = plato;
		plato = null;
		notify(); // Cocinero
		return  recogido;
	}
}
