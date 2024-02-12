package repaso;

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

	public static void main(String[] args) {
		ClienteTCPSocket client = new ClienteTCPSocket("localhost", 49171);
		Scanner scanner = new Scanner(System.in);
		String respuesta="";
		String mensaje;
		try {
			client.start();
			client.abrirCanalesDeTexto();
			
			while(!respuesta.equals("#0#")) {
				System.out.println("Dime el mensaje que quieras");
				mensaje=scanner.nextLine();
				client.pw.println(mensaje);
				respuesta=client.br.readLine();
				if(respuesta.equals("#99#")) {
					System.out.println("Mensaje no adecuadamente formateado para su tratamiento");
				}else if(respuesta.equals("#0#")) {
					System.out.println("Fin de la conexion");
					client.cerrarCanalesDeTexto();
					client.stop();
				}else if(respuesta.startsWith("#1#")) {
					System.out.printf("El mensaje enviado ha sido: %s \n", mensaje);
					System.out.printf("El mensaje recibido ha sido: %s \n", respuesta);
					System.out.printf("Texto a codificar a base 64: %s \n", mensaje);
					System.out.printf("Texto codificado en base 64: %s \n", respuesta);
				}else if(respuesta.startsWith("#2#")) {					
					System.out.printf("El mensaje enviado ha sido: %s \n",mensaje);
					System.out.printf("El mensaje recibido ha sido: %s \n",respuesta);
					System.out.printf("Texto a decodificar de 64: %s \n",respuesta);
					System.out.printf("Texto decodificado de base 64: %s \n",mensaje);
				}
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			scanner.close();
		}
	}
}
