/**
 * This is the program for the assignment a3.
 * 
 * @project a3 Milestone 3: Final Project
 * @course CS400
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200714
 * @attribution 
 * - see Main.java
 */
package application;

/**
 * The class to store the information of each transaction.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200715
 *
 */
public class TransactionNode {
	/**
	 * Private fields
	 */
	// the date of the transaction
	private long date;
	// the name of the investor
	private String investorName;
	// ”sell”,”buy”,”dividend”
	private String TransactionType;
	// the name of the investment target
	private String target;
	// the unit price of the transaction
	private double unitPrice;
	// the number of units of the transaction
	private double numUnits;

	/**
	 * Constructors
	 */
	/**
	 * @param date
	 * @param investorName
	 * @param TransactionType
	 * @param target
	 * @param unitPrice
	 * @param numUnits
	 */
	public TransactionNode(long date, String investorName, String TransactionType, String target,
			double unitPrice, double numUnits) {
		this.date = date;
		this.investorName = investorName;
		this.TransactionType = TransactionType;
		this.target = target;
		this.unitPrice = unitPrice;
		this.numUnits = numUnits;
	}

	/**
	 * Public Methods
	 */
	@Override
	public String toString() {
		return "Date: " + this.date + "\nInvestor: " + this.investorName + "\nType: "
				+ this.TransactionType + "\nTarget: " + this.target + "\n#Units: " + this.numUnits
				+ "\nUnit Price: " + this.unitPrice;
	}

	/**
	 * Getters and setters
	 */
	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}

	/**
	 * @return the investorName
	 */
	public String getInvestorName() {
		return investorName;
	}

	/**
	 * @param investorName the investorName to set
	 */
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	/**
	 * @return the TransactionType
	 */
	public String getTransactionType() {
		return TransactionType;
	}

	/**
	 * @param TransactionType the TransactionType to set
	 */
	public void setTransactionType(String TransactionType) {
		this.TransactionType = TransactionType;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the unitPrice
	 */
	public double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the numUnits
	 */
	public double getNumUnits() {
		return numUnits;
	}

	/**
	 * @param numUnits the numUnits to set
	 */
	public void setNumUnits(double numUnits) {
		this.numUnits = numUnits;
	}
}
