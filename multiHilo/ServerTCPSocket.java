package multiHilo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerTCPSocket {
	private ServerSocket serverSocket;

	public ServerTCPSocket(int puerto) throws IOException {
		serverSocket = new ServerSocket(puerto);
		int numeroAl = new Random().nextInt(1000);
		System.out.println("Abriendo server");
		
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("(Servidor) Conexión establecida.");
			new GestorProcesos(socket, numeroAl).start();
		}
	}

	public void stop() throws IOException {
		serverSocket.close();
	}

	public static void main(String[] args) {
		try {
			ServerTCPSocket servidor = new ServerTCPSocket(49171);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}