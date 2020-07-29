/**
 * This is the program for the assignment a2.
 * 
 * @project a2 Milestone 2: UI
 * @course CS400
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200714
 * @attribution 
 * - see Main.java
 */
package application;

/**
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
	private String investmentType;
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
	 * @param investmentType
	 * @param target
	 * @param unitPrice
	 * @param numUnits
	 */
	public TransactionNode(long date, String investorName, String investmentType, String target,
			double unitPrice, double numUnits) {
		this.date = date;
		this.investorName = investorName;
		this.investmentType = investmentType;
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
	 * @return the investmentType
	 */
	public String getInvestmentType() {
		return investmentType;
	}
	/**
	 * @param investmentType the investmentType to set
	 */
	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
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
