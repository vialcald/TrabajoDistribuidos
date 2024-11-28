package gestorPartidas;

import java.io.*;
import java.net.*;

public class Servidor {
	public static void main(String[] args) {
		try(ServerSocket server = new ServerSocket(80000);){
			while(true) {
				try(Socket cliente = server.accept();
						OutputStream o = cliente.getOutputStream();
						OutputStreamWriter ow = new OutputStreamWriter(o, "UTF-8");
						InputStream i = cliente.getInputStream();
						InputStreamReader ir = new InputStreamReader(i, "UTF-8");
						BufferedReader br = new BufferedReader(ir);){
					
					
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
