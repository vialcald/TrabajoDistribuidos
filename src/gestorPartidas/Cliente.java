package gestorPartidas;

import java.io.*;
import java.net.*;

public class Cliente {
	public static void main(String[] args) {
		try (Socket s = new Socket("localhost", 80000);
				OutputStream o = s.getOutputStream();
				OutputStreamWriter ow = new OutputStreamWriter(o, "UTF-8");
				InputStream i = s.getInputStream();
				InputStreamReader ir = new InputStreamReader(i, "UTF-8");
				BufferedReader br = new BufferedReader(ir);){
			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
