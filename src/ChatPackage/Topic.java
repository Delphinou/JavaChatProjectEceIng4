/*
 * ChatProject ING4-APP-SI 2020
 * LAM Daphne
 * BUI Mai Anh
 * 
 */

package ChatPackage;

import java.util.ArrayList;

public class Topic {

	// ATTRIBUTES
	private String title;
	private ArrayList<Message> arrayMsg;
	
	// CONSTRUCTOR
	public Topic(String title, ArrayList<Message> arrayMsg) {
		super();
		this.title = title;
		this.arrayMsg = arrayMsg;
	}

	// ACCESSORS
	public String getTitle() {
		return title;
	}

	// MUTATORS
	public void setTitle(String title) {
		this.title = title;
	}
}
