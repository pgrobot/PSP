package examenPabloGonzalez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

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
			while (true) {
				String dato = br.readLine();
				if (dato == null) {
					break;
				}
				System.out.println("Peticion recibida");
				if (dato.startsWith("#Listado números#")) {
					String[] mensaje = dato.split("#");
					String respuesta = "";
					for (int i = Integer.parseInt(mensaje[2]); i <= Integer.parseInt(mensaje[3]); i++) {
						respuesta = respuesta + i + "|";
					}
					respuesta = respuesta.substring(0, respuesta.length() - 1);
					System.out.println("Mensaje recibido: " + dato);
					System.out.println("Mensaje enviado: " + respuesta);
					pw.println(respuesta);
				} else if (dato.startsWith("#Número aleatorio#")) {
					String[] mensaje = dato.split("#");
					String numeroAleatorio = String
							.valueOf(new Random().nextInt(Integer.parseInt(mensaje[3])) + Integer.parseInt(mensaje[2]));

					System.out.println("Mensaje recibido: " + dato);
					System.out.println("Mensaje enviado: " + numeroAleatorio);

					pw.println(numeroAleatorio);
				} else if (dato.equalsIgnoreCase("#Fin#")) {
					System.out.println("Mensaje recibido: " + dato);
					System.out.println("Mensaje enviado: #Finalizado#");
					pw.println("#Finalizado#");
				} else {
					System.out.println("Mensaje recibido: " + dato);
					System.out.println("Mensaje enviado: #Error#");
					pw.println("#Error#");
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
