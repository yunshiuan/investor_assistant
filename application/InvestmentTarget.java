/**
 * This is the program for the assignment a3.
 * 
 * @project a3 Milestone 3: Final Project
 * @course CS400
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200730
 * @attribution 
 * - see Main.java
 */
package application;

/**
 * The class to store the information of one investment target.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200730
 *
 */
public class InvestmentTarget {
	/**
	 * Private fields
	 */
	// the target name
	private String name;
	// the target current price
	private double currentPrice;
	// type of the target, e.g., ”stock”, “bond”
	private String type;

	/**
	 * Constructors
	 */
	public InvestmentTarget(String name, double currentPrice, String type) {
		this.setName(name);
		this.setCurrentPrice(currentPrice);
		this.setType(type);
	}

	/**
	 * Public Methods
	 */
	@Override
	public String toString() {
		return "Name: " + this.name + "\nCurrent Unit Price: $" + this.currentPrice + "\nType: "
				+ this.type;
	}

	/**
	 * Getters and setters
	 */

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the currentPrice
	 */
	public double getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @param currentPrice the currentPrice to set
	 */
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
