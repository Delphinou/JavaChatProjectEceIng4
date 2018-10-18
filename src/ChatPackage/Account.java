/*
 * ChatProject ING4-APP-SI 2020
 * LAM Daphne
 * BUI Mai Anh
 * 
 */

package ChatPackage;

import java.io.Serializable;

public class Account implements Serializable{
	
	// ATTRIBUTES
	private String login;
	private String pwd;
	private static final long serialVersionUID = 1L;
	
	// CONSTRUCTOR
	public Account(String login, String pwd) {
		this.login = login;
		this.pwd = pwd;
	}

	// ACCESSORS
	public String getLogin() {
		return login;
	}

	public String getPwd() {
		return pwd;
	}

	// MUTATORS
	public void setLogin(String login) {
		this.login = login;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
