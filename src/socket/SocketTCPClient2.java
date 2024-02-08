package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTCPClient2 {
	private String serverIP;
	private int serverPort;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	// añadiendo las elementos necesarios para transferir cadenas de caracteres	
	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;	
	
	
	public SocketTCPClient2(String serverIP, int serverPort) {
		super();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	
	public void start() throws UnknownHostException, IOException {
		System.out.println("(Cliente) Estableciendo conexión ...");
		socket = new Socket(serverIP, serverPort);
		os = socket.getOutputStream();
		is = socket.getInputStream();	
		System.out.println("(Cliente) Conexión establecida.");
	}
	
	public void stop() throws IOException {
		System.out.println("(Cliente) Estableciendo conexión ...");
		is.close();
		os.close();
		socket.close();
		System.out.println("(Cliente) Conexiones cerradas.");
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

	public String leerMensajeTexto() throws IOException {
		System.out.println("(Cliente) Leyendo mensaje ...");
		String mensaje = br.readLine();
		System.out.println("(Cliente) Mensaje leído.");
		return mensaje;
	}

	public void enviarMensajeTexto(String mensaje) {
		System.out.println("(Cliente) Enviando mensaje ...");
		pw.println(mensaje);
		System.out.println("(Cliente) Mensaje enviado.");
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		SocketTCPClient2 cliente = new SocketTCPClient2("localhost", 8080);
		
		try {
			cliente.start();
			cliente.abrirCanalesDeTexto();
			// Envío de mensaje al servidor
			cliente.enviarMensajeTexto("Mensaje enviado desde el cliente");
			// Recepción de mensaje del servidor
			String mensajeRecibido = cliente.leerMensajeTexto();
			System.out.println("(Cliente) Mensaaje recibido: " + mensajeRecibido);
			cliente.cerrarCanalesDeTexto();
			cliente.stop();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}