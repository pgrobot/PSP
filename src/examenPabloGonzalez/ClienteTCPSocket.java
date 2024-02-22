package examenPabloGonzalez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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

	public static void main(String[] args) throws IOException {
		ClienteTCPSocket client = new ClienteTCPSocket("localhost", 49171);
		Scanner scanner = new Scanner(System.in);
		String respuesta = "";
		String mensaje;
		try {
			client.start();
			client.abrirCanalesDeTexto();

			while (true) {
				System.out.println("Dime el mensaje que quieras");
				mensaje = scanner.nextLine();
				client.pw.println(mensaje);
				respuesta = client.br.readLine();

				if (respuesta.equalsIgnoreCase("#Error#")) {
					System.out.println("Mensaje enviado: " + mensaje);
					System.out.println("Mensaje recibido: " + respuesta);
					System.out.println("Mensaje no entendido");
				} else if (respuesta.equalsIgnoreCase("#Finalizado#")) {
					System.out.println("Mensaje enviado: " + mensaje);
					System.out.println("Mensaje recibido: " + respuesta);
					System.out.println("Fin de la conexion");
					break;
				} else {

					System.out.println("Mensaje enviado: " + mensaje);
					System.out.println("Mensaje recibido: " + respuesta);

				}

			}

			client.stop();
			client.cerrarCanalesDeTexto();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			scanner.close();
		}
	}
}
