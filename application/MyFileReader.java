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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * The class to read and write external files.
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
	 * @throws FileNotFoundException
	 */
	public static LinkedList<TransactionNode> readTransactionFile(String fileName)
			throws IOException, InvalidFileFormatException, FileNotFoundException {
		File csvFile = new File(fileName);

		// read the file only if it exists
		if (csvFile.isFile()) {
			// the row in the csv file
			String row = null;
			int indexRow = 0;
			// create BufferedReader and read data from csv
			BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
			try {
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
					if (!(transactionType.equals("sell") || transactionType.equals("buy"))) {
						throw new InvalidFileFormatException(
								"Unrecognized transaction type" + transactionType);
					}
					// add the row data to the list
					TransactionNode node = new TransactionNode(date, investorName, transactionType,
							target, unitPrice, numUnits);
					newNodes.add(node);
					indexRow++;
				}
				// return the new nodes
				return newNodes;
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("IO Exception occured while reading the file at line "
						+ String.valueOf(indexRow) + " in: " + fileName);
			} catch (NumberFormatException e) {
				throw new InvalidFileFormatException(
						"The format of the file at line " + String.valueOf(indexRow)
								+ " is invalid,i.e.," + row + " in: " + fileName);
			} finally {
				// close the reader
				csvReader.close();
			}
		} else {
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
	 * @throws IOException
	 * @throws InvalidFileFormatException
	 * @throws FileNotFoundException
	 */
	public static HashMap<String, InvestmentTarget> readTargetInfoFile(String fileName)
			throws IOException, InvalidFileFormatException, FileNotFoundException {
		File csvFile = new File(fileName);
		// read the file only if it exists
		if (csvFile.isFile()) {
			// the row in the csv file
			String row = null;
			int indexRow = 0;
			// create BufferedReader and read data from csv
			BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
			try {
				// read the rest of the lines
				HashMap<String, InvestmentTarget> tableTargetInfo = new HashMap<String, InvestmentTarget>();
				while ((row = csvReader.readLine()) != null) {
					String[] data = row.split(",");

					// check the header at the first line (header)
					if (indexRow == 0) {
						// check if the column names
						if (!(data[0].equals("target") && data[1].equals("currentUnitPrice")
								&& data[2].equals("type"))) {
							throw new InvalidFileFormatException(
									"The column names of the file is invalid.");
						}
						indexRow++;
						continue;
					}
					// for the rest of the rows
					// - create the transaction node for each row
					String target = data[0];
					double currentUnitPrice = Double.valueOf(data[1]);
					String type = data[2];

					if (!(type.equals("stock") || type.equals("bond"))) {
						throw new InvalidFileFormatException("Unrecognized target type" + type);
					}
					// add the row data to the hash table
					InvestmentTarget targetInfo = new InvestmentTarget(target, currentUnitPrice,
							type);
					tableTargetInfo.put(target, targetInfo);
					indexRow++;
				}
				// return the table
				return tableTargetInfo;
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("IO Exception occured while reading the file at line "
						+ String.valueOf(indexRow) + " in: " + fileName);
			} catch (NumberFormatException e) {
				throw new InvalidFileFormatException(
						"The format of the file at line " + String.valueOf(indexRow)
								+ " is invalid,i.e.," + row + " in: " + fileName);
			} finally {
				// close the reader
				csvReader.close();
			}
		} else

		{
			throw new FileNotFoundException(
					"The file you attempted to read does not exist or is not a valid file: "
							+ fileName);
		}
	}

	/**
	 * Read a file that contains the investor information (e.g., name, target
	 * ratio). portfolio and rateReturn are initialized to null.
	 * 
	 * @return
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public static HashMap<String, Investor> readInvestorInfoFile(String fileName)
			throws InvalidFileFormatException, IOException {
		File csvFile = new File(fileName);
		// read the file only if it exists
		if (csvFile.isFile()) {
			// the row in the csv file
			String row = null;
			int indexRow = 0;
			// create BufferedReader and read data from csv
			BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
			try {
				// read the rest of the lines
				HashMap<String, Investor> tableInvestorInfo = new HashMap<String, Investor>();
				while ((row = csvReader.readLine()) != null) {
					String[] data = row.split(",");

					// check the header at the first line (header)
					if (indexRow == 0) {
						// check if the column names
						if (!(data[0].equals("name") && data[1].equals("targetRatio"))) {
							throw new InvalidFileFormatException(
									"The column names of the file is invalid.");
						}
						indexRow++;
						continue;
					}
					// for the rest of the rows
					// - create the transaction node for each row
					String name = data[0];
					double targetRatio = Double.valueOf(data[1]);

					// add the row data to the hash table
					Investor investorInfo = new Investor(name, targetRatio);
					tableInvestorInfo.put(name, investorInfo);
					indexRow++;
				}
				// return the table
				return tableInvestorInfo;
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("IO Exception occured while reading the file at line "
						+ String.valueOf(indexRow) + " in: " + fileName);
			} catch (NumberFormatException e) {
				throw new InvalidFileFormatException(
						"The format of the file at line " + String.valueOf(indexRow)
								+ " is invalid,i.e.," + row + " in: " + fileName);
			} finally {
				// close the reader
				csvReader.close();
			}
		} else

		{
			throw new FileNotFoundException(
					"The file you attempted to read does not exist or is not a valid file: "
							+ fileName);
		}
	}

	/**
	 * Write the transaction records in a given recorder to an external file.
	 * 
	 * @param fileName the file to write to
	 * @param recorder the recorder that contains the transactions
	 * @throws FileNotFoundException
	 */
	public static void writeTransactionToFile(String fileName, Recorder recorder)
			throws FileNotFoundException {

		try (PrintWriter writer = new PrintWriter(new File(fileName))) {
			// the builder to collect the content
			StringBuilder sb = new StringBuilder();

			// get the table of records
			HashMap<String, LinkedList<TransactionNode>> tableRecords = recorder.getTableRecords();

			// add the header
			final String[] HEADER = new String[] { "target", "date", "unitPrice", "investorName",
					"numUnits", "transactionType" };
			for (String varName : HEADER) {
				sb.append(varName);
				// add ',' except for the last var
				if (!varName.equals(HEADER[HEADER.length - 1])) {
					sb.append(",");
				} else {
					// add \n for the last var
					sb.append('\n');
				}
			}
			// add the rows: iterate over each investor's records
			for (String investorName : tableRecords.keySet()) {
				// iterate over each node
				LinkedList<TransactionNode> records = recorder.getTableRecords().get(investorName);
				for (TransactionNode node : records) {
					sb.append(node.getTarget());
					sb.append(",");
					sb.append(node.getDate());
					sb.append(",");
					sb.append(node.getUnitPrice());
					sb.append(",");
					sb.append(node.getInvestorName());
					sb.append(",");
					sb.append(node.getNumUnits());
					sb.append(",");
					sb.append(node.getTransactionType());
					sb.append('\n');
				}
			}
			writer.write(sb.toString());
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(
					"Failed to write the file: '" + fileName + "'." + e.getMessage());
		}

	}

}
