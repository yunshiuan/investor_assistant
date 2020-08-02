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

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
	private HashMap<String, InvestmentTarget> tableTargets = new HashMap<String, InvestmentTarget>();

	// the hash table to contain the transactions for each investor
	// - each investor's records are stored in a linked list
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
	 * @return the tableTargets
	 */
	public HashMap<String, InvestmentTarget> getTableTargets() {
		return tableTargets;
	}

	/**
	 * Public Methods
	 */
	/**
	 * Import the file of transactions that is downloaded from brokerage companies
	 * This saves the records into 'this.tableRecords' and update the portfolios in
	 * 'this.tableInvestors' accordingly.
	 * 
	 * @param fileName a csv file that contains the file of transaction records.
	 * @return true if succeeded and false if failed
	 * @throws FailedReadingFileException
	 */
	public boolean importRecordData(String fileName) throws FailedReadingFileException {
		try {
			// TODO: Should check if the node is already present in the recorder
			// get the list of the new nodes in the file
			LinkedList<TransactionNode> newNodes = MyFileReader.readTransactionFile(fileName);
			// should check if the investor already exists in 'tableInvestors'
			Set<String> investorNames = this.tableInvestors.keySet();
			// add the new nodes to the recorder
			for (TransactionNode node : newNodes) {
				if (!investorNames.contains(node.getInvestorName())) {
					throw new NonExistentInvestorException("The investor " + node.getInvestorName()
							+ " is not present in the recorder. Shold be 'Amy' or 'Andy'.");
				}
				// if the investor exists in 'tableInvestors' but has empty records
				if (this.tableRecords.get(node.getInvestorName()) == null) {
					LinkedList<TransactionNode> newList = new LinkedList<TransactionNode>();
					newList.add(node);
					this.tableRecords.put(node.getInvestorName(), newList);
				} else {
					// if the investor exists in 'tableInvestors' and already has records
					this.tableRecords.get(node.getInvestorName()).add(node);
				}
			}
			// update the investors' portfolios
			this.updateInvestorPortfolioAfterImporting(newNodes);
			// update the investors' current balance
			this.updateAllInvestorsCurrentBalance();
			return true;
		} catch (IOException | InvalidFileFormatException | NonExistentInvestorException e) {
//			e.printStackTrace();
			throw new FailedReadingFileException(e.getMessage());
//			return false;			
		} catch (Exception e) {
			// TODO Should print this to the GUI
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Update the information of each target based on an external file. This update
	 * the current price in 'tableTargets' and add the target if it is not yet
	 * present in 'tableTargets'.
	 * 
	 * @param fileName the file that contains the current price of each target.
	 * @return true if succeeded and false if failed
	 * @throws FailedReadingFileException
	 */
	public boolean updateTargetInfo(String fileName) throws FailedReadingFileException {
		// get the hash table that contains the current price of each target
		try {
			HashMap<String, InvestmentTarget> newTargetInfo = MyFileReader
					.readTargetInfoFile(fileName);
			this.tableTargets = newTargetInfo;
		} catch (IOException | InvalidFileFormatException e) {
			throw new FailedReadingFileException(e.getMessage());
		} catch (Exception e) {
			// TODO Should print this to the GUI
			System.out.println("An unexpected exception occured!");
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Add the information of each investor based on an external file. This update
	 * the name and the target ratio and add the investor if it is not yet present
	 * in 'tableInvestors'.
	 * 
	 * @param fileName the file that contains investors' information.
	 * @return true if succeeded and false if failed
	 */
	public boolean updateInvestorInfo(String fileName) {
		// get the hash table that contains the information of each investor
		try {
			HashMap<String, Investor> newInvestorInfo = MyFileReader.readInvestorInfoFile(fileName);
			// add back the information that was present in the table but not in the
			// external file
			// - e.g., portfolio and rateReturn
			Set<String> investorNames = newInvestorInfo.keySet();
			for (String investorName : investorNames) {
				Investor oldInvestorInfo = this.tableInvestors.get(investorName);
				if (oldInvestorInfo != null) {
					newInvestorInfo.get(investorName)
							.setRateReturn(oldInvestorInfo.getRateReturn());
					newInvestorInfo.get(investorName).setPortfolio(oldInvestorInfo.getPortfolio());
				}
			}
			// update the tableInvestors
			this.tableInvestors = newInvestorInfo;
		} catch (IOException | InvalidFileFormatException e) {
			// TODO Should print this to the GUI
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Should print this to the GUI
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Add a transaction node to the recorder.
	 * 
	 * @param date
	 * @param investorName
	 * @param transactionType
	 * @param target
	 * @param unitPrice
	 * @param numUnits
	 * @throws NonExistentInvestorException
	 */
	public void addTransaction(long date, String investorName, String transactionType,
			String target, double unitPrice, double numUnits) throws NonExistentInvestorException {
		if (!this.tableInvestors.containsKey(investorName)) {
			throw new NonExistentInvestorException(
					"The investor you typed in not present in the recorder.");
		}
		TransactionNode newNode = new TransactionNode(date, investorName, transactionType, target,
				unitPrice, numUnits);
		this.tableRecords.get(investorName).add(newNode);
		// update the investors' portfolios
		this.updateInvestorPortfolioAfterOneChange(investorName, transactionType, target, numUnits);
		// update the investors' current balance
		this.updateOneInvestorCurrentBalance(investorName);
		;
	}

	/**
	 * Initialize the program based on the data in external files.
	 */
	public void initializeFromFiles() {
		try {
			this.updateInvestorInfo("./data/investor_info_20200731.csv");
			this.updateTargetInfo("./data/target_info_20200731.csv");
			this.importRecordData("./data/transaction_record_20200730.csv");
		} catch (Exception e) {
			System.out.println("Initilization failed.");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		// set the default directory for file chooser to the data directory
		String currentPath = Paths.get("./data/").toAbsolutePath().normalize().toString();
		Recorder.fileChooser.setInitialDirectory(new File(currentPath));
	}

	/**
	 * Initialize the program based on demo data.
	 */
	public void initializeDemo() {
		this.loadDemoInvestors();
		this.loadDemoRecords();
		this.loadDemoTargets();
		// set the default directory for file chooser to the data directory
		String currentPath = Paths.get("./data/").toAbsolutePath().normalize().toString();
		Recorder.fileChooser.setInitialDirectory(new File(currentPath));
	}

	/**
	 * Show a list of the transactions.
	 * 
	 * @param printTarget where the transaction should be printed to. Should be
	 *                    either 'console' or 'program'
	 * @return the list of transaction if printTarget = 'program'. Return null if
	 *         printing to the console.
	 */
	public String showAllTransactions(String printTarget) {
		// print to the console
		if (printTarget.equals("console")) {
			System.out.println("=================");
			System.out.println("Show all investors' transactions in the recorder");
			System.out.println("=================");
			Set<String> investorNames = this.tableInvestors.keySet();
			for (String investorName : investorNames) {
				LinkedList<TransactionNode> records = this.tableRecords.get(investorName);
				for (TransactionNode node : records) {
					System.out.println("-----------------");
					System.out.println(node.toString());
				}
			}
			System.out.println("========End=======");
			return null;
		} else if (printTarget.equals("program")) {
			// print to the program
			String stringRecords = "";
			stringRecords += "==========================================\n";
			stringRecords += "Show all investors' transactions in the recorder\n";
			stringRecords += "==========================================\n";
			Set<String> investorNames = this.tableInvestors.keySet();
			for (String investorName : investorNames) {
				stringRecords += showInvestorTransactions(investorName, printTarget);
			}
			return stringRecords;
		} else {
			System.out.println("Unrecognize printing target.");
		}
		return null;
	}

	/**
	 * Show a given investor's list of the transactions.
	 * 
	 * @param investor    the investor's name.
	 * @param printTarget where the transaction should be printed to. Should be
	 *                    either 'console' or 'program'
	 * @return the list of transaction if printTarget = 'program'. This returned
	 *         list will be returned to the program window. Return null if printing
	 *         to the console.
	 */
	public String showInvestorTransactions(String investorName, String printTarget) {
		// print to the console
		if (printTarget.equals("console")) {
			System.out.println("==========================================");
			System.out.println("Show " + investorName + "'s transactions in the recorder");
			System.out.println("==========================================");
			LinkedList<TransactionNode> records = this.tableRecords.get(investorName);
			for (TransactionNode node : records) {
				System.out.println("-----------------");
				System.out.println(node.toString());
			}
			System.out.println("========End=======");
			return null;
		} else if (printTarget.equals("program")) {
			// print to the program
			String stringRecords = "";
			stringRecords += "==========================================\n";
			stringRecords += investorName + "'s transactions\n";
			stringRecords += "==========================================\n";
			stringRecords += "=======Start=======\n";
			LinkedList<TransactionNode> records = this.tableRecords.get(investorName);
			for (TransactionNode node : records) {
				stringRecords += "-----------------\n";
				stringRecords += node.toString();
				stringRecords += "\n";
			}
			stringRecords += "========End=======\n";
			return stringRecords;
		} else {
			System.out.println("Unrecognize printing target.");
		}
		return null;
	}

	/**
	 * Show the information of all targets in the recorder.
	 * 
	 * @param printTarget where the transaction should be printed to. Should be
	 *                    either 'console' or 'program'
	 * @return the list of transaction if printTarget = 'program'. This returned
	 *         list will be returned to the program window. Return null if printing
	 *         to the console.
	 */
	public void showAllTargetsInfo(String printTarget) {
		System.out.println("==========================================");
		System.out.println("Show the information of all targets in the recorder.");
		System.out.println("==========================================");
		Set<String> targetNames = this.tableTargets.keySet();
		for (String targetName : targetNames) {
			System.out.println("-----------------");
			System.out.println(this.tableTargets.get(targetName).toString());
		}
		System.out.println("========End=======");
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
	 * @param transactionType
	 * @param target
	 * @param numUnits
	 */
	private void updateInvestorPortfolioAfterOneChange(String investorName, String transactionType,
			String target, double numUnits) {

		// update the number of units
		HashMap<String, Double> currentPortfolio = this.tableInvestors.get(investorName)
				.getPortfolio();
		// special case for the new investor who does not yet have any record
		if (currentPortfolio == null) {
			this.tableInvestors.get(investorName).setPortfolio(new HashMap<String, Double>());
			this.tableInvestors.get(investorName).getPortfolio().put(target, numUnits);
			return;
		}
		// if the target not yet exists in this investor's portfolio
		if (!currentPortfolio.containsKey(target)) {
			this.tableInvestors.get(investorName).getPortfolio().put(target, numUnits);
			return;
		}
		// if the target already exists in this investor's portfolio
		double currentNumUnits = currentPortfolio.get(target);
		double updatedNumUnits = currentNumUnits;
		if (transactionType.equals("buy")) {
			updatedNumUnits += numUnits;
		} else if (transactionType.equals("sell")) {
			updatedNumUnits -= numUnits;
		}
		this.tableInvestors.get(investorName).getPortfolio().put(target, updatedNumUnits);
	}

	/**
	 * Update the investors' portfolios (in tableInvestors) based on the given list
	 * of new nodes. This method is required after importing an external record
	 * file.
	 * 
	 * @param newNodes newly added nodes
	 */
	private void updateInvestorPortfolioAfterImporting(LinkedList<TransactionNode> newNodes) {
		// iterate over the new nodes
		for (TransactionNode node : newNodes) {
			this.updateInvestorPortfolioAfterOneChange(node.getInvestorName(),
					node.getTransactionType(), node.getTarget(), node.getNumUnits());
		}

	}

	/**
	 * Update one investor's current balance according to current target price.
	 * 
	 * @param investorName
	 */
	private void updateOneInvestorCurrentBalance(String investorName) {
		this.tableInvestors.get(investorName).computeCurrentBalance(this.tableTargets);
	}

	/**
	 * Update all investors' current balance according to current target price.
	 */
	private void updateAllInvestorsCurrentBalance() {
		// get all investor names
		Set<String> investorNames = this.tableInvestors.keySet();

		// iterate over each investor
		for (String investorName : investorNames) {
			updateOneInvestorCurrentBalance(investorName);
		}
	}

	/**
	 * Load in demo investor data.
	 */
	private void loadDemoInvestors() {
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

	/**
	 * Load in demo record data.
	 */
	private void loadDemoRecords() {
		LinkedList<TransactionNode> recordsA = new LinkedList<TransactionNode>();
		LinkedList<TransactionNode> recordsB = new LinkedList<TransactionNode>();
		this.tableRecords.put("Andy", recordsA);
		this.tableRecords.put("Amy", recordsB);
	}

	private void loadDemoTargets() {
		this.tableTargets.put("VTI", new InvestmentTarget("VTI", 150, "stock"));
		this.tableTargets.put("VGK", new InvestmentTarget("VGK", 50, "stock"));
		this.tableTargets.put("VWO", new InvestmentTarget("VWO", 40, "stock"));
		this.tableTargets.put("IEI", new InvestmentTarget("IEI", 134, "bond"));
	}
}
