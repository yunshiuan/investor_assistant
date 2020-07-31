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

import java.util.HashMap;

/**
 * The class to store each investor’s information.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200714
 *
 */
public class Investor {
	/**
	 * Private fields
	 */
	// the inverstor’s name
	private String name;
	// the target stock/(stock+bond) ratio (the proportion of the stock value over
	// the total value)
	private Double targetRatio;
	// rate of return (%)
	private Double rateReturn;
	// the hash table with keys being the investment target name and values being
	// the number of units that the investor has
	private HashMap<String, Double> portfolio;

	/**
	 * Constructors
	 */
	/**
	 * Constructor with name and target ratio.
	 * 
	 * @param name
	 * @param targetRatio
	 */
	public Investor(String name, Double targetRatio) {
		this.setName(name);
		this.setTargetRatio(targetRatio);
	};

	/**
	 * Constructor with name, target ratio, and current rate of return.
	 * 
	 * @param name
	 * @param targetRatio
	 * @param rateReturn
	 */
	public Investor(String name, Double targetRatio, Double rateReturn) {
		this.setName(name);
		this.setTargetRatio(targetRatio);
		this.setRateReturn(rateReturn);
	};

	/**
	 * Constructor with name, target ratio, current rate of return, and current
	 * portfolio.
	 * 
	 * @param name
	 * @param targetRatio
	 * @param rateReturn
	 * @param portfolio
	 */
	public Investor(String name, Double targetRatio, Double rateReturn,
			HashMap<String, Double> portfolio) {
		this.setName(name);
		this.setTargetRatio(targetRatio);
		this.setRateReturn(rateReturn);
		this.setPortfolio(portfolio);
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
	 * @return the targetRatio
	 */
	public Double getTargetRatio() {
		return targetRatio;
	}

	/**
	 * @param targetRatio the targetRatio to set
	 */
	public void setTargetRatio(Double targetRatio) {
		this.targetRatio = targetRatio;
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

	/**
	 * @return the portfolio
	 */
	public HashMap<String, Double> getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio the portfolio to set
	 */
	public void setPortfolio(HashMap<String, Double> portfolio) {
		this.portfolio = portfolio;
	}

}
