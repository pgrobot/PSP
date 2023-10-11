package ejemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Activad2Servicio {

	
	public void Salida() {
		
		try {
			Process p= new ProcessBuilder("CMD", "/C", "DIR").start();
			InputStream put= p.getInputStream();
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(put));
			String linea;
			while((linea=reader.readLine())!=null) {
			System.out.println(linea); 
			}
			
			
			put.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
}
