package ejercicio1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	
	public SocketServer(int port) throws IOException {
		serverSocket=new ServerSocket(port);
	
	}
	
	
}
