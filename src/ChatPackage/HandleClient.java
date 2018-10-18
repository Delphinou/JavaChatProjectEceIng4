/*
 * ChatProject ING4-APP-SI 2020
 * LAM Daphne
 * BUI Mai Anh
 * 
 */

package ChatPackage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class HandleClient implements Runnable{

	// ATTRIBUTES
	private Socket socket;
	private ArrayList<Topic> arrayTopic;

	// CONSTRUCTOR
	public HandleClient(Socket socket) throws IOException {
		this.socket = socket;
		arrayTopic = new ArrayList<Topic>();
		
		// Fill the arraylist from the file "topics.txt"
		DataInputStream disFile = new DataInputStream((new BufferedInputStream(new FileInputStream("topics.txt"))));
		Scanner scanner = new Scanner(disFile);

		// Read all the file's lines
		String tempTitle, tempLogin, tempMsg="", tempWord="";
		int tempNbMsg;
		ArrayList<Message> arrayMsg = new ArrayList<Message>();
		
		while(scanner.hasNextLine()) {
			tempTitle = scanner.next();
			
			tempNbMsg =  Integer.parseInt(scanner.next());
			
			for(int i = 0; i < tempNbMsg; i++ ) {
				
				tempLogin = scanner.next();
				
				tempMsg = scanner.next();
				
				do {
					tempWord = scanner.next();
					tempMsg += " " + tempWord;
					
				}while(!tempWord.equals("]"));
				
				arrayMsg.add(new Message(tempLogin, tempMsg));
			}
			
			arrayTopic.add( new Topic(tempTitle, arrayMsg));
		}

		// Close the file's stream
		disFile.close();
		
	}

	// RUN METHOD
	public void run() {

		try {
			// Open the output/input streams
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			String temp = dis.readUTF();
			System.out.println(temp);
			
			switch(temp) {
			case "log in" : logIn(dos, dis, oos, ois);
							break;
			case "submit" : submit(dos, dis, oos, ois);
							break;
			case "list" :   list(dos, dis, oos, ois);
						    break;
			case "create" : create(dos, dis, oos, ois);
							break;
			case "join" :   join(dos, dis, oos, ois);
						    break;
			}
			
			socket.close();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	// METHOD LOG IN
	public void logIn(DataOutputStream dos, DataInputStream dis, ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException {
		
		// Variables
		Account account;
		ArrayList<String> arrayLogin = new ArrayList<String>();
		ArrayList<String> arrayPwd = new ArrayList<String>();
		String login, pwd;
		boolean compliant = false;
		
		// Get the object Account
		account = (Account)ois.readObject();
		
		// Stream : file "logins_passwords.txt" 
		DataInputStream disFile = new DataInputStream((new BufferedInputStream(new FileInputStream("logins_passwords.txt"))));
		Scanner scanner = new Scanner(disFile);

		// Read all the file's lines
		while(scanner.hasNextLine()) {
			arrayLogin.add(scanner.next());
			arrayPwd.add(scanner.next());
		}

		// Close the file's stream
		disFile.close();

		// Verify that the login and the password exists in the file
		for(int i=0; i<arrayLogin.size(); i++) {

			// If the login exists in the file
			if((account.getLogin()).equals(arrayLogin.get(i)))

				// If the password that corresponds to that login is the one that the user entered
				if((account.getPwd()).equals(arrayPwd.get(i))) {

					// It's all good
					compliant = true;
				}
				else {

					// End of the comparison
					i = arrayLogin.size();
				}
		}
		
		// Respond to the client
		dos.writeBoolean(compliant);
	}

	// METHOD SUBMIT
	public void submit(DataOutputStream dos, DataInputStream dis, ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException {

		// Variable
		Account account;

		// Get the object Account
		account = (Account)ois.readObject();
		
		// Stream : file "logins_passwords.txt" 
		DataOutputStream dosFile = new DataOutputStream(new FileOutputStream("logins_passwords.txt", true));

		// Write in the file
		String newLine = System.getProperty("line.separator");
		dosFile.writeBytes(newLine);

		String temp = account.getLogin() + " " + account.getPwd();
		dosFile.write((temp).getBytes("UTF-8"));
	}
	
	// METHOD LIST
	public void list(DataOutputStream dos, DataInputStream dis, ObjectOutputStream oos, ObjectInputStream ois) {
		
		// Variable
		
		try {
			dos.writeUTF("The server is going to send you the topics' list ...");
			}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// METHOD CREATE
	public void create(DataOutputStream dos, DataInputStream dis, ObjectOutputStream oos, ObjectInputStream ois) {
		
		try {
			dos.writeUTF("The server is going to allow you to create a new topic ...");
			}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// METHOD JOIN
	public void join(DataOutputStream dos, DataInputStream dis, ObjectOutputStream oos, ObjectInputStream ois) {
		
		try {
			dos.writeUTF("The server is going to allow you to join a topic ...");
			}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

}
