/**
 * This is the program for the assignment a3.
 * 
 * @project a3 Milestone 3: Final Project
 * @course CS400
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.eduF
 * @date 20200730
 * @attribution 
 * - see Main.java
 */
package application;

import java.util.Hashtable;
import java.util.LinkedList;

import javafx.stage.FileChooser;

/**
 * The class stores the transaction records and provide the funtionality of the
 * program.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200730
 *
 */
public class Recorder {
	/**
	 * Constructors
	 */
	
	/**
	 * Private fields
	 */
	// a file chooser for browsing the file
	protected static final FileChooser fileChooser = new FileChooser();
	// the table to contFain investors
	private Hashtable<String, Investor> tableInvestors = new Hashtable<String, Investor>();
	// the list to contain the transactions
	// TODO: Should utilize this
	private LinkedList<TransactionNode> records = new LinkedList<TransactionNode>();
	/**
	 * @return the tableInvestors
	 */
	public Hashtable<String, Investor> getTableInvestors() {
		return tableInvestors;
	}
	/**
	 * @param tableInvestors the tableInvestors to set
	 */
	public void setTableInvestors(Hashtable<String, Investor> tableInvestors) {
		this.tableInvestors = tableInvestors;
	}
	

}
