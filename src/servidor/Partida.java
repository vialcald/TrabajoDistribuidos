package servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import modelo.Tablero;

public class Partida extends Thread {
	private Tablero tablero = new Tablero();
	private Socket c1;
	private Socket c2;

	public Partida(Socket cliente1, Socket cliente2) {
		this.c1 = cliente1;
		this.c2 = cliente2;
	}

	public void run() {
		try(InputStream is1 = c1.getInputStream();
				DataInputStream dis1 = new DataInputStream(is1);
				InputStreamReader isr1 = new InputStreamReader(dis1, "UTF-8");
				BufferedReader br1 = new BufferedReader(isr1);
				OutputStream os1 = c1.getOutputStream();
				DataOutputStream dos1 = new DataOutputStream(os1);
				OutputStreamWriter osw1 = new OutputStreamWriter(dos1, "UTF-8");

				InputStream is2 = c2.getInputStream();
				DataInputStream dis2 = new DataInputStream(is2);
				InputStreamReader isr2 = new InputStreamReader(dis2, "UTF-8");
				BufferedReader br2 = new BufferedReader(isr2);
				OutputStream os2 = c2.getOutputStream();
				DataOutputStream dos2 = new DataOutputStream(os2);
				OutputStreamWriter osw2 = new OutputStreamWriter(dos2, "UTF-8");){

			String[] jugadores =  new String[2];
			OutputStreamWriter[] osw = {osw1, osw2};
			BufferedReader[] br = {br1, br2};

			for(int i = 0; i < 2; i++) { // Leer nombres
				jugadores[i] = br[i].readLine();
			}

			for(int i = 0; i < 2; i++) { // Enviar nombres
				osw[i].write(jugadores[(i + 1) % 2] + "\r\n");
			}

			// Sistema de rondas
			while(!tablero.juegoFinalizado()) {
				int jugador = tablero.getJugador();
				int espectador = tablero.getEspectador();

				// Comunica turnos
				osw[jugador].write("JUEGA" + "\r\n");
				osw[espectador].write("MIRA" + "\r\n");
				for(int i = 0; i < 2; i++) {
					osw[i].flush();
				}

				// Turno jugador
				int columna = br[jugador].read();
				tablero.ponerFicha(columna);

				// Turno  espectador
				osw[espectador].write(columna);
				osw[espectador].flush();
			}

			// Fin de partida
			for(int i = 0; i < 2; i++) {
				osw[i].write("FIN" + "\r\n");
			}

			int ganador = tablero.getGanador();
			if(ganador != -1) {
				int perdedor = tablero.getPerdedor();
				osw[ganador].write("GANAS" + "\r\n");
				osw[perdedor].write("PIERDES" + "\r\n");
			} else {
				for(int i = 0; i < 2; i++) {
					osw[i].write("EMPATE" + "\r\n");
				}
			}

			for(int i = 0; i < 2; i++) {
				osw[i].flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				c1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				c2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
