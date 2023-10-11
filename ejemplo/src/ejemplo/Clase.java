package ejemplo;

import java.io.IOException;

public class Clase {

	public void ejecutarProceso(String ruta) {
		
		ProcessBuilder pB= new ProcessBuilder(ruta);
		
		try {
			pB.start();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	
}
}
