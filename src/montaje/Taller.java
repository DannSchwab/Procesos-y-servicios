package montaje;

public class Taller {

	public static final int BICICLETAS = 5;
	public static final int FASES = 3;

	public static void main(String[] args) {
		LineaMontaje linea = new LineaMontaje(FASES);

		Thread soldador = new Thread(
				new Operario(linea, 0, "suelda el cuadro de", BICICLETAS, 300), "Soldador");
		Thread montador = new Thread(
				new Operario(linea, 1, "monta las ruedas de", BICICLETAS, 200), "Montador");
		Thread pintor = new Thread(
				new Operario(linea, 2, "pinta", BICICLETAS, 400), "Pintor");

		long inicio = System.currentTimeMillis();

		// Se lanzan en orden inverso: el monitor se encarga de respetar el orden de fases
		pintor.start();
		montador.start();
		soldador.start();

		try {
			pintor.join();
			montador.join();
			soldador.join();
		} catch (InterruptedException e) {
			System.out.println("El main ha sido interrumpido");
		}

		long tiempo = System.currentTimeMillis() - inicio;

		System.out.println("=== Fin de la jornada ===");
		System.out.println("Bicicletas terminadas: " + linea.getBicicletas());
		System.out.println("Tiempo: " + tiempo + " ms");
	}

}