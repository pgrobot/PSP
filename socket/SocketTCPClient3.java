package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTCPClient3 {
	private String serverIP;
	private int serverPort;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	// añadiendo las elementos necesarios para transferir cadenas de caracteres	
	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;	
	
	
	public SocketTCPClient3(String serverIP, int serverPort) {
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
		SocketTCPClient3 cliente = new SocketTCPClient3("localhost", 8080);
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			cliente.start();
			cliente.abrirCanalesDeTexto();

			
			// Envío de mensaje al servidor y espera de respuesta
            while (true) {
                System.out.println("(Cliente) Escriba un mensaje para enviar al servidor (o 'FIN' para terminar): ");
                String mensajeParaEnviar = consoleReader.readLine();

                if (mensajeParaEnviar.equalsIgnoreCase("FIN")) {
                    cliente.enviarMensajeTexto(mensajeParaEnviar);
                    break;
                }

                cliente.enviarMensajeTexto(mensajeParaEnviar);
                String mensajeRecibido = cliente.leerMensajeTexto();
                System.out.println("(Cliente) Mensaje recibido: " + mensajeRecibido);
            }

			
			
			
			cliente.cerrarCanalesDeTexto();
			cliente.stop();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
                consoleReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

}