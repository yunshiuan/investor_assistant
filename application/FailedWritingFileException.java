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
 * The class for the exception when failing to write an external file.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200804
 *
 */
@SuppressWarnings("serial")
public class FailedWritingFileException extends Exception {
	/**
	 * default no-arg constructor
	 */
	public FailedWritingFileException() {
	}

	/**
	 * This constructor is provided to allow user to include a message
	 * 
	 * @param msg Additional message for this exception
	 */
	public FailedWritingFileException(String msg) {
		super(msg);
	}
}
