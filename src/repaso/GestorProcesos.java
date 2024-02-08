package repaso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

public class GestorProcesos extends Thread {
	private Socket socket;
	private OutputStream os;
	private InputStream is;

	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;

	public GestorProcesos(Socket socket) {
		this.socket = socket;

	}

	public void realizarProceso() throws IOException {
		os = socket.getOutputStream();
		is = socket.getInputStream();

		abrirCanalesDeTexto();

		try {
			while(true) {
				String dato =br.readLine(); 
				if(dato==null) {
				break;
				}
				if (dato.startsWith("#1#")) {
					String[] mensaje = dato.split("#");
					String encodedString = Base64.getEncoder().encodeToString(mensaje[1].getBytes());
					pw.println("#1#"+encodedString);
				} else if (dato.startsWith("#2#")) {
					String[] mensaje = dato.split("#");
					byte[] decodedBytes = Base64.getDecoder().decode(mensaje[1]);
					String decodedString = new String(decodedBytes);
					pw.println("#2#"+decodedString);
				}else if(dato.equals("#0#")) {
					pw.println("#0#");
				}else{
					pw.println("#99#");
				}
						
			}
		

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			cerrarCanalesDeTexto();
			os.close();
			is.close();
		}
	}

	public void abrirCanalesDeTexto() {
		System.out.println("(Servidor) Abriendo canales de texto ...");
		// lectura
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		// escritura
		pw = new PrintWriter(os, true); // flush automático
		System.out.println("(Servidor) Canales de texto abiertos.");

	}

	public void cerrarCanalesDeTexto() throws IOException {
		// canal de lectura
		br.close();
		isr.close();
		// canal de escritura
		pw.close();

	}

	@Override
	public void run() {
		try {
			realizarProceso();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
