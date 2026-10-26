package ejercicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

public class P_S_DanielSchwab_EjProcessBuilder {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		validarDatos();
		sc.close();
	}

	/** Muestra el contenido del archivo de errores que escribió el proceso hijo. */
	public static void mostrarErrores(File archivoErrores) {
		try (BufferedReader br = new BufferedReader(new FileReader(archivoErrores))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				System.out.println("Aviso del proceso: " + linea);
			}
		} catch (IOException e) {
			System.out.println("No se pudo leer " + archivoErrores.getName() + ": " + e.getMessage());
		}
	}

	public static void validarDatos() {
		// PASO 1. Pedir y validar los datos ANTES de crear el proceso
		System.out.print("Carpeta: ");
		String ruta = sc.nextLine().trim();
		System.out.print("Extensión (sin punto): ");
		String ext = sc.nextLine().trim();

		File carpeta = new File(ruta);
		if (!carpeta.isDirectory()) {
			System.out.println("La carpeta no existe");
			return;
		}
		if (ext.isEmpty()) {
			System.out.println("La extensión es obligatoria");
			return;
		}

		// PASO 2. Configurar el ProcessBuilder: comando, carpeta, entorno, redirecciones
		File archivoErrores = new File("errores_inspector.txt");
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "dir", "*." + ext);
		pb.directory(carpeta);
		Map<String, String> entorno = pb.environment();
		entorno.put("DIRCMD", "/b");
		pb.redirectError(archivoErrores);

		try {
			// PASO 3. Lanzar el proceso
			Process proceso = pb.start();

			// PASO 4. Leer la salida del proceso ANTES de esperar
			int total = 0;
			try (BufferedReader salida = new BufferedReader(
					new InputStreamReader(proceso.getInputStream()))) {
				String linea;
				while ((linea = salida.readLine()) != null) {
					total++;
					System.out.println(total + ". " + linea);
				}
			}

			// PASO 5. Esperar a que termine y recoger el código de salida
			int codigo = proceso.waitFor();

			// PASO 6. Decidir según el código de salida
			if (codigo == 0) {
				System.out.println("Total: " + total + " archivos ." + ext);
			} else {
				mostrarErrores(archivoErrores);
				System.out.println("No hay archivos ." + ext + " en "
						+ carpeta.getAbsolutePath() + " (código " + codigo + ")");
			}

		} catch (IOException e) {
			System.out.println("No se pudo lanzar o leer el proceso: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Espera interrumpida: " + e.getMessage());
		}
	}

}