package gestorPartidas;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		try(ServerSocket s = new ServerSocket(8000)){
			while(true) {
				try (Socket c1 = s.accept();
						Socket c2 = s.accept();
						
						InputStream is1 = c1.getInputStream();
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
					
					String jugador1 = br1.readLine();
					String jugador2 = br2.readLine();
					osw1.write(jugador2);
					osw2.write(jugador1);
					osw1.flush();
					osw2.flush();
					
					
					
					pool.execute(new Partida(c1,c2));
				}catch(IOException e) {
					e.printStackTrace();
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
