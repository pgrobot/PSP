package multiHilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class ClienteTCPSocket {
	InputStream is;
	Socket socket;
	String serverIP;
	int serverPort;
	private OutputStream os;

	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;

	public ClienteTCPSocket(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public void start() throws UnknownHostException, IOException {
		System.out.printf("[Cliente] Estableciendo conexión socket ...%n");
		socket = new Socket(serverIP, serverPort);
		System.out.printf("[Cliente %s:%d] Conexión socket establecida ...%n", serverIP, serverPort);
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}

	public void stop() throws IOException {
		System.out.printf("[Cliente %s:%d] Cerrando conexión socket ...%n", serverIP, serverPort);
		is.close();
		socket.close();
		os.close();
		System.out.printf("[Cliente %s:%d] Conexión socket cerrada.%n", serverIP, serverPort);
	}

	public void abrirCanalesDeTexto() {
		System.out.println("(Cliente) Abriendo canales de texto ...");
		// lectura
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		// escritura
		pw = new PrintWriter(os, true); // flush automático
		System.out.println("(Cliente) Canales de texto abiertos.");

	}

	public void cerrarCanalesDeTexto() throws IOException {
		System.out.println("(Cliente) Cerrando canales de texto ...");
		// canal de lectura
		br.close();
		isr.close();
		// canal de escritura
		pw.close();
		System.out.println("(Cliente) Canales de texto cerrados.");

	}

	public static void main(String[] args) {
		ClienteTCPSocket client = new ClienteTCPSocket("localhost", 49171);
		String respuesta;
		Integer contador=0;
		Integer veces=0;
		Integer totalContador=0;
		try {
			client.start();
			client.abrirCanalesDeTexto();
			while (veces!=10) {
				
				String numeroAleatorio=String.valueOf(new Random().nextInt(1000));
				client.pw.println(numeroAleatorio);				
				contador++;
				respuesta = client.br.readLine();
				if (respuesta.equalsIgnoreCase("Correcto")) {
					System.out.println(contador);
					totalContador+=contador;
					contador=0;
					veces++;
				}
			}
			System.out.println(totalContador);

			client.cerrarCanalesDeTexto();
			client.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}