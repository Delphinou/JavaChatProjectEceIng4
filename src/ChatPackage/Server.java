/*
 * ChatProject ING4-APP-SI 2020
 * LAM Daphne
 * BUI Mai Anh
 * 
 */

package ChatPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	// MAIN
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		// Variables
		ServerSocket connex;

		// Create the server, choose the port
		connex = new ServerSocket(1000);

		// Wait for the clients 
		while(true) {

			// Open the socket
			Socket s = connex.accept();
			System.out.println("Connexion ok");

			// Launch a thread
			Thread t = new Thread(new HandleClient(s));
			t.start();
		}
	}
}
