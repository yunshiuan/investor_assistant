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
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class to store each investor’s information.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200714
 *
 */
public class Investor {
//	public class User{
//	    private StringProperty nameProperty = new StringProperty();
//
//	    public final String getName() {
//	        return nameProperty.get();
//	    }
//
//	    public final void setName(String name) {
//	        nameProperty.set(name);
//	    }
//
//	    // expose the property for data binding
//	    // – possibly use a read-only interface
//	    public StringProperty nameProperty() {
//	        return nameProperty;
//	    }
//
//	    public User(String name) {
//	        nameProperty.set(name);
//	    }
//	}	
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
	// the current total balance
	private Double currentBalance;
	// the property to facilitate data binding
	private StringProperty stringCurrentBalance = new SimpleStringProperty();

	// the hash table with keys being the investment target name and values being
	// the information of each target specific to the investor (e.g., number of
	// units, average cost, rate of return)
	// - note that strictly speaking, the number of units is not portfolio.
	// Portfolio is a set of financial assets (not number of units). In order to
	// compute the portfolio, The number of units should be multiplied by unit
	// price, which is stored in
	// 'InvestmentTarget'.
	private HashMap<String, PortfolioNode> portfolio;

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
			HashMap<String, PortfolioNode> portfolio) {
		this.setName(name);
		this.setTargetRatio(targetRatio);
		this.setRateReturn(rateReturn);
		this.setPortfolio(portfolio);
	}

	/**
	 * Public Methods
	 */
	/**
	 * Compute the investor's current balance based on the current price of targets.
	 * 
	 * @param tableTagetsInfo the table that contain the current price of each
	 *                        target
	 */
	public void computeCurrentBalance(HashMap<String, InvestmentTarget> tableTagetsInfo) {
		// iterate over the investor's all targets
		Set<String> targetNames = this.portfolio.keySet();
		double currentBalance = 0;
		for (String targetName : targetNames) {
			currentBalance += (this.portfolio.get(targetName).getNumUnits()
					* tableTagetsInfo.get(targetName).getCurrentPrice());
		}
		// set both the string and the corresponding string property
		this.setCurrentBalance(currentBalance);
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
	public HashMap<String, PortfolioNode> getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio the portfolio to set
	 */
	public void setPortfolio(HashMap<String, PortfolioNode> portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @return the currentBalance
	 */
	public Double getCurrentBalance() {
		return this.currentBalance;
	}

	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
		// update the corresponding property as well
		// - format the string
		this.stringCurrentBalance.set(
				"Current Balance: $" + ((double) Math.round(this.getCurrentBalance() * 100) / 100));
	}

	/**
	 * @return the stringCurrentBalance
	 */
	public StringProperty getStringCurrentBalance() {
		return this.stringCurrentBalance;
	}

}
