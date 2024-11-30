package gestorPartidas;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		try(ServerSocket s = new ServerSocket(8000)){
			while(true) {
				try {
					Socket c1 = s.accept();
					Socket c2 = s.accept();
					
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
