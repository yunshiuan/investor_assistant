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
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200715
 *
 */
public class TransactionNode {
	/**
	 * Private fields
	 */
	//the date of the transaction	
	private long date;
	//the name of the investor
	private String investorName;
	//”sell”,”buy”,”dividend”	
	private String transactionType;
	//the name of the investment target
	private String target;
	//the unit price of the transaction
	private double unitPrice;
	//the number of units of the transaction
	private double numUnits;
	
	/**
	 * Constructors
	 */	
	/**
	 * @param date
	 * @param investorName
	 * @param transactionType
	 * @param target
	 * @param unitPrice
	 * @param numUnits
	 */
	public TransactionNode(long date, String investorName, String transactionType, String target,
			double unitPrice, double numUnits) {
		this.date = date;
		this.investorName = investorName;
		this.transactionType = transactionType;
		this.target = target;
		this.unitPrice = unitPrice;
		this.numUnits = numUnits;
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
	 * @return the transactionType
	 */
	public String gettransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void settransactionType(String transactionType) {
		this.transactionType = transactionType;
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
