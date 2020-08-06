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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

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
	 * Getters
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
	 * @return the tableRecords
	 */
	public HashMap<String, LinkedList<TransactionNode>> getTableRecords() {
		return tableRecords;
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
	 * @throws Exception
	 */
	public boolean importRecordData(String fileName) throws Exception {
		try {
			// get the list of the new nodes in the file
			LinkedList<TransactionNode> newNodes = MyFileReader.readTransactionFile(fileName);
			// should check if the investor already exists in 'tableInvestors'
			Set<String> investorNames = this.tableInvestors.keySet();
			// should check if the target already exists in 'tableTargets'
			Set<String> targetNames = this.tableTargets.keySet();

			// add the new nodes to the recorder
			for (TransactionNode node : newNodes) {
				// non-existent investors
				if (!investorNames.contains(node.getInvestorName())) {
					// Future TODO: should not hard-code this
					throw new NonExistentInvestorException("The investor " + node.getInvestorName()
							+ " is not present in the recorder. Shold be 'Amy' or 'Andy'.");
				}
				// non-existent targets
				if (!targetNames.contains(node.getTarget())) {
					// Future TODO: should not hard-code this
					throw new NonExistentTargetException("The target " + node.getTarget()
							+ " is not present in the recorder. "
							+ "Should be one of the following: 'VTI','VWO','VPL','VWO','IEI','BWX'.");
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
			// update the investor's rate of return
			this.updateAllInvestorsRateReturn();
			return true;
		} catch (IOException | InvalidFileFormatException | NonExistentInvestorException
				| NonExistentTargetException e) {
			throw new FailedReadingFileException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("An unexpected error occured.\n\nError message: " + e.getMessage());
		}
	}

	/**
	 * Update the information of each target based on an external file. This update
	 * the current price in 'tableTargets' and add the target if it is not yet
	 * present in 'tableTargets'.
	 * 
	 * @param fileName the file that contains the current price of each target.
	 * @return true if succeeded and false if failed
	 * @throws Exception
	 */
	public boolean updateTargetInfo(String fileName) throws Exception {
		// get the hash table that contains the current price of each target
		try {
			HashMap<String, InvestmentTarget> newTargetInfo = MyFileReader
					.readTargetInfoFile(fileName);
			this.tableTargets = newTargetInfo;
			this.updateAllInvestorsCurrentBalance();
			this.updateAllInvestorsRateReturn();
		} catch (IOException | InvalidFileFormatException e) {
			throw new FailedReadingFileException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("An unexpected error occured.\n\nError message: " + e.getMessage());
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
	 * @throws Exception
	 */
	public boolean updateInvestorInfo(String fileName) throws Exception {
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
			throw new FailedReadingFileException(
					"An error occured while reading the file.\n\nError message: " + e.getMessage());
		} catch (Exception e) {
			throw new Exception("An unexpected error occured.\n\nError message: " + e.getMessage());
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
	 * @throws NonExistentTargetException
	 */
	public void addTransaction(long date, String investorName, String transactionType,
			String target, double unitPrice, double numUnits)
			throws NonExistentInvestorException, NonExistentTargetException {
		if (!this.tableInvestors.containsKey(investorName)) {
			throw new NonExistentInvestorException(
					"The investor you typed in is not present in the recorder.");
		}
		if (!this.tableTargets.containsKey(target)) {
			throw new NonExistentTargetException(
					"The investment target you typed in is not present in the recorder.");
		}
		if (!(transactionType.toLowerCase().equals("buy")
				|| transactionType.toLowerCase().equals("sell"))) {
			throw new IllegalArgumentException(
					"The transaction type should be either 'buy' or 'sell'.");
		}
		TransactionNode newNode = new TransactionNode(date, investorName, transactionType, target,
				unitPrice, numUnits);
		this.tableRecords.get(investorName).add(newNode);
		// update the investors' portfolios
		this.updateInvestorPortfolioAfterOneChange(investorName, transactionType, target, numUnits,
				unitPrice);
		// update the investors' current balance
		this.updateOneInvestorCurrentBalance(investorName);
		// update the investors' rate of return
		this.updateOneInvestorRateReturn(investorName);
		;
	}

	/**
	 * Initialize the program based on the data in external files.
	 */
	public void initializeFromFiles() {
		try {
			this.updateTargetInfo("./data/target_info_20200731.csv");
			this.updateInvestorInfo("./data/investor_info_20200731.csv");
			this.importRecordData("./data/transaction_record_20200730.csv");
		} catch (Exception e) {
			System.out.println("Initilization failed.");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Initialize the program based on demo data.
	 */
	public void initializeDemo() {
		this.loadDemoInvestors();
		this.loadDemoRecords();
		this.loadDemoTargets();
	}

	/**
	 * Show a list of the transactions.
	 * 
	 * @param printTarget where the transaction should be printed to. Should be
	 *                    either 'console' or 'program'
	 * @param startDate   only show the transactions starting from this date. The
	 *                    format should be yyyymmdd.
	 * @param endDate     only show the transactions until this date. The format
	 *                    should be yyyymmdd.
	 * @return the list of transaction if printTarget = 'program'. Return null if
	 *         printing to the console.
	 */
	public String showAllTransactions(String printTarget, Long startDate, Long endDate) {
		// print to the console
		if (printTarget.equals("console")) {
			System.out.println("=================");
			System.out.println("Show all investors' transactions in the recorder");
			System.out.println("=================");
			Set<String> investorNames = this.tableInvestors.keySet();
			for (String investorName : investorNames) {
				showInvestorTransactions(investorName, printTarget, startDate, endDate);
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
				stringRecords += showInvestorTransactions(investorName, printTarget, startDate,
						endDate);
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
	 * @param startDate   only show the transactions starting from this date. The
	 *                    format should be yyyymmdd.
	 * @param endDate     only show the transactions until this date. The format
	 *                    should be yyyymmdd.
	 * @return the list of transaction if printTarget = 'program'. This returned
	 *         list will be returned to the program window. Return null if printing
	 *         to the console.
	 * @throws NullPointerException
	 */
	public String showInvestorTransactions(String investorName, String printTarget, Long startDate,
			Long endDate) throws NullPointerException {
		// print to the console
		if (printTarget.equals("console")) {
			System.out.println("==========================================");
			System.out.println("Show " + investorName + "'s transactions in the recorder");
			System.out.println("==========================================");
			LinkedList<TransactionNode> records = this.tableRecords.get(investorName);
			for (TransactionNode node : records) {
				// if date range is specified
				if (startDate != null && endDate != null) {
					// skip the record if not in the date range of interest
					if (!(node.getDate() >= startDate && node.getDate() <= endDate)) {
						continue;
					}
				}
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
			int countValidRecords = 0;
			LinkedList<TransactionNode> records = this.tableRecords.get(investorName);
			for (TransactionNode node : records) {
				// if date range is specified
				if (startDate != null && endDate != null) {
					// skip the record if not in the date range of interest
					if (!(node.getDate() >= startDate && node.getDate() <= endDate)) {
						continue;
					}
				}
				stringRecords += "-----------------\n";
				stringRecords += node.toString();
				stringRecords += "\n";
				countValidRecords++;
			}
			if (countValidRecords == 0) {
				throw new NullPointerException(
						"There are no records that fullfill the date range you specified."
								+ " Please re-specify the date range, "
								+ "e.g, start date = 6/19/2019 and end date = 8/1/2020");
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
		if (printTarget == "console") {
			System.out.println("==========================================");
			System.out.println("Show the information of all targets in the recorder.");
			System.out.println("==========================================");
			Set<String> targetNames = this.tableTargets.keySet();
			for (String targetName : targetNames) {
				System.out.println("-----------------");
				System.out.println(this.tableTargets.get(targetName).toString());
			}
			System.out.println("========End=======");
		} else if (printTarget == "program") {
			// undefined
		} else {
			System.out.println("Unrecognize printing target.");
		}
	}

	/**
	 * Save the transactions in the recorder to an external file.
	 * 
	 * @param fileName the file to save the transactions to.
	 * @throws FailedWritingFileException
	 */
	public void saveTransactions(String fileName) throws FailedWritingFileException {
		try {
			MyFileReader.writeTransactionToFile(fileName, this);
		} catch (FileNotFoundException e) {
			throw new FailedWritingFileException(e.getMessage());
		}
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
	 * @param numUnits        the number of units of the given target in this
	 *                        transaction
	 * @param unitPrice       the unit price of the target in this transaction
	 */
	private void updateInvestorPortfolioAfterOneChange(String investorName, String transactionType,
			String target, double numUnits, double unitPrice) {

		// update the number of units
		HashMap<String, PortfolioNode> currentPortfolio = this.tableInvestors.get(investorName)
				.getPortfolio();
		// special case for the new investor who does not yet have any records
		if (currentPortfolio == null) {
			this.tableInvestors.get(investorName)
					.setPortfolio(new HashMap<String, PortfolioNode>());
			this.tableInvestors.get(investorName).getPortfolio().put(target,
					new PortfolioNode(numUnits, unitPrice));
			return;
		}
		// if the target not yet exists in this investor's portfolio
		if (!currentPortfolio.containsKey(target)) {
			this.tableInvestors.get(investorName).getPortfolio().put(target,
					new PortfolioNode(numUnits, unitPrice));
			return;
		}
		// if the target already exists in this investor's portfolio
		double currentNumUnits = currentPortfolio.get(target).getNumUnits();
		double updatedNumUnits = currentNumUnits;
		double currentAvgUnitCost = currentPortfolio.get(target).getAverageUnitCost();
		double updatedAvgUnitCost = currentAvgUnitCost;
		if (transactionType.toLowerCase().equals("buy")) {
			// update the number of units
			updatedNumUnits += numUnits;
			// update the average unit cost
			updatedAvgUnitCost = ((currentNumUnits * currentAvgUnitCost) + (numUnits * unitPrice))
					/ (currentNumUnits + numUnits);
		} else if (transactionType.toLowerCase().equals("sell")) {
			// update the number of units
			updatedNumUnits -= numUnits;
			// update the average unit cost
			updatedAvgUnitCost = ((currentNumUnits * currentAvgUnitCost) - (numUnits * unitPrice))
					/ (currentNumUnits - numUnits);
		}
		this.tableInvestors.get(investorName).getPortfolio().put(target,
				new PortfolioNode(updatedNumUnits, updatedAvgUnitCost));
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
					node.getTransactionType(), node.getTarget(), node.getNumUnits(),
					node.getUnitPrice());
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
	 * Update one investor's rate of return based on the current balance.
	 * 
	 * @param investorName
	 */
	private void updateOneInvestorRateReturn(String investorName) {
		this.tableInvestors.get(investorName).computeRateOfReturn();
	}

	/**
	 * Update all investors' rate of return based on the current balance.
	 */
	private void updateAllInvestorsRateReturn() {
		// get all investor names
		Set<String> investorNames = this.tableInvestors.keySet();

		// iterate over each investor
		for (String investorName : investorNames) {
			updateOneInvestorRateReturn(investorName);
		}
	}

	/**
	 * Load in demo investor data.
	 */
	private void loadDemoInvestors() {
		Investor investorA = new Investor("Andy", Double.valueOf(0.8), Double.valueOf(5.5),
				new HashMap<String, PortfolioNode>());
		investorA.getPortfolio().put("VTI", new PortfolioNode(8.2, 1.0));
		investorA.getPortfolio().put("VGK", new PortfolioNode(3.2, 2.0));
		investorA.getPortfolio().put("VWO", new PortfolioNode(1.4, 3.0));
		investorA.getPortfolio().put("IEI", new PortfolioNode(1.2, 4.0));
		this.tableInvestors.put(investorA.getName(), investorA);

		Investor investorB = new Investor("Amy", Double.valueOf(0.6), Double.valueOf(1.5),
				new HashMap<String, PortfolioNode>());
		investorB.getPortfolio().put("VTI", new PortfolioNode(2.5, 2.0));
		investorB.getPortfolio().put("VGK", new PortfolioNode(1.4, 3.0));
		investorB.getPortfolio().put("VWO", new PortfolioNode(5.0, 4.0));
		investorB.getPortfolio().put("IEI", new PortfolioNode(3.0, 5.0));
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
