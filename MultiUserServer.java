package projet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiUserServer {
	private static final int PORT = 12345;
    private ServerSocket serverSocket ;
	  public MultiUserServer(ServerSocket serverSocket) {
    	this.serverSocket = serverSocket;
	}	
    
    public void startServer() {
    	try { 
    		System.out.println("Server is  Connecte on port 12345");	
    	
    	while (!serverSocket.isClosed()) {
		Socket socket = serverSocket.accept();    		
		System.out.println("A New Client Connecte!");	
		ClientHandler clientHandler = new ClientHandler(socket);
    	Thread thread = new Thread(clientHandler);
    	thread.start();
    	}
    	}
    	catch (IOException e) {
    		closeServerSocket();
    		e.printStackTrace();
			}		
	}
    
    public void closeServerSocket() {
		try {
			if(serverSocket != null) {
			
				serverSocket.close();
			}
		} catch (Exception e) {
		e.printStackTrace();
		}
    	
	}
    public static void main(String[] args) {
    	try {
		ServerSocket serverSocket = new ServerSocket(PORT);
		MultiUserServer server = new MultiUserServer(serverSocket);
        server.startServer();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}
