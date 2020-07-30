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

import java.io.IOException;
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
	 * No-args
	 */
	public Recorder() {

	}

	/**
	 * Private fields
	 */
	// a file chooser for browsing the file
	protected static final FileChooser fileChooser = new FileChooser();
	// the table to contain investors
	private Hashtable<String, Investor> tableInvestors = new Hashtable<String, Investor>();
	// the hash table with keys being the investment target name and values being
	// the object InvestmentTarget (which contains the investment target
	// information)
	private Hashtable<String, InvestmentTarget> tableInvestmentTarget;
	// the list to contain the transactions
	// TODO: Should utilize this
	private LinkedList<TransactionNode> records = new LinkedList<TransactionNode>();
	// the file reader to read and parse the imported file
	// - use the static methods in MyFileReader

	/**
	 * Getters and setters
	 */
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

	/**
	 * Public Methods
	 */
	/**
	 * import the file of transactions that is downloaded from brokerage companies
	 * 
	 * @param fileName
	 * @return true if succeeded and false if failed
	 */
	public boolean importData(String fileName) {
		try {
			this.records.addAll(MyFileReader.readTransactionFile(fileName));
//			System.out.println(this.records.toString());
		} catch (IOException | InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Load in demo investor data.
	 */
	public void loadDemoInvestors() {
		Investor investorA = new Investor("Andy", Double.valueOf(0.8), Double.valueOf(5.5),
				new Hashtable<String, Double>());
		investorA.getPortfolio().put("VTI", 8.2);
		investorA.getPortfolio().put("VGK", 3.2);
		investorA.getPortfolio().put("VWO", 1.4);
		investorA.getPortfolio().put("IEI", 1.2);
		this.tableInvestors.put(investorA.getName(), investorA);

		Investor investorB = new Investor("Amy", Double.valueOf(0.6), Double.valueOf(1.5),
				new Hashtable<String, Double>());
		investorB.getPortfolio().put("VTI", 2.5);
		investorB.getPortfolio().put("VGK", 1.4);
		investorB.getPortfolio().put("VWO", 5.0);
		investorB.getPortfolio().put("IEI", 3.0);
		this.tableInvestors.put(investorB.getName(), investorB);
	}

	/**
	 * Private Methods
	 */
}
