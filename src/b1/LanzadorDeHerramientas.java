package b1;

import java.io.File;

import java.io.IOException;
import java.util.Scanner;

public class LanzadorDeHerramientas {

	/*
	 * Hemos cambiado la forma en que muestra por consola
	 * el comando ejecutado a la forma en la que lo hacía JiaXi
	 * Por otro lado, hemos agregado mi forma de controlar una opción
	 * no válida por la mía con una variable booleana en vez de un return;
	 */
	
	/**
	 * @author Daniel Schwab
	 * @author JiaXi
	 */
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ProcessBuilder pb = new ProcessBuilder();
		boolean opcionValida = true;

		System.out.println("1. Bloc de notas");
		System.out.println("2. Calculadora");
		System.out.println("3. Listar C:\\Windows");
		System.out.println("4. Programa inexistente");
		System.out.print("Elija una opción: ");
		int opcion = sc.nextInt();
		String comando = "";
		sc.close();

		switch (opcion) {
	    
		case 1:
             comando = "notepad.exe";
             pb.command("notepad.exe");
             break;
         case 2:
             comando = "calc.exe";
             pb.command("calc.exe");
             break;
         case 3:
             comando = "cmd.exe";
             pb.command("cmd.exe", "/c", "dir", "C:\\Windows");
             pb.directory(new File("C:\\Windows"));
             pb.inheritIO(); // inheritIO significa que va por consola 
             break;
         case 4:
             comando = "programaquenoexiste.exe";
             pb.command("programaquenoexiste.exe");
             break;
		
		default:
			System.out.println("Opción no válida");
			opcionValida = false;
			break;
		}

		if (opcionValida) {			

			try {
				Process p = pb.start();
				System.out.println("Lanzado: [" + comando + "]");
				p.waitFor();
			} catch (IOException e) {
				System.out.println("No se ha podido lanzar [" + comando + "]" + e.getMessage());
				
			} catch (InterruptedException e) {
				System.out.println("Proceso interrumpido.");
				Thread.currentThread().interrupt();
			}

		}


	}

}
