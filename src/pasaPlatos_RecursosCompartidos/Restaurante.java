package pasaPlatos_RecursosCompartidos;

public class Restaurante {

	
	public static void main(String[]args) {
		
		Pasaplatos miPasaplatos = new Pasaplatos();
		String[] platos = {"Paella", "Gazpacho", "Tortilla", "Torrijas", "Croquetas"};
		
		Thread cocinero = new Thread(new Cocinero(miPasaplatos, platos), "manolo");
		Thread camarero = new Thread(new Camarero(miPasaplatos, platos.length), "manolin");
		
		cocinero.start();
		camarero.start();
		
		try {
			cocinero.join();
			camarero.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Servicio terminado");
	}
}
