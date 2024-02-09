package repaso;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class ServerTCPSocket {
	private ServerSocket serverSocket;

	public ServerTCPSocket(int puerto) throws IOException {
		serverSocket = new ServerSocket(puerto);
		System.out.println("Abriendo server");
		
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("(Servidor) Conexión establecida.");
			new GestorProcesos(socket).start();
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
