/**
 * This is the program for the assignment a3.
 * 
 * @project a3 Milestone 3: Final Project
 * @course CS400
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.eduF
 * @date 20200730
 * @attribution 
 * - based on  my assignment "a1 Milestone1: Design" and "a2 Milestone 2: UI"
 * JavaFX-related
 * - pie chart: 
 * -- https://docs.oracle.com/javafx/2/charts/pie-chart.htm
 * -- change pie label: https://stackoverflow.com/questions/35479375/display-additional-values-in-pie-chart
 * - JavaFX Overview: http://tutorials.jenkov.com/javafx/overview.html
 * - JavaFX css reference guide: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 * - padding: https://stackoverflow.com/questions/38528328/how-to-only-change-left-padding-in-javafx-css
 * - Event handler: 
 * -- open a new window: https://o7planning.org/en/11533/opening-a-new-window-in-javafx
 * -- encapsulate the handlers: https://stackoverflow.com/questions/51534680/eventhandler-in-a-separate-class
 * - file chooser:
 * -- https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 * -- set initial directory: https://stackoverflow.com/questions/44003330/set-programs-directory-as-the-initial-directory-of-javafx-filechooser
 * - Enable css in new windows: https://stackoverflow.com/questions/36295482/javafx-css-not-loading-when-opening-new-window
 * - Styling Charts with CSS: https://docs.oracle.com/javafx/2/charts/css-styles.htm
 * - Read in csv filse: https://stackabuse.com/reading-and-writing-csvs-in-java/
 * - DataBinding:
 * -- https://stackoverflow.com/questions/13227809/displaying-changing-values-in-javafx-label
 * -- using StringProperty: https://softwareengineering.stackexchange.com/questions/367463/javafx-is-there-difference-between-string-and-stringproperty-in-model-classes
 * Others
 * - Self-defined exceptions: p3/KeyNotFoundException.java
 */
package application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200714
 *
 */
public class Main extends Application {

	/**
	 * Constants
	 */
	// the window size
	private static final double SIZE_FACTOR = (double) 35 / 30;
	private static final double WINDOW_WIDTH = 26 * 30 * SIZE_FACTOR;
	private static final double WINDOW_HEIGHT = 15 * 30 * SIZE_FACTOR;
	private static final double SECOND_WINDOW_WIDTH = 400 * SIZE_FACTOR;
	private static final double SECOND_WINDOW_HEIGHT = 300 * SIZE_FACTOR;
	private static final double OFFSET_X_SECOND_WINDOW = 200 * SIZE_FACTOR;
	private static final double OFFSET_Y_SECOND_WINDOW = 100 * SIZE_FACTOR;
	private static final double THIRD_WINDOW_WIDTH = 400 * SIZE_FACTOR * 0.5;
	private static final double THIRD_WINDOW_HEIGHT = 300 * SIZE_FACTOR * 0.5;
	private static final double OFFSET_X_THIRD_WINDOW = 200 * SIZE_FACTOR;
	private static final double OFFSET_Y_THIRD_WINDOW = 100 * SIZE_FACTOR;
	private static final String FILE_CSS = "application/application.css";

	// the app title
	private static final String APP_TITLE = "Best Investment Assistant for Joint Account Holders";
//	 the title in the top panel
	private static final String TOP_TITLE = APP_TITLE;
	// the labels for the "add a transaction" window
	private static final String[] ADD_TRANS_LABELS = { "Transaction Date", "Transaction Type",
			"Investor Name", "Target Name", "Unit Price", "# of Units" };
	// the prompts for the "add a transaction" window
	private static final String[] ADD_TRANS_PROMPT = { "20200714", "buy", "Andy", "VTI", "155.13",
			"4.5" };

	/**
	 * Private fields
	 */
	private Recorder myRecorder = new Recorder();
	// store any command-line arguments that were entered.
	// -NOTE: this.getParameters().getRaw() will get these also
	// could set the argument in "Run Configuration>Arguments>Program Arguments"
	// private List<String> args;

	// store the pie chart data as a class field so that it will get updated
	// whenever the underlying data has changed
	private HashMap<String, ObservableList<PieChart.Data>> tablePieChartData = new HashMap<String, ObservableList<PieChart.Data>>();

	// store the pie charts to make synchronization between charts and underlying
	// data easier
	private HashMap<String, PieChart> tablePieCharts = new HashMap<String, PieChart>();

	/**
	 * Design the GUI. Inherit from the class Application.
	 * 
	 * @param primaryWindow the stage for the GUI.
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryWindow) throws Exception {
//		// save args example
//		args = this.getParameters().getRaw();
		/*
		 * Initialize the recorder
		 */
		// create investors
		// - load in demo investor data
		myRecorder.initializeFromFiles();
//		myRecorder.initializeDemo();

//		myRecorder.showAllTransactions();
//		myRecorder.showInvestorTransactions("Amy");
//		myRecorder.showAllTargetsInfo();

		/**
		 * Define the border pane
		 */
		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		/**
		 * Create the scene based on the border pane
		 */
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		/**
		 * Add css
		 */
		mainScene.getStylesheets().add(FILE_CSS);

//		/**
//		 * Bottom panel: Only for developing use. Show console output.
//		 */
//		TextArea textArea = new TextArea();
//		HBox boxTextArea = new HBox(textArea);
//		boxTextArea.getStyleClass().add("text-main-window-title");		
//        textArea.setText("abc");
//		root.setBottom(boxTextArea);
		/**
		 * Top panel: title
		 */
		Label label = new Label(TOP_TITLE);
		HBox boxLabel = new HBox(label);
		boxLabel.getStyleClass().add("text-main-window-title");
		// center the box
		boxLabel.setAlignment(Pos.CENTER);
		root.setTop(boxLabel);

		/**
		 * Left panel: investors portfolios
		 */
		// the box to contain multiple investors
		HBox boxInvestors = new HBox();
		boxInvestors.getStyleClass().add("box-investors");

		// create portfolio data for each investor's the pie chart
		updateAllPortfolioChartData();

		// put investor information into the box
		Set<String> namesInvestors = myRecorder.getTableInvestors().keySet();
		int indexInvestor = 0;
		for (String thisInvestor : namesInvestors) {
			indexInvestor++;
			/**
			 * create a box for each investor - contain a text box and a chart box
			 */
			VBox boxThisInvestor = new VBox();
			// add the box to the parent container
			boxInvestors.getChildren().add(boxThisInvestor);
			// set the styles of the investor's box
			boxThisInvestor.getStyleClass().add("box-one-investor");

			/**
			 * text box at the top
			 */
			VBox boxText = new VBox();
			boxThisInvestor.getChildren().add(boxText);
			boxText.getStyleClass().add("box-investor-text");
			// add the investor information to the box
			Main.investorToText(boxText, myRecorder.getTableInvestors().get(thisInvestor),
					indexInvestor);

			/**
			 * chart box at the bottom
			 */
			VBox boxChart = new VBox();
			boxThisInvestor.getChildren().add(boxChart);
			// create the pie chart
			// - TODO: Should beatify the chart.
			// -- (1) Show the unit and percentage besides each slide in the
			// - TODO: Should update the pie chart once the portfolio changes
			// -- (1) (V) when importing data
			// -- (2) when adding one transaction record
			// -- (3) (V) the price gets updated
			// TODO: ensure the color binds with specific target across investors' charts

			AnnotatedPieChart chart = new AnnotatedPieChart(
					this.tablePieChartData.get(thisInvestor));
			// hide the legend
			chart.setLegendVisible(false);
			chart.setTitle("Portfolio");
			this.tablePieCharts.put(thisInvestor, chart);
			boxChart.getStyleClass().add("box-chart-portfolio");
			boxChart.getChildren().add(this.tablePieCharts.get(thisInvestor));
		}

		// place the box for investors
		boxInvestors.setAlignment(Pos.CENTER);
		root.setLeft(boxInvestors);

		/**
		 * Right panel: a column of menu buttons
		 */
		VBox boxMenuButtons = new VBox();
		boxMenuButtons.getStyleClass().add("box-menu-buttons");
		Button buttonImport = new Button("Import Data");
		Button buttonAddTrans = new Button("Add a \nTransaction");
		Button buttonShowTransactions = new Button("Show \nTransactions");
		Button buttonWriteSummary = new Button("Write \nSummary");
		Button buttonUpdatePrice = new Button("Update Price");

		// collect the buttons to a list
		Button menuButtonsList[] = { buttonImport, buttonAddTrans, buttonShowTransactions,
				buttonWriteSummary, buttonUpdatePrice };
		// add styled buttons to the box
		for (int indexButton = 0; indexButton < menuButtonsList.length; indexButton++) {
			// set the styles of the buttons
			menuButtonsList[indexButton].getStyleClass().add("menu-button");
			// add buttons to the box
			boxMenuButtons.getChildren().add(menuButtonsList[indexButton]);
		}
		// place the box
		boxMenuButtons.setAlignment(Pos.CENTER);
		root.setRight(boxMenuButtons);

		/**
		 * Event Handlers
		 */
		// the "import data" button
		buttonImport.setOnAction(new ImportDataButtonHandler(primaryWindow));
		// the "add a transaction" button
		buttonAddTrans.setOnAction(new AddTransactionButtonHandler(primaryWindow));
		// the "show transactions" button
		buttonShowTransactions.setOnAction(new ShowTransactionsButtonHandler(primaryWindow));
		// the "write summary" button
		buttonWriteSummary.setOnAction(new WriteSummaryButtomHandler(primaryWindow));
		// the "update price" button
		buttonUpdatePrice.setOnAction(new UpdatePriceButtomHandler(primaryWindow));
		/**
		 * Define the stage based on the scene
		 */
		// Add the stuff and set the primary stage
		primaryWindow.setTitle(APP_TITLE);
		primaryWindow.setScene(mainScene);
		primaryWindow.show();
	}

	/**
	 * Launch the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Private helper functions
	 */
	/**
	 * Add an investor information to a box as "Text" nodes of the children of the
	 * box.
	 * 
	 * @param box      the box to contain the investor's information
	 * @param investor an investor object
	 * @param index    the index of the investor
	 * @return the box that contains the added information
	 */
	private static Pane investorToText(Pane box, Investor investor, int index) {
		Text numberInvestor = new Text("Investor " + index);
		numberInvestor.getStyleClass().add("text-investor-title");

		Text nameInvestor = new Text("Name: " + investor.getName());
		Text currentBalance = new Text("Current Balance: $"
				+ ((double) Math.round(investor.getCurrentBalance() * 100) / 100));
		// enable data binding
		currentBalance.textProperty().bind(investor.getStringCurrentBalance());
		Text ratioInvestor = new Text("Targte Stock/Bond Ratio: " + investor.getTargetRatio());
		Text returnInvestor = new Text("Return of Investment: " + investor.getRateReturn() + "%");
		box.getChildren().add(numberInvestor);
		box.getChildren().add(nameInvestor);
		box.getChildren().add(currentBalance);
		box.getChildren().add(ratioInvestor);
		box.getChildren().add(returnInvestor);
		return box;
	}

	/**
	 * Update one investor's pie chart data based on the portfolio. Should call
	 * updatePieCharts() afterwards to actually update the corresponding pie chart.
	 * 
	 * @param investorName the investor's name of interest
	 * @return ObservableList<PieChart.Data> that will be used by the constructor
	 *         PieChart().
	 */
	private void updateOnePortfolioChartData(String investorName) {
		// get the current number of units
		HashMap<String, Double> portfolio = myRecorder.getTableInvestors().get(investorName)
				.getPortfolio();
		// get the target names
		Set<String> namesTargets = portfolio.keySet();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		// add portfolio information to the list
		for (String thisTarget : namesTargets) {
			// multiple the number of units by the current unit price
			double targetAsset = portfolio.get(thisTarget)
					* myRecorder.getTableTargets().get(thisTarget).getCurrentPrice();
			pieChartData.add(new PieChart.Data(thisTarget, targetAsset));
		}
		this.tablePieChartData.put(investorName, pieChartData);
	}

	/**
	 * Update all investors' pie chart data based on the portfolios. Should call
	 * updatePieCharts() afterwards to actually update the corresponding pie charts.
	 */
	private void updateAllPortfolioChartData() {
		Set<String> investorNames = myRecorder.getTableInvestors().keySet();
		for (String investorName : investorNames) {
			updateOnePortfolioChartData(investorName);
		}
	}

	/**
	 * Update one pie chart based on 'tablePieChartData'.
	 */
	public void updateOnePieChart(String investorName) {
		this.tablePieCharts.get(investorName).setData(this.tablePieChartData.get(investorName));
	}

	/**
	 * Update all pie charts based on 'tablePieChartData'.
	 */
	private void updateAllPieCharts() {
		// iterate over investors
		Set<String> investorNames = myRecorder.getTableInvestors().keySet();
		for (String investorName : investorNames) {
			this.tablePieCharts.get(investorName).setData(this.tablePieChartData.get(investorName));
		}
	}

	/**
	 * Helper classes
	 */
	/**
	 * The class for annotated pie chart that provides detailed label for each slice
	 * of pie.
	 * 
	 * @author Chuang, Yun-Shiuan (Sean)
	 * @email ychuang26@wisc.edu
	 * @date 20200801
	 *
	 */
	private class AnnotatedPieChart extends PieChart {

		/**
		 * Constructor
		 */
		AnnotatedPieChart(ObservableList<Data> data) {
			super(data);
		}

		/**
		 * Mark each slice of pie with detailed labels.
		 */
		@Override
		protected void layoutChartChildren(double top, double left, double contentWidth,
				double contentHeight) {
			if (getLabelsVisible()) {
				// get the sum of the total asset
				double sumBalance = 0;
				for (Data d : getData()) {
					sumBalance += d.getPieValue();
				}
				final double SUM_BALANCE = sumBalance;
				// update the pie lab
				// TODO: Should place the labels above the chart. Probably relocate the labels
				getData().forEach(d -> {
					Optional<Node> opTextNode = this.lookupAll(".chart-pie-label").stream().filter(
							n -> n instanceof Text && ((Text) n).getText().contains(d.getName()))
							.findAny();
					if (opTextNode.isPresent()) {

						((Text) opTextNode.get()).setText(d.getName() + "\n$"
								+ Math.round(d.getPieValue()) + "\n("
								+ (double) Math.round((d.getPieValue() * 1000) / SUM_BALANCE) / 10
								+ "%)");
					}
				});
			}
			super.layoutChartChildren(top, left, contentWidth, contentHeight);
		}
	};

	/**
	 * Event Handlers
	 */
	/**
	 * The event handler for the 'import data' button.
	 * 
	 * @author Chuang, Yun-Shiuan (Sean)
	 * @email ychuang26@wisc.edu
	 * @date 20200801
	 */
	private class ImportDataButtonHandler implements EventHandler<ActionEvent> {
		private final Stage primaryWindow;
		private File externalFile;

		public ImportDataButtonHandler(Stage primaryWindow) {
			this.primaryWindow = primaryWindow;
		}

		@Override
		public void handle(ActionEvent arg0) {

			// define the new main window
			BorderPane secondRoot = new BorderPane();

			/**
			 * Top panel: add the title
			 */
			// the text to show on the new window
			Text secondTitle = new Text("Please select the the file of transactions to import."
					+ "\n\nThe file should be in the correct format, e.g., 'transaction_record_20200801.csv'. ");

			secondTitle.wrappingWidthProperty().set(SECOND_WINDOW_WIDTH);
			HBox boxTitle = new HBox(secondTitle);
			boxTitle.getStyleClass().add("text-new-window-title");
			boxTitle.setAlignment(Pos.CENTER);
			secondRoot.setTop(boxTitle);

			/**
			 * Bottom panel: a Confirm and a Cancel button
			 */
			HBox boxButton = new HBox();
			Button buttonBrowse = new Button("Browse");
			Button buttonConfirm = new Button("Confirm");
			Button buttonCancel = new Button("Cancel");
			boxButton.getChildren().add(buttonBrowse);
			boxButton.getChildren().add(buttonConfirm);
			boxButton.getChildren().add(buttonCancel);

			// center the box
			boxButton.setAlignment(Pos.CENTER);
			secondRoot.setBottom(boxButton);

			/**
			 * Create the scene for the new window
			 */
			Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH, SECOND_WINDOW_HEIGHT);
			secondScene.getStylesheets().add(FILE_CSS);

			// create the stage for the new window
			Stage secondWindow = new Stage();
			secondWindow.setTitle("Import Data");
			secondWindow.setScene(secondScene);

			// set position of the new window, related to primary window.
			secondWindow.setX(this.primaryWindow.getX() + OFFSET_X_SECOND_WINDOW);
			secondWindow.setY(this.primaryWindow.getY() + OFFSET_Y_SECOND_WINDOW);
			secondWindow.show();

			/**
			 * Secondary Event handlers
			 */
			buttonBrowse.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					externalFile = Recorder.fileChooser.showOpenDialog(secondWindow);
				}
			});
			buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					// handle the case that the user confirm without browsing
					if (externalFile == null) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText(
								"Please select a file that contains the transaction records,"
										+ " e.g., 'transaction_record_20200801.csv'.");
						alert.show();
						return;
					}

					// import the transaction records
					// - should handle the case when it fails (e.g., invalid file format)
					try {
						myRecorder.importRecordData(externalFile.getPath());
						// update the pie charts accordingly
						updateAllPortfolioChartData();
						updateAllPieCharts();
						// show success
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText(
								"You have successfully imported the transaction records based the file:\n"
										+ externalFile.getPath()
										+ "\n\nThe dashboard has been updated accordinly.");
						alert.show();
						secondWindow.close();
					} catch (FailedReadingFileException error) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Failed to read the file."
								+ " Please check that the file is in the coorect format."
								+ " For the correct format, see 'data/transaction_record_20200730.csv'"
								+ "\n\nError Message:" + error.getMessage());
						alert.show();
					}
				}
			});
			buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					secondWindow.close();
				}
			});
		}
	}

	/**
	 * The event handler for the 'add a transaction' button.
	 * 
	 * @author Chuang, Yun-Shiuan (Sean)
	 * @email ychuang26@wisc.edu
	 * @date 20200801
	 */
	private class AddTransactionButtonHandler implements EventHandler<ActionEvent> {
		private final Stage primaryWindow;

		public AddTransactionButtonHandler(Stage primaryWindow) {
			this.primaryWindow = primaryWindow;
		}

		@Override
		public void handle(ActionEvent event) {

			// define the new main window
			BorderPane secondRoot = new BorderPane();

			/**
			 * Top panel: add the title
			 */
			// the text to show on the new window
			Text secondTitle = new Text("Type in the tractions you want to add");

			secondTitle.wrappingWidthProperty().set(SECOND_WINDOW_WIDTH);
			HBox boxTitle = new HBox(secondTitle);
			boxTitle.getStyleClass().add("text-new-window-title");
			boxTitle.setAlignment(Pos.CENTER);
			secondRoot.setTop(boxTitle);

			/**
			 * Bottom panel: a Confirm and a Cancel button
			 */
			HBox boxButton = new HBox();
			Button buttonConfirm = new Button("Confirm");
			Button buttonCancel = new Button("Cancel");
			boxButton.getChildren().add(buttonConfirm);
			boxButton.getChildren().add(buttonCancel);

			// center the box
			boxButton.setAlignment(Pos.CENTER);
			secondRoot.setBottom(boxButton);

			/**
			 * Center panel: text fields
			 */
			VBox boxTextFields = new VBox();
			// the input text fields
			ArrayList<TextField> listFields = new ArrayList<TextField>(ADD_TRANS_LABELS.length);
			for (int indexField = 0; indexField < ADD_TRANS_LABELS.length; indexField++) {
				HBox box = new HBox();
				boxTextFields.getChildren().add(box);
				TextField textField = new TextField();
				textField.setPromptText(ADD_TRANS_PROMPT[indexField]);
				box.getChildren().add(new Label(ADD_TRANS_LABELS[indexField]));
				box.getChildren().add(textField);
				box.setAlignment(Pos.CENTER);
				// save the text fields so I could get the values later in the event handler
				listFields.add(textField);
			}

			// center the text fields
			boxTextFields.setAlignment(Pos.CENTER);
			secondRoot.setCenter(boxTextFields);
			/**
			 * Create the scene for the new window
			 */
			Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH, SECOND_WINDOW_HEIGHT);
			secondScene.getStylesheets().add(FILE_CSS);

			// create the stage for the new window
			Stage secondWindow = new Stage();
			secondWindow.setTitle("Add a transaction");
			secondWindow.setScene(secondScene);

			// set position of the new window, related to primary window.
			secondWindow.setX(primaryWindow.getX() + OFFSET_X_SECOND_WINDOW);
			secondWindow.setY(primaryWindow.getY() + OFFSET_Y_SECOND_WINDOW);
			secondWindow.show();
			/**
			 * Secondary Event handlers
			 */
			buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					String investorName = "";
					String target = "";
					try {
						long date = Long.valueOf(listFields.get(0).getText());
						String transactionType = listFields.get(1).getText();
						investorName = listFields.get(2).getText();
						target = listFields.get(3).getText();
						Double unitPrice = Double.valueOf(listFields.get(4).getText());
						Double numUnits = Double.valueOf(listFields.get(5).getText());
						myRecorder.addTransaction(date, investorName, transactionType, target,
								unitPrice, numUnits);
						updateOnePortfolioChartData(investorName);
						updateAllPieCharts();
						// show success
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("You have successfully added the record. "
								+ "The dashboard has been updated accordingly.");
						alert.show();
						secondWindow.close();
					} catch (NumberFormatException error) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText(
								"Invalid input value. Please check the date, number of units, and unit price you typed are valid numbers.\n"
										+ "\n\nError message: " + error.getMessage());
						alert.show();
						return;
					} catch (NonExistentInvestorException error) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						// TODO: Should not hard-code 'Amy' and 'Andy' here
						alert.setContentText("The investor name you typed " + "'" + investorName
								+ "'"
								+ " is not present in this recorder. Shold be 'Amy' or 'Andy'."
								+ "\n\nError message: " + error.getMessage());
						alert.show();
						return;
					} catch (NonExistentTargetException error) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						// TODO: should not hard-code
						alert.setContentText("The target " + target
								+ " is not present in the recorder. "
								+ "Should be one of the following: 'VTI','VWO','VPL','VWO','IEI','BWX'."
								+ "\n\nError message: " + error.getMessage());
						alert.show();
						return;
					} catch (IllegalArgumentException error) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Error message: " + error.getMessage());
						alert.show();
						return;
					}
				}
			});
			buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					secondWindow.close();
				}
			});
		}
	}

	/**
	 * The event handler for the 'show transactions' button.
	 * 
	 * @author Chuang, Yun-Shiuan (Sean)
	 * @email ychuang26@wisc.edu
	 * @date 20200801
	 */
	private class ShowTransactionsButtonHandler implements EventHandler<ActionEvent> {
		private final Stage primaryWindow;

		public ShowTransactionsButtonHandler(Stage primaryWindow) {
			this.primaryWindow = primaryWindow;
		}

		@Override
		public void handle(ActionEvent event) {

			// define the new main window
			BorderPane secondRoot = new BorderPane();

			/**
			 * Top panel: choose the investor
			 */
			VBox boxChooseInvestor = new VBox();
			secondRoot.setTop(boxChooseInvestor);
			// label
			Text titleChooseInvestor = new Text(
					"Which investor's transactions do you want to see?");
			titleChooseInvestor.wrappingWidthProperty().set(SECOND_WINDOW_WIDTH);

			HBox boxTitleChooseInvestor = new HBox(titleChooseInvestor);
			boxTitleChooseInvestor.getStyleClass().add("text-new-window-title");
			boxTitleChooseInvestor.setAlignment(Pos.CENTER);
			boxChooseInvestor.getChildren().add(boxTitleChooseInvestor);

			// combo box to select the investor of interest
			ObservableList<String> listInvestors = FXCollections.observableArrayList();
			listInvestors.add("All Investors");
			Set<String> namesInvestors = myRecorder.getTableInvestors().keySet();
			for (String nameInvestor : namesInvestors) {
				listInvestors.add(nameInvestor);
			}
			ComboBox<String> comboBox = new ComboBox<String>(listInvestors);
			comboBox.setPromptText("Select...");

			HBox boxComboChooseInvestor = new HBox(comboBox);
			boxComboChooseInvestor.setAlignment(Pos.CENTER);
			boxChooseInvestor.getChildren().add(boxComboChooseInvestor);

			/**
			 * Bottom panel: a Confirm and a Cancel button
			 */
			HBox boxButton = new HBox();
			Button buttonConfirm = new Button("Confirm");
			Button buttonCancel = new Button("Cancel");
			boxButton.getChildren().add(buttonConfirm);
			boxButton.getChildren().add(buttonCancel);

			// center the box
			boxButton.setAlignment(Pos.CENTER);
			secondRoot.setBottom(boxButton);

			/**
			 * Create the scene for the new window
			 */
			Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH, SECOND_WINDOW_HEIGHT);
			secondScene.getStylesheets().add(FILE_CSS);

			// create the stage for the new window
			Stage secondWindow = new Stage();
			secondWindow.setTitle("Show transactions");
			secondWindow.setScene(secondScene);

			// set position of the new window, related to primary window.
			secondWindow.setX(primaryWindow.getX() + OFFSET_X_SECOND_WINDOW);
			secondWindow.setY(primaryWindow.getY() + OFFSET_Y_SECOND_WINDOW);
			secondWindow.show();
			/**
			 * Event handlers
			 */
			buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {

					// Read in the investor name and do the filtering
					String investorName = comboBox.getValue();
					// The string of transactions to show to the text area
					String stringTransactions = "";
					// if nothing is selected
					if (investorName == null) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Please choose the investor!");
						alert.show();
						return;
					}
					if (investorName.equals("All Investors")) {
						stringTransactions += myRecorder.showAllTransactions("program");
						// for developing
						// myRecorder.showAllTransactions("console");
					} else {
						stringTransactions += myRecorder.showInvestorTransactions(investorName,
								"program");
						// for developing
						// myRecorder.showInvestorTransactions(investorName, "console");
					}

					// create the pane
					BorderPane thirdLayout = new BorderPane();

					// create the window
					Scene thirdScene = new Scene(thirdLayout, THIRD_WINDOW_WIDTH * 3,
							THIRD_WINDOW_HEIGHT * 3);
					thirdScene.getStylesheets().add(FILE_CSS);
					Stage thirdWindow = new Stage();
					thirdWindow.setTitle("View Transactions");
					thirdWindow.setScene(thirdScene);
					// position the window
					thirdWindow.setX(primaryWindow.getX() + OFFSET_X_THIRD_WINDOW);
					thirdWindow.setY(primaryWindow.getY() + OFFSET_Y_THIRD_WINDOW);

					/**
					 * top panel: title
					 */
					// create the title
					Label thirdLabel = new Label("See below for the transactions: " + investorName);
					HBox boxTitle = new HBox(thirdLabel);
					boxTitle.getStyleClass().add("text-new-window-title");
					boxTitle.setAlignment(Pos.CENTER);
					thirdLayout.setTop(boxTitle);

					/**
					 * center panel: text area
					 */
					TextArea textArea = new TextArea();
					VBox boxText = new VBox(textArea);
					boxText.setAlignment(Pos.CENTER);
					thirdLayout.setCenter(boxText);
					textArea.setText(stringTransactions);
					textArea.setPrefHeight(THIRD_WINDOW_WIDTH * 2);
					thirdWindow.show();
				}
			});
			buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					secondWindow.close();
				}
			});
		}
	};

	/**
	 * The event handler for the 'write summary' button.
	 * 
	 * @author Chuang, Yun-Shiuan (Sean)
	 * @email ychuang26@wisc.edu
	 * @date 20200801
	 */
	private class WriteSummaryButtomHandler implements EventHandler<ActionEvent> {
		private final Stage primaryWindow;

		public WriteSummaryButtomHandler(Stage primaryWindow) {
			this.primaryWindow = primaryWindow;
		}

		@Override
		public void handle(ActionEvent event) {

			// define the new main window
			BorderPane secondRoot = new BorderPane();

			/**
			 * Top panel: add the title
			 */
			// the text to show on the new window
			Text secondTitle = new Text(
					"Browse the directory to choose where the file should be written to.");
			secondTitle.wrappingWidthProperty().set(SECOND_WINDOW_WIDTH);

			HBox boxTitle = new HBox(secondTitle);
			boxTitle.getStyleClass().add("text-new-window-title");
			boxTitle.setAlignment(Pos.CENTER);
			secondRoot.setTop(boxTitle);

			/**
			 * Bottom panel: a Confirm and a Cancel button
			 */
			HBox boxButton = new HBox();
			Button buttonBrowse = new Button("Browse");
			Button buttonConfirm = new Button("Confirm");
			Button buttonCancel = new Button("Cancel");
			boxButton.getChildren().add(buttonBrowse);
			boxButton.getChildren().add(buttonConfirm);
			boxButton.getChildren().add(buttonCancel);

			// center the box
			boxButton.setAlignment(Pos.CENTER);
			secondRoot.setBottom(boxButton);

			/**
			 * Create the scene for the new window
			 */
			Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH, SECOND_WINDOW_HEIGHT);
			secondScene.getStylesheets().add(FILE_CSS);

			// create the stage for the new window
			Stage secondWindow = new Stage();
			secondWindow.setTitle("Write Summary");
			secondWindow.setScene(secondScene);

			// set position of the new window, related to primary window.
			secondWindow.setX(primaryWindow.getX() + OFFSET_X_SECOND_WINDOW);
			secondWindow.setY(primaryWindow.getY() + OFFSET_Y_SECOND_WINDOW);
			secondWindow.show();
			/**
			 * Event handlers
			 */
			buttonBrowse.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					// TODO: should actually import the data
					File file = Recorder.fileChooser.showOpenDialog(secondWindow);
				}
			});
			buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {

					Label thirdLabel = new Label(
							"Sorry! The functionality is still under construction.");

					StackPane thirdLayout = new StackPane();
					thirdLayout.getChildren().add(thirdLabel);

					Scene thirdScene = new Scene(thirdLayout, THIRD_WINDOW_WIDTH,
							THIRD_WINDOW_HEIGHT);

					Stage secondWindow = new Stage();
					secondWindow.setTitle("Under Construction.");
					secondWindow.setScene(thirdScene);

					secondWindow.setX(primaryWindow.getX() + OFFSET_X_THIRD_WINDOW);
					secondWindow.setY(primaryWindow.getY() + OFFSET_Y_THIRD_WINDOW);

					secondWindow.show();
				}
			});
			buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					secondWindow.close();
				}
			});
		}
	};

	/**
	 * The event handler for the 'update price' button.
	 * 
	 * @author Chuang, Yun-Shiuan (Sean)
	 * @email ychuang26@wisc.edu
	 * @date 20200801
	 */
	private class UpdatePriceButtomHandler implements EventHandler<ActionEvent> {
		private final Stage primaryWindow;
		private File externalFile;

		public UpdatePriceButtomHandler(Stage primaryWindow) {
			this.primaryWindow = primaryWindow;
		}

		@Override
		public void handle(ActionEvent event) {

			// define the new main window
			BorderPane secondRoot = new BorderPane();

			/**
			 * Top panel: add the title
			 */
			// the text to show on the new window
			Text secondTitle = new Text(
					"Please select the file that contains the current target price."
							+ " \n\nThe file should be in the correct format,"
							+ " e.g., 'transaction_record_20200801.csv'.");

			secondTitle.wrappingWidthProperty().set(SECOND_WINDOW_WIDTH);

			HBox boxTitle = new HBox(secondTitle);
			boxTitle.getStyleClass().add("text-new-window-title");
			boxTitle.setAlignment(Pos.CENTER);
			secondRoot.setTop(boxTitle);

			/**
			 * Bottom panel: a Confirm and a Cancel button
			 */
			HBox boxButton = new HBox();
			Button buttonBrowse = new Button("Browse");
			Button buttonConfirm = new Button("Confirm");
			Button buttonCancel = new Button("Cancel");
			boxButton.getChildren().add(buttonBrowse);
			boxButton.getChildren().add(buttonConfirm);
			boxButton.getChildren().add(buttonCancel);

			// center the box
			boxButton.setAlignment(Pos.CENTER);
			secondRoot.setBottom(boxButton);

			/**
			 * Create the scene for the new window
			 */
			Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH, SECOND_WINDOW_HEIGHT);
			secondScene.getStylesheets().add(FILE_CSS);

			// create the stage for the new window
			Stage secondWindow = new Stage();
			secondWindow.setTitle("Update Price");
			secondWindow.setScene(secondScene);

			// set position of the new window, related to primary window.
			secondWindow.setX(primaryWindow.getX() + OFFSET_X_SECOND_WINDOW);
			secondWindow.setY(primaryWindow.getY() + OFFSET_Y_SECOND_WINDOW);
			secondWindow.show();
			/**
			 * Event handlers
			 */
			buttonBrowse.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					externalFile = Recorder.fileChooser.showOpenDialog(secondWindow);
				}
			});
			buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					// handle the case that the user confirm without browsing
					if (externalFile == null) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText(
								"Please select a file that contains the transaction records,"
										+ " e.g., 'transaction_record_20200801.csv'.");
						alert.show();
						return;
					}

					// update the target info
					// - should handle the case when it fails (e.g., invalid file format)
					try {
						myRecorder.updateTargetInfo(externalFile.getPath());
						updateAllPortfolioChartData();
						updateAllPieCharts();
						// show success
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText(
								"You have successfully updated the price based the file:\n"
										+ externalFile.getPath()
										+ "\n\nThe dashboard has been updated accordinly.");
						alert.show();
						secondWindow.close();

					} catch (FailedReadingFileException error) {
						// show an error window
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Failed to read the file."
								+ " Please check that the file is in the coorect format."
								+ " For the correct format, see 'data/target_info_20200731.csv'"
								+ "\n\nError Message:" + error.getMessage());
						alert.show();
					}
				}
			});
			buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					secondWindow.close();
				}
			});
		}

	};
}