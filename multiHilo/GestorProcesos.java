package multiHilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class GestorProcesos extends Thread {
	private Socket socket;
	private OutputStream os;
	private InputStream is;
	private int numeroAl;
	private static Integer totalContador=0;

	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;

	public GestorProcesos(Socket socket, int numeroAl) {
		this.socket = socket;
		this.numeroAl = numeroAl;
		

	}

	public void realizarProceso() throws IOException {
		os = socket.getOutputStream();
		is = socket.getInputStream();

		String numeroAleatorio = String.valueOf(numeroAl);
		abrirCanalesDeTexto();
		
		
		try {
			
				while (true) {
					if (numeroAleatorio.equalsIgnoreCase(br.readLine())) {
						totalContador++;
						pw.println("Correcto");
						System.out.println(totalContador);
						
					} else {
						totalContador++;
						pw.println("Mal");
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