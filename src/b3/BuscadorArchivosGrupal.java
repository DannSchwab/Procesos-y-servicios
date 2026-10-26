package b3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Pide una carpeta y una extensión, ejecuta cmd.exe /c dir /b <carpeta>
 * y muestra numerados solo los archivos con esa extensión.
 *
 * @author Daniel Schwab
 * @author Adrian Mier
 */
public class BuscadorArchivosGrupal {

	// Autor: Adrian Mier
	static Scanner sc = new Scanner(System.in);

	/**
	 * Main que ejecuta el método buscarArchivos y cierra el scanner
	 * @param args
	 */
	// Autor: Adrian Mier
	public static void main(String[] args) {
		buscarArchivos();
		sc.close();
	}

	/**
	 * Este método maneja casi el 100% del programa: recoge
	 * la carpeta y la extensión de los archivos a buscar
	 */
	static void buscarArchivos() {

		// ===== Autor: Adrian Mier — pedir y validar los datos =====
		String ruta;
		String extension;

		System.out.print("Carpeta: ");
		ruta = sc.nextLine().trim();
		System.out.print("Extensión (sin punto): ");
		extension = sc.nextLine().trim();

		// En caso de que la extensión no se haya escrito se detiene por completo
		if (extension.isEmpty()) {
			System.out.println("La extensión es obligatoria");
			// sería mejor hacerlo con una comprobación booleana, pero no tengo tiempo
			return;
		}
		// ===== Fin bloque Adrian Mier =====

		// ===== Autor: Daniel Schwab — preparar, lanzar, leer y comprobar =====
		ProcessBuilder pB = new ProcessBuilder("cmd.exe", "/c", "dir", "/b", ruta);

		int total = 0;

		/*
		 * Se inicia el process tras ajustar todo el process builder según los
		 * requisitos pedidos
		 */
		try {
			Process p = pB.start();

			/*
			 * try with resources cierra automáticamente los
			 * recursos abiertos al acabar el bloque try, facilita
			 * y evita poner el bloque finally para asegurarse de que
			 * no queden comunicaciones abiertas, en este caso los dos
			 * BufferedReader (salida y errores). Los fallos se recogen
			 * con los bloques catch del try padre.
			 */
			try (BufferedReader salida = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
				 BufferedReader errores = new BufferedReader(
					new InputStreamReader(p.getErrorStream()))) {

				String linea;
				while ((linea = salida.readLine()) != null) {
					// este if comprueba la extensión del archivo, asegurando la igualdad
					// ajustando el texto con .toLowerCase() y .endsWith()
					if (linea.toLowerCase().endsWith("." + extension.toLowerCase())) {
						total++;
						System.out.println(total + ". " + linea);
					}
				}

				// mientras la línea obtenida no sea null esta se imprime por consola
				while ((linea = errores.readLine()) != null) {
					System.out.println("ERROR: " + linea);
				}
			}

			// recoge en un int un valor de cero si el proceso termina normalmente
			int codigo = p.waitFor();

			if (total == 0 && codigo == 0) {
				System.out.println("No hay archivos ." + extension);
			}
			System.out.println("Total: " + total + " | Código de salida: " + codigo);
		// ===== Fin bloque Daniel Schwab =====

		/*
		 * Bloques catch que recogen los fallos de todos los bloques try, incluso
		 * el try with resources que está dentro del primer try
		 */
		// Autor: Adrian Mier
		} catch (IOException e) {
			System.out.println("No se pudo lanzar o leer el proceso: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Espera interrumpida");
		}
	}
}