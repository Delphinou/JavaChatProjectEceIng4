/*
 * ChatProject ING4-APP-SI 2020
 * LAM Daphne
 * BUI Mai Anh
 * 
 */

package ChatPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends JFrame implements ActionListener {

	// ATTRIBUTES
	private Account account;
	private Socket socket;
	private int widthSize;
	private int heightSize;
	private int fontSize;
	private String font;
	private JPanel panel; 		// Used as a "buffer"

	// CONSTRUCTOR
	public Client() throws UnknownHostException, IOException {

		// Open socket just once
		//socket = new Socket(InetAddress.getLocalHost(), 1000);

		// Call the first login GUI
		buildGUI();
		login();

	}

	// METHOD BUILD THE GUI
	public void buildGUI() {

		// Initialisation of the variables
		widthSize=750;
		heightSize=600;
		fontSize=18;
		font="Arial";

		// Intitialisation of the window
		setTitle("Chat Project");
		setSize(widthSize, heightSize);

		panel = new JPanel();
		add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setVisible(true);

	}

	// METHOD LOGIN 
	public void login() {

		// When we log in, we fill a formula

		// Remove all from the panel to rebuild it
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		// JComponents
		JLabel labelHello = new JLabel("HELLO ! WELCOME TO OUR CHAT ! :D ");
		JLabel labelLogin = new JLabel("Login:        ");
		JLabel labelPassword = new JLabel("Password: ");
		JTextField textFieldLogin = new JTextField("", 15);
		JTextField textFieldPwd = new JTextField("", 15);

		// Buttons
		JButton buttonSignIn = new JButton("Sign in!");
		buttonSignIn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				signIn();
			}
		});

		JButton buttonNext = new JButton("Next!");
		buttonNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try {
					account = new Account(textFieldLogin.getText(), textFieldPwd.getText());
					logIn();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		labelHello.setFont(new Font(font, Font.PLAIN, fontSize));
		labelLogin.setFont(new Font(font, Font.PLAIN, fontSize));
		labelPassword.setFont(new Font(font, Font.PLAIN, fontSize));

		// Add all to the panel
		//panel.add(labelHello);

		JPanel wrapper1 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper1.add(labelLogin);
		wrapper1.add(textFieldLogin);

		JPanel wrapper2 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper2.add(labelPassword);
		wrapper2.add(textFieldPwd);

		JPanel wrapper3 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper3.add(buttonSignIn);
		wrapper3.add(buttonNext);

		JPanel wrapper4 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper4.add(labelHello);
		wrapper4.add(wrapper1);
		wrapper4.add(wrapper2);
		wrapper4.add(wrapper3);
		wrapper4.setLayout(new BoxLayout(wrapper4, BoxLayout.PAGE_AXIS));

		panel.add(wrapper4);

		// Validate the new panel to show it
		panel.revalidate();
		panel.repaint();

	}

	// METHOD SIGN IN
	public void signIn() {

		// Remove all from the panel to rebuild it
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		// JComponents
		JLabel labelInstructions = new JLabel("PLEASE CHOOSE A LOGIN AND A PASSWORD !");
		JLabel labelLogin = new JLabel("Login:        ");
		JLabel labelPassword = new JLabel("Password: ");
		JTextField textFieldLogin = new JTextField("", 15);
		JTextField textFieldPwd = new JTextField("", 15);

		// Buttons
		JButton buttonSubmit = new JButton("Submit!");
		buttonSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try {
					if( ( !(textFieldLogin.getText()).equals("") ) && ( !(textFieldPwd.getText()).equals("") ) ){
						account = new Account(textFieldLogin.getText(), textFieldPwd.getText());
						submit();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		labelInstructions.setFont(new Font(font, Font.PLAIN, fontSize));
		labelLogin.setFont(new Font(font, Font.PLAIN, fontSize));
		labelPassword.setFont(new Font(font, Font.PLAIN, fontSize));

		JPanel wrapper1 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper1.add(labelLogin);
		wrapper1.add(textFieldLogin);

		JPanel wrapper2 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper2.add(labelPassword);
		wrapper2.add(textFieldPwd);

		JPanel wrapper3 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper3.add(buttonSubmit);

		JPanel wrapper4 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper4.add(labelInstructions);
		wrapper4.add(wrapper1);
		wrapper4.add(wrapper2);
		wrapper4.add(wrapper3);
		wrapper4.setLayout(new BoxLayout(wrapper4, BoxLayout.PAGE_AXIS));

		panel.add(wrapper4);

		// Validate the new panel to show it
		panel.revalidate();
		panel.repaint();
	}

	// METHOD LOG IN
	public void logIn() throws IOException {
		
		// We need to verify if the login and the password are corrects
		try {
			// Open socket
			socket = new Socket(InetAddress.getLocalHost(), 1000);
			
			// Open the output/input streams
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			// Send the login and the password to the server
			dos.writeUTF("log in");
			dos.flush();
			
			// Send the object account
			oos.writeObject(account);
			
			// Access authorized
			if(dis.readBoolean() == true) {
				socket.close();
				menu();
			}
			
			socket.close();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// METHOD SUBMIT
	public void submit() throws IOException {
		
		// We want to save the new login and the new password in our file
		try {
			
			socket = new Socket(InetAddress.getLocalHost(), 1000);
			
			// Open the output/input streams
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			// Send the login and the password to the server
			dos.writeUTF("submit");
			dos.flush();
 
			// Send the object account
			oos.writeObject(account);

			socket.close();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		menu();
	}

	// METHOD MENU
	public void menu() {
		
		// Remove all from the panel to rebuild it
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		
		// JComponents
		JLabel labelMenu = new JLabel("MENU");
		
		JButton buttonList = new JButton("List of the topics");
		buttonList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				list();
			}
		});
		
		JButton buttonCreation = new JButton("Create a new topic");
		buttonCreation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				create();
			}
		});
		
		JButton buttonJoin = new JButton("Join a topic");
		buttonJoin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				join();
			}
		});
		
		JPanel wrapper1 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper1.add(buttonList);
		wrapper1.add(buttonCreation);
		wrapper1.add(buttonJoin);
		
		JPanel wrapper2 = new JPanel( new FlowLayout(0, 0, FlowLayout.LEADING) );
		wrapper2.add(labelMenu);
		wrapper2.add(wrapper1);
		wrapper2.setLayout(new BoxLayout(wrapper2, BoxLayout.PAGE_AXIS));
		
		panel.add(wrapper2);
		
		// Validate the new panel to show it
		panel.revalidate();
		panel.repaint();
	}

	// METHOD LIST
	public void list(){

		// Variable 
		ArrayList<Topic> arrayTopic = new ArrayList<Topic>();

		// Remove all from the panel to rebuild it
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		try {
			// Open socket
			socket = new Socket(InetAddress.getLocalHost(), 1000);

			// Open input and output streams
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			dos.writeUTF("list");
			dos.flush();
			System.out.println(dis.readUTF());

			//String str = (String) ois.readObject();
			//System.out.println(str);
			
			/*arrayTopic = (ArrayList<Topic>) ois.readObject();

			System.out.println("coucou2");

			for(int i = 0; i < arrayTopic.size(); i++) {
				System.out.println(arrayTopic.get(i).getTitle());
			}*/

			// Fermeture du socket
			socket.close();

		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return button
		JButton buttonReturn = new JButton("Return");
		buttonReturn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				menu();
			}
		});	
		panel.add(buttonReturn);

		// Validate the new panel to show it
		panel.revalidate();
		panel.repaint();
	}
	
	// METHOD CREATE
	public void create() {
		
		// Remove all from the panel to rebuild it
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		// Remove all from the panel to rebuild it
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		try {
			// Open socket
			socket = new Socket(InetAddress.getLocalHost(), 1000);
			
			// Open the output/input streams
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			dos.writeUTF("create");
			System.out.println(dis.readUTF());
			
			socket.close();

		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return button
		JButton buttonReturn = new JButton("Return");
		buttonReturn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				menu();
			}
		});	
		panel.add(buttonReturn);
		
		// Validate the new panel to show it
		panel.revalidate();
		panel.repaint();
	}
	
	// METHOD JOIN
	public void join() {
		
		// Remove all from the panel to rebuild it
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		// Remove all from the panel to rebuild it
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		try {
			// Open socket
			socket = new Socket(InetAddress.getLocalHost(), 1000);
			
			// Open the output/input streams
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			dos.writeUTF("join");
			System.out.println(dis.readUTF());
			
			socket.close();

		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return button
		JButton buttonReturn = new JButton("Return");
		buttonReturn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				menu();
			}
		});	
		panel.add(buttonReturn);
		
		// Validate the new panel to show it
		panel.revalidate();
		panel.repaint();
		
	}
	
	// MAIN
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
