package gestorPartidas;

import java.io.*;
import java.net.*;
import java.util.*;

import conectaCuatro.Tablero;

public class Cliente {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("---- CONECTA CUATRO ONLINE ----");
		System.out.println("Pulsa cualquier tecla para jugar");
		System.out.println("Pulsa 0 para salir");
		
		boolean salir;
		try {
			salir = Integer.parseInt(teclado.nextLine()) == 0;
		} catch (NumberFormatException e) {
			salir = false;
		}
		
		if(!salir) {
			System.out.print("Nombre de usuario: ");
			String nombre = teclado.nextLine();
			
			try (Socket s = new Socket("localhost", 8000);
					OutputStream os = s.getOutputStream();
					DataOutputStream dos = new DataOutputStream(os);
					OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
					InputStream is = s.getInputStream();
					DataInputStream dis = new DataInputStream(is);
					InputStreamReader isr = new InputStreamReader(is, "UTF-8");
					BufferedReader br = new BufferedReader(isr);){
				
				osw.write(nombre);
				String oponente = br.readLine();
				System.out.println("Oponente asignado: " + oponente);
				
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		teclado.close();
	}
}
