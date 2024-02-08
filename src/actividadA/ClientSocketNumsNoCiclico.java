package actividadA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketNumsNoCiclico {

	private InputStream is;
	private OutputStream os;
	private Socket socket;
	private String hostIP;
	private int hostPort;

	public ClientSocketNumsNoCiclico(int hostPort, String hostIP) throws UnknownHostException, IOException {

		this.hostIP = hostIP;
		this.hostPort = hostPort;

	}

	public void start() throws UnknownHostException, IOException {
		System.out.println("(Cliente) Estableciendo conexión ...");
		socket = new Socket(hostIP, hostPort);
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println("(Cliente) Conexion extablecida");

	}

	public void close() throws IOException {
		System.out.println("(Cliente) Cerrando conexion...");
		is.close();
		os.close();
		socket.close();
		System.out.println("(Cliente) Conexion cerrada....");

	}

	public static void main(String[] args) {
		ClientSocketNumsNoCiclico cliente;
		try {
			cliente = new ClientSocketNumsNoCiclico(8080, "localhost");
			cliente.start();
			for (int i = 0; i < 10; i++) {
				
				System.out.printf("(Cliente %s: %d) Enviado: %d%n", cliente.hostIP, cliente.hostPort, i);
				cliente.os.write(i);
				System.out.printf("(Cliente %s: %d) Recibido: %d%n", cliente.hostIP, cliente.hostPort, cliente.is.read());
			}
			
			cliente.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
