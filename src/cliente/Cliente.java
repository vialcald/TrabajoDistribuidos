package cliente;

import java.io.*;
import java.net.*;
import java.util.*;

import conectaCuatro.Tablero;

public class Cliente {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("---- CONECTA CUATRO ONLINE ----");
		System.out.println("Pulsa [INTRO] para jugar.");
		System.out.println("Pulsa 0 para salir.");
		
		boolean salir;
		try {
			salir = Integer.parseInt(teclado.nextLine()) == 0;
		} catch (NumberFormatException e) {
			salir = false;
		}
		
		if(!salir) {
			try (Socket s = new Socket("localhost", 8000);
					OutputStream os = s.getOutputStream();
					DataOutputStream dos = new DataOutputStream(os);
					OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
					InputStream is = s.getInputStream();
					DataInputStream dis = new DataInputStream(is);
					InputStreamReader isr = new InputStreamReader(is, "UTF-8");
					BufferedReader br = new BufferedReader(isr);){
				Tablero tablero = new Tablero();
				
				// Intrcambio de nombres
				System.out.print("Nombre de usuario: ");
				String nombre = teclado.nextLine();
				osw.write(nombre + "\r\n");
				osw.flush();
				System.out.println("Esperando oponente...");
				String oponente = br.readLine();
				System.out.println("Oponente asignado: " + oponente);
				
				// Sistema de rondas
				while(!s.isClosed()) {
					int columna = -1;
					System.out.println("--------------------");
					String ronda = br.readLine();
					switch (ronda) {
					case "JUEGA":
						System.out.println("Es tu turno. Juegas con: " + tablero.getSimboloJugador());
						System.out.print(tablero.toString());
						
						boolean entradaValida = false;
						while(!entradaValida) {
							System.out.print("Elige columna: ");
							try {
								columna = Integer.parseInt(teclado.nextLine());
								if(!tablero.validarEntrada(columna)) {
									System.out.println("Entrada no válida.");
								} else {
									entradaValida = true;
								}
							} catch (NumberFormatException e) {
								System.out.println("Entrada no válida.");
							}
						}
						
						tablero.ponerFicha(columna);
						osw.write(columna);
						osw.flush();
						break;
						
					case "MIRA":
						System.out.println("Es el turno de " + oponente + ". Espera.");
						
						columna = br.read();
						tablero.ponerFicha(columna);
						break;
						
					case "FIN":
						System.out.println("La partida ha terminado.");
						System.out.print(tablero.toString());
						String resultado = br.readLine();
						switch (resultado) {
						case "GANAS":
							System.out.println("Enhorabuena, has ganado.");
						case "PIERDES":
							System.out.println("Tal vez tengas más suerte a la próxima.");
						case "EMPATE":
							System.out.println("Habéis empatado.");
						}
					}
				}
				
				System.out.println("Se ha cerrado la conexión. Adiós.");
				
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		teclado.close();
	}
}
