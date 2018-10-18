/*
 * ChatProject ING4-APP-SI 2020
 * LAM Daphne
 * BUI Mai Anh
 * 
 */

package ChatPackage;

public class Message {

	// ATTRIBUTES
	private String login;
	private String msg;
	
	// CONSTRUCTOR
	public Message(String login, String msg) {
		this.login = login;
		this.msg = msg;
	}

	// ACCESSORS
	public String getLogin() {
		return login;
	}

	public String getMsg() {
		return msg;
	}
	
	// MUTATORS
	public void setLogin(String login) {
		this.login = login;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	// METHOD toString()
	@Override
	public String toString() {
		return "Message de " + login + " : " + msg;
	}
	
	
	
	
}
