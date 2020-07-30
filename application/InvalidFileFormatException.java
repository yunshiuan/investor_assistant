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

@SuppressWarnings("serial")
/**
 * The class for the exception of invalid file format.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200730
 *
 */
public class InvalidFileFormatException extends Exception {
	/**
	 * default no-arg constructor
	 */
	public InvalidFileFormatException() {
	}

	/**
	 * This constructor is provided to allow user to include a message
	 * 
	 * @param msg Additional message for this exception
	 */
	public InvalidFileFormatException(String msg) {
		super(msg);
	}
}
