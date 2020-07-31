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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

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
	private HashMap<String, Investor> tableInvestors = new HashMap<String, Investor>();
	// the hash table with keys being the investment target name and values being
	// the object InvestmentTarget (which contains the investment target
	// information)
	private HashMap<String, InvestmentTarget> tableInvestmentTarget;
	// the hash table to contain the transactions for each investor
	private HashMap<String, LinkedList<TransactionNode>> tableRecords = new HashMap<String, LinkedList<TransactionNode>>();
	// the file reader to read and parse the imported file
	// - use the static methods in MyFileReader

	/**
	 * Getters and setters
	 */
	/**
	 * @return the tableInvestors
	 */
	public HashMap<String, Investor> getTableInvestors() {
		return tableInvestors;
	}

	/**
	 * @param tableInvestors the tableInvestors to set
	 */
	public void setTableInvestors(HashMap<String, Investor> tableInvestors) {
		this.tableInvestors = tableInvestors;
	}

	/**
	 * Public Methods
	 */
	/**
	 * Import the file of transactions that is downloaded from brokerage companies
	 * 
	 * @param fileName
	 * @return true if succeeded and false if failed
	 * @throws NonExistentInvestorException
	 */
	public boolean importData(String fileName) throws NonExistentInvestorException {
		try {
			// should check if the investor already exists in the recorder
			Set<String> investorNames = this.tableInvestors.keySet();

			// get the list of the new nodes in the file
			LinkedList<TransactionNode> newNodes = MyFileReader.readTransactionFile(fileName);
			// add the new nodes to the recorder
			for (TransactionNode node : newNodes) {
				if (!investorNames.contains(node.getInvestorName())) {
					throw new NonExistentInvestorException("The investor " + node.getInvestorName()
							+ " is not present in the recorder.");
				}
				this.tableRecords.get(node.getInvestorName()).add(node);
			}
			// update the investors' portfolios
			this.updateInvestorPortfolioAfterImporting();
			return true;
		} catch (IOException | InvalidFileFormatException e) {
			// TODO Should print this to the GUI
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Should print this to the GUI
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Show a list of the transactions.
	 */
	public void showAllTransactions() {
		System.out.println("=================");
		System.out.println("Show all transactions in the recorders");
		System.out.println("=================");
		Set<String> investorNames = this.tableInvestors.keySet();
		for (String investorName : investorNames) {
			System.out.println("-----------------");
			System.out.println("Show " + investorName + "'s transactions in the recorders");
			System.out.println("-----------------");
			LinkedList<TransactionNode> records = this.tableRecords.get(investorName);
			for (TransactionNode node : records) {
				System.out.println("-----------------");
				System.out.println(node.toString());
			}
		}

	}

	/**
	 * Show a given investor's list of the transactions.
	 * 
	 * @param investor the investor's name.
	 */
	public void showInvestorTransactions(String investorName) {
		System.out.println("=================");
		System.out.println("Show " + investorName + "'s transactions in the recorders");
		System.out.println("=================");
		LinkedList<TransactionNode> records = this.tableRecords.get(investorName);
		for (TransactionNode node : records) {
			System.out.println("-----------------");
			System.out.println(node.toString());
		}
	}

	/**
	 * Load in demo investor data.
	 */
	public void loadDemoInvestors() {
		Investor investorA = new Investor("Andy", Double.valueOf(0.8), Double.valueOf(5.5),
				new HashMap<String, Double>());
		investorA.getPortfolio().put("VTI", 8.2);
		investorA.getPortfolio().put("VGK", 3.2);
		investorA.getPortfolio().put("VWO", 1.4);
		investorA.getPortfolio().put("IEI", 1.2);
		this.tableInvestors.put(investorA.getName(), investorA);

		Investor investorB = new Investor("Amy", Double.valueOf(0.6), Double.valueOf(1.5),
				new HashMap<String, Double>());
		investorB.getPortfolio().put("VTI", 2.5);
		investorB.getPortfolio().put("VGK", 1.4);
		investorB.getPortfolio().put("VWO", 5.0);
		investorB.getPortfolio().put("IEI", 3.0);
		this.tableInvestors.put(investorB.getName(), investorB);
	}

	public void loadDemoRecords() {
		LinkedList<TransactionNode> recordsA = new LinkedList<TransactionNode>();
		LinkedList<TransactionNode> recordsB = new LinkedList<TransactionNode>();
		this.tableRecords.put("Andy", recordsA);
		this.tableRecords.put("Amy", recordsB);
	}

	/**
	 * Private Methods
	 */
	/**
	 * Update the investors' portfolios (in tableInvestors) based on the one
	 * transaction record. This method is required after adding or deleting one
	 * transaction record.
	 * 
	 * @param investorName
	 * @param TransactionType
	 * @param target
	 * @param numUnits
	 */
	private void updateInvestorPortfolioAfterOneChange(String investorName, String TransactionType,
			String target, double numUnits) {
		// update the number of units
		HashMap<String, Double> currentPortfolio = this.tableInvestors.get(investorName)
				.getPortfolio();
		// - if the target not yet exists in this investor's portfolio
		if (!currentPortfolio.containsKey(target)) {
			this.tableInvestors.get(investorName).getPortfolio().put(target, numUnits);
			return;
		}
		// - if the target already exists in this investor's portfolio
		double currentNumUnits = currentPortfolio.get(target);
		double updatedNumUnits = currentNumUnits;
		if (TransactionType.equals("buy")) {
			updatedNumUnits += numUnits;
		} else if (TransactionType.equals("sell")) {
			updatedNumUnits -= numUnits;
		}
		this.tableInvestors.get(investorName).getPortfolio().put(target, updatedNumUnits);
	}

	/**
	 * Update the investors' portfolios (in tableInvestors) based on the current
	 * total number of units (records). This method is required after importing an
	 * external record file.
	 */
	private void updateInvestorPortfolioAfterImporting() {
		// get all investor names
		Set<String> investorNames = this.tableInvestors.keySet();

		// iterate over the tableRecords and output each investor's updated number of
		// units of each target
		for (String investorName : investorNames) {
			// the current records
			LinkedList<TransactionNode> records = this.tableRecords.get(investorName);
			// update the number of units based on current records
			for (TransactionNode node : records) {
				this.updateInvestorPortfolioAfterOneChange(node.getInvestorName(),
						node.getTransactionType(), node.getTarget(), node.getNumUnits());
			}

		}
	}
}
