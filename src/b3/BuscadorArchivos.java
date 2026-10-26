package b3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BuscadorArchivos {

	static Scanner sc = new Scanner(System.in);

	
	/**
	 * Main que ejecuta el metodo buscar archivos y cierra el scanner
	 * @param args
	 */
	public static void main(String[] args) {
		buscarArchivos();
		sc.close();
	}

	
	/**
	 * Se maneja con esta clase casi el 100% del programa, recoge 
	 * la carpeta y la extension del archivo a ejecutar
	 */
	static void buscarArchivos() {
		
		String ruta;
		String extension;
		
		System.out.print("Carpeta: ");
		ruta = sc.nextLine().trim();
		System.out.print("Extension (Sin punto): ");
		extension = sc.nextLine().trim();
		
		
		// En caso de que la extensión no se haya escrito se detiene por completo
		if (extension.isEmpty()) {
			System.out.println("La extensión es obligatoria");
			// sería mejor hacerlo con una comprobante booleana, pero no tengo tiempo
			return;
		}

		ProcessBuilder pB = new ProcessBuilder("cmd.exe", "/c", "dir", "/b" , ruta);
		
		int total = 0;
		
		
		/*
		 * Se inicia el process tras ajustar todo el process builder segun los 
		 * requisitos pedidos
		 */
		try {
			Process p = pB.start();
			
			
			/* 
			 * try with resources cierra automáticamente el 
			 * recurso abierto al acabar el bloque try, facilita 
			 * y evita poner el bloque finally para asegurarse de que 
			 * no queden comunicaciones abiertas, en este caso el 
			 * bufferedReader
			 */
			
			try (BufferedReader salida = new BufferedReader(
			        new InputStreamReader(p.getInputStream()))) {

			    String linea;
			    while ((linea = salida.readLine()) != null) {
			    	// este if comprueba la extension del archivo, asegurando la igualdad
			    	// ajustando el texto con .toLowerCase() y .endWith
			        if (linea.toLowerCase().endsWith("." + extension.toLowerCase())) {
			            total++;
			            System.out.println(total + ". " + linea);
			        }
			    }
			}	
			
			/*
			 * Similar al bloque previo, el try with resources asegura
			 * el cierre de la conexción al acabar el bloque try
			 * asegurando que se recojan los errores con los bloques catch
			 * del try padre en el que está este bloque try with resources
			 */
			try (BufferedReader errores = new BufferedReader(
			        new InputStreamReader(p.getErrorStream()))) {

			    String linea;
			    // mientras la linea obtenida no sea null esta se imprime por consola
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

			
		/*
		 * Bloques catch que recogen los fallos de todos los bloques try, incluso 
		 * los try with resource que están dentro del primer try
		 */
			
		} catch (IOException e) {
			System.out.println("No se pudo lanzar o leer el proceso: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Espera interrumpida");
		}
	}
}