package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCPServer3 {
	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private OutputStream os;

	// añadiendo las elementos necesarios para transferir cadenas de caracteres
	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;

	public SocketTCPServer3(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void start() throws IOException {
		System.out.println("Servidor esperando conexión ...");
		socket = serverSocket.accept();
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println("Conexión abierta.");

	}

	public void stop() throws IOException {
		System.out.println("Servidor cerrando conexión ...");
		is.close();
		os.close();
		socket.close();
		serverSocket.close();
		System.out.println("Conexión cerrada.");
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
		System.out.println("(Servidor) Cerrando canales de texto ...");
		// canal de lectura
		br.close();
		isr.close();
		// canal de escritura
		pw.close();
		System.out.println("(Servidor) Canales de texto cerrados.");

	}

	public String leerMensajeTexto() throws IOException {
		System.out.println("(Servidor) Leyendo mensaje ...");
		String mensaje = br.readLine();
		System.out.println("(Servidor) Mensaje leído.");
		return mensaje;
	}

	public void enviarMensajeTexto(String mensaje) {
		System.out.println("(Servidor) Enviando mensaje ...");
		pw.println(mensaje);
		System.out.println("(Servidor) Mensaje enviado.");
	}

	public static void main(String[] args) {
		try {
			SocketTCPServer3 servidor = new SocketTCPServer3(8080);
			servidor.start();
			servidor.abrirCanalesDeTexto();
			
            // Recepción del mensaje del cliente y respuesta
            while (true) {
                String mensajeRecibido = servidor.leerMensajeTexto();
                if (mensajeRecibido == null || mensajeRecibido.equals("FIN")) {
                    break;
                }
                System.out.println("(Servidor) Mensaje recibido: " + mensajeRecibido);
                servidor.enviarMensajeTexto("Listo");
            }

			servidor.cerrarCanalesDeTexto();
			servidor.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
