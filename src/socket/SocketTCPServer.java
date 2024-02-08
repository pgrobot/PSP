package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCPServer {
	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	public SocketTCPServer(int port) throws IOException {
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
	
	public static void main(String[] args) {
		try {
			SocketTCPServer servidor = new SocketTCPServer(8080);
			servidor.start();
			System.out.println("Mensaje del cliente: " + servidor.is.read());
			servidor.os.write(101);
			servidor.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}