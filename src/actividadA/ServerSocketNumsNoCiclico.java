package actividadA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketNumsNoCiclico {

	private InputStream is;
	private OutputStream os;
	private ServerSocket serverSocket;
	private Socket socket;

	public ServerSocketNumsNoCiclico(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void start() throws IOException {
		System.out.println("Abriendo conexion....");
		socket = serverSocket.accept();
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println("Conexion abierta.....");
	}

	public void stop() throws IOException {
		System.out.println("Cerrando conexion.....");
		is.close();
		os.close();
		socket.close();
		serverSocket.close();
		System.out.println("Conexion cerrada.....");

	}

	public static void main(String[] args) {
		try {
			ServerSocketNumsNoCiclico server = new ServerSocketNumsNoCiclico(8080);
			server.start();
			while (true) {
				
				int datoLeido = server.is.read();
				if(datoLeido==-1) {
					break;
				}
				System.out.printf("(Servido) Leido: %d%n", datoLeido);
				int datoAmandar = datoLeido + 1;
				System.out.printf("(Servidor) Mandando: %d%n", datoAmandar);
				server.os.write(datoAmandar);
				
			}

			server.stop();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
