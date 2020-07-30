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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * The class to read external files.
 * 
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200730
 *
 */
public class MyFileReader {
	/**
	 * Public Methods
	 */
	/**
	 * import the file of transactions that is downloaded from a specified brokerage
	 * company
	 * 
	 * @param fileName
	 * @return a linked list of transaction nodes in the csv file
	 * @throws IOException
	 * @throws InvalidFileFormatException
	 */
	public static LinkedList<TransactionNode> readTransactionFile(String fileName)
			throws IOException, InvalidFileFormatException {
		File csvFile = new File(fileName);
		// read the file only if it exists
		if (csvFile.isFile()) {
			// the row in the csv file
			String row = null;
			int indexRow = 0;
			// create BufferedReader and read data from csv
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
				// read the rest of the lines
				LinkedList<TransactionNode> newNodes = new LinkedList<TransactionNode>();
				while ((row = csvReader.readLine()) != null) {
					String[] data = row.split(",");

					// check the header at the first line (header)
					if (indexRow == 0) {
						// check if the column names
						if (!(data[0].equals("target") && data[1].equals("date")
								&& data[2].equals("unitPrice") && data[3].equals("investorName")
								&& data[4].equals("numUnits")
								&& data[5].equals("transactionType"))) {

							throw new InvalidFileFormatException(
									"The column names of the file is invalid.");
						}
						indexRow++;
						continue;
					}
					// for the rest of the rows
					// - create the transaction node for each row
					String target = data[0];
					long date = Long.valueOf(data[1]);
					double unitPrice = Double.valueOf(data[2]);
					String investorName = data[3];
					double numUnits = Double.valueOf(data[4]);
					String transactionType = data[5];
					// add the row data to the list
					TransactionNode node = new TransactionNode(date, investorName, transactionType,
							target, unitPrice, numUnits);
					newNodes.add(node);
					indexRow++;
				}
				// close the reader after reading the whole file
				csvReader.close();
				// return the new nodes
				return newNodes;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException("IO Exception occured while reading the file at line "
						+ String.valueOf(indexRow) + " in: " + fileName);
			} catch (NumberFormatException e) {
				throw new InvalidFileFormatException(
						"The format of the file at line " + String.valueOf(indexRow)
								+ " is invalid,i.e.," + row + " in: " + fileName);
			}
		} else

		{
			throw new FileNotFoundException(
					"The file you attempted to read does not exist or is not a valid file: "
							+ fileName);
		}
	}

	/**
	 * Read a file that contains the investment target information (e.g., price,
	 * type)
	 * 
	 * @param fileName
	 * @return true if succeeded and false if failed
	 */
//	public static boolean readTargetInfoFile(String fileName) {
//
//	}

}
