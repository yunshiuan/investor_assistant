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
 * The class to store the information of each target that is specific to an
 * investor.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200806
 *
 */
public class PortfolioNode {
	/**
	 * Private fields
	 */
	// the number of units of this target the investor has
	private Double numUnits;
	// the average cost
	private Double averageCost;
	// rate of return (%)
	private Double rateReturn;

	/**
	 * Constructors
	 */
	public PortfolioNode(Double numUnits, Double averageCost, Double rateReturn) {
		this.numUnits = numUnits;
		this.averageCost = averageCost;
		this.rateReturn = rateReturn;
	}

	public PortfolioNode(Double numUnits) {
		this.numUnits = numUnits;
		this.averageCost = (double) 0;
		this.rateReturn = (double) 0;
	}

	/**
	 * Public Methods
	 */
	/**
	 * @return the numUnits
	 */
	public Double getNumUnits() {
		return numUnits;
	}

	/**
	 * @param numUnits the numUnits to set
	 */
	public void setNumUnits(Double numUnits) {
		this.numUnits = numUnits;
	}

	/**
	 * @return the averageCost
	 */
	public Double getAverageCost() {
		return averageCost;
	}

	/**
	 * @param averageCost the averageCost to set
	 */
	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}

	/**
	 * @return the rateReturn
	 */
	public Double getRateReturn() {
		return rateReturn;
	}

	/**
	 * @param rateReturn the rateReturn to set
	 */
	public void setRateReturn(Double rateReturn) {
		this.rateReturn = rateReturn;
	}

}
