/**
 * This is the program for the assignment a2.
 * 
 * @project a2 Milestone 2: UI
 * @course CS400
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200714
 * @attribution 
 * (1) modified from my assignment "p2 HelloFX "
 * (2) pie chart: https://docs.oracle.com/javafx/2/charts/pie-chart.htm
 * (3) JavaFX Overview: http://tutorials.jenkov.com/javafx/overview.html
 * (4) JavaFX css reference guide: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 * (5) padding: https://stackoverflow.com/questions/38528328/how-to-only-change-left-padding-in-javafx-css
 * (6) Event handler: 
 * (6-1) open a new window: https://o7planning.org/en/11533/opening-a-new-window-in-javafx
 * (6-2) file chooser: https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 * (7) Enable css in new windows: https://stackoverflow.com/questions/36295482/javafx-css-not-loading-when-opening-new-window
 * (8) Styling Charts with CSS: https://docs.oracle.com/javafx/2/charts/css-styles.htm
 */
package application;

import java.io.File;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Chuang, Yun-Shiuan (Sean)
 * @email ychuang26@wisc.edu
 * @date 20200714
 *
 */
public class Main extends Application {

	// store any command-line arguments that were entered.
	// -NOTE: this.getParameters().getRaw() will get these also
	// could set the argument in "Run Configuration>Arguments>Program Arguments"

	private List<String> args;

	/**
	 * Constants
	 */
	// the window size
	private static final int SIZE_FACTOR = 30;
	private static final int WINDOW_WIDTH = 26 * SIZE_FACTOR;
	private static final int WINDOW_HEIGHT = 15 * SIZE_FACTOR;
	private static final int SECOND_WINDOW_WIDTH = (400 / 30) * SIZE_FACTOR;
	private static final int SECOND_WINDOW_HEIGHT = (300 / 30) * SIZE_FACTOR;
	private static final int OFFSET_X_SECOND_WINDOW = (200 / 30) * SIZE_FACTOR;
	private static final int OFFSET_Y_SECOND_WINDOW = (100 / 30) * SIZE_FACTOR;
	private static final int THIRD_WINDOW_WIDTH = (400 / 30) * SIZE_FACTOR * (1 / 2);
	private static final int THIRD_WINDOW_HEIGHT = (300 / 30) * SIZE_FACTOR * (1 / 2);
	private static final int OFFSET_X_THIRD_WINDOW = (200 / 30) * SIZE_FACTOR;
	private static final int OFFSET_Y_THIRD_WINDOW = (100 / 30) * SIZE_FACTOR;

	// the app title
	private static final String APP_TITLE = "Best Investment Assistant for Joint Account Holders";
//	 the title in the top panel
	private static final String TOP_TITLE = APP_TITLE;
	// the labels for the "add a transaction" window
	private static final String[] ADD_TRANS_LABELS = { "Transaction Date", "Transaction Type",
			"Investor Name", "Target Name", "Unit Price", "# of Units" };
	// the prompts for the "add a transaction" window
	private static final String[] ADD_TRANS_PROMPT = { "20200714", "Buying",
			"Andy", "VTI", "155.13", "4.5" };	
	
	/**
	 * Global variables
	 */
	// a file chooser for browsing the file
	private static final FileChooser fileChooser = new FileChooser();
	// the table to contain investors
	private Hashtable<String, Investor> tableInvestors = new Hashtable<String, Investor>();
	// the list to contain the transactions
	// TODO: Should utilize this
	private LinkedList<TransactionNode> records = new LinkedList<TransactionNode>();

	/**
	 * Design the GUI. Inherit from the class Application.
	 * 
	 * @param primaryStage the stage for the GUI.
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

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
		mainScene.getStylesheets().add("application/application.css");

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
		// create investors
		// - load in demo investor data
		tableInvestors = Main.loadDemoInvestors(tableInvestors);
		// - TODO: This is hard-coded for a2. It should be read from input file.
		// - TODO: Make the parameters here as class constants

		// put investor information into the box
		Set<String> namesInvestors = tableInvestors.keySet();
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
			Main.investorToText(boxText, tableInvestors.get(thisInvestor), indexInvestor);

			/**
			 * chart box at the bottom
			 */
			VBox boxChart = new VBox();
			boxThisInvestor.getChildren().add(boxChart);
			// create portfolio data for the pie chart
			// - TODO: Should beatify the chart.
			// -- (1) Show the unit and percentage besides each slide in the pie
			ObservableList<PieChart.Data> pieChartData = Main
					.createPortfolioChartData(tableInvestors.get(thisInvestor));
			// create the pie chart
			// TODO: ensure the color binds with specific target across investors' charts
			PieChart chart = new PieChart(pieChartData);
			chart.setTitle("Portfolio");
			boxChart.getStyleClass().add("box-chart-portfolio");
			boxChart.getChildren().add(chart);
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
		buttonImport.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// define the new main window
				BorderPane secondRoot = new BorderPane();

				/**
				 * Top panel: add the title
				 */
				// the text to show on the new window
				Text secondTitle = new Text(
						"Please select the the file of transactions to import.");

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
				Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH,
						SECOND_WINDOW_HEIGHT);
				secondScene.getStylesheets().add("application/application.css");

				// create the stage for the new window
				Stage newWindow = new Stage();
				newWindow.setTitle("Import Data");
				newWindow.setScene(secondScene);

				// set position of the new window, related to primary window.
				newWindow.setX(primaryStage.getX() + OFFSET_X_SECOND_WINDOW);
				newWindow.setY(primaryStage.getY() + OFFSET_Y_SECOND_WINDOW);
				newWindow.show();

				/**
				 * Event handlers
				 */
				buttonBrowse.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						// TODO: should actually import the data
						File file = fileChooser.showOpenDialog(newWindow);
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

						Stage newWindow = new Stage();
						newWindow.setTitle("Under Construction.");
						newWindow.setScene(thirdScene);

						newWindow.setX(primaryStage.getX() + OFFSET_X_THIRD_WINDOW);
						newWindow.setY(primaryStage.getY() + OFFSET_Y_THIRD_WINDOW);

						newWindow.show();
					}
				});
				buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						newWindow.close();
					}
				});
			}

		});
		// the "add a transaction" button
		buttonAddTrans.setOnAction(new EventHandler<ActionEvent>() {
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
				// the date field
				for (int indexField = 0; indexField < ADD_TRANS_LABELS.length; indexField++) {
					HBox box = new HBox();
					boxTextFields.getChildren().add(box);
					TextField textField = new TextField();
					textField.setPromptText(ADD_TRANS_PROMPT[indexField]);					
					box.getChildren().add(new Label(ADD_TRANS_LABELS[indexField]));
					box.getChildren().add(textField);
					box.setAlignment(Pos.CENTER);
				}

				// center the text fields
				boxTextFields.setAlignment(Pos.CENTER);
				secondRoot.setCenter(boxTextFields);
				/**
				 * Create the scene for the new window
				 */
				Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH,
						SECOND_WINDOW_HEIGHT);
				secondScene.getStylesheets().add("application/application.css");

				// create the stage for the new window
				Stage newWindow = new Stage();
				newWindow.setTitle("Add a transaction");
				newWindow.setScene(secondScene);

				// set position of the new window, related to primary window.
				newWindow.setX(primaryStage.getX() + OFFSET_X_SECOND_WINDOW);
				newWindow.setY(primaryStage.getY() + OFFSET_Y_SECOND_WINDOW);
				newWindow.show();
				/**
				 * Event handlers
				 */
				// TODO: See if I could re-use event handlers
				buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						Label thirdLabel = new Label(
								"Sorry! The functionality is still under construction.");

						StackPane thirdLayout = new StackPane();
						thirdLayout.getChildren().add(thirdLabel);

						Scene thirdScene = new Scene(thirdLayout, THIRD_WINDOW_WIDTH,
								THIRD_WINDOW_HEIGHT);

						Stage newWindow = new Stage();
						newWindow.setTitle("Under Construction.");
						newWindow.setScene(thirdScene);

						newWindow.setX(primaryStage.getX() + OFFSET_X_THIRD_WINDOW);
						newWindow.setY(primaryStage.getY() + OFFSET_Y_THIRD_WINDOW);

						newWindow.show();
					}
				});
				buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						newWindow.close();
					}
				});
			}

		});
		// the "show transactions" button
		buttonShowTransactions.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// define the new main window
				BorderPane secondRoot = new BorderPane();

				/**
				 * Top panel: add the title
				 */
				// the text to show on the new window
				Text secondTitle = new Text("Which investor's transactions do you want to see?");
				secondTitle.wrappingWidthProperty().set(SECOND_WINDOW_WIDTH);

				HBox boxTitle = new HBox(secondTitle);
				boxTitle.getStyleClass().add("text-new-window-title");
				boxTitle.setAlignment(Pos.CENTER);
				secondRoot.setTop(boxTitle);

				/**
				 * Center panel: a combo box
				 */
				ObservableList<String> listInvestors = FXCollections.observableArrayList();
				Set<String> namesInvestors = tableInvestors.keySet();
				for (String nameInvestor : namesInvestors) {
					listInvestors.add(nameInvestor);
				}
				ComboBox<String> comboBox = new ComboBox<String>(listInvestors);
				secondRoot.setCenter(comboBox);

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
				Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH,
						SECOND_WINDOW_HEIGHT);
				secondScene.getStylesheets().add("application/application.css");

				// create the stage for the new window
				Stage newWindow = new Stage();
				newWindow.setTitle("Show transactions");
				newWindow.setScene(secondScene);

				// set position of the new window, related to primary window.
				newWindow.setX(primaryStage.getX() + OFFSET_X_SECOND_WINDOW);
				newWindow.setY(primaryStage.getY() + OFFSET_Y_SECOND_WINDOW);
				newWindow.show();
				/**
				 * Event handlers
				 */
				buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {

						Label thirdLabel = new Label(
								"Sorry! The functionality is still under construction.");

						StackPane thirdLayout = new StackPane();
						thirdLayout.getChildren().add(thirdLabel);

						Scene thirdScene = new Scene(thirdLayout, THIRD_WINDOW_WIDTH,
								THIRD_WINDOW_HEIGHT);

						Stage newWindow = new Stage();
						newWindow.setTitle("Under Construction.");
						newWindow.setScene(thirdScene);

						newWindow.setX(primaryStage.getX() + OFFSET_X_THIRD_WINDOW);
						newWindow.setY(primaryStage.getY() + OFFSET_Y_THIRD_WINDOW);

						newWindow.show();
					}
				});
				buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						newWindow.close();
					}
				});
			}

		});
		// the "write summary" button
		buttonWriteSummary.setOnAction(new EventHandler<ActionEvent>() {
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
				Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH,
						SECOND_WINDOW_HEIGHT);
				secondScene.getStylesheets().add("application/application.css");

				// create the stage for the new window
				Stage newWindow = new Stage();
				newWindow.setTitle("Write Summary");
				newWindow.setScene(secondScene);

				// set position of the new window, related to primary window.
				newWindow.setX(primaryStage.getX() + OFFSET_X_SECOND_WINDOW);
				newWindow.setY(primaryStage.getY() + OFFSET_Y_SECOND_WINDOW);
				newWindow.show();
				/**
				 * Event handlers
				 */
				buttonBrowse.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						// TODO: should actually import the data
						File file = fileChooser.showOpenDialog(newWindow);
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

						Stage newWindow = new Stage();
						newWindow.setTitle("Under Construction.");
						newWindow.setScene(thirdScene);

						newWindow.setX(primaryStage.getX() + OFFSET_X_THIRD_WINDOW);
						newWindow.setY(primaryStage.getY() + OFFSET_Y_THIRD_WINDOW);

						newWindow.show();
					}
				});
				buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						newWindow.close();
					}
				});
			}

		});
		// the "update price" button
		buttonUpdatePrice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// define the new main window
				BorderPane secondRoot = new BorderPane();

				/**
				 * Top panel: add the title
				 */
				// the text to show on the new window
				Text secondTitle = new Text(
						"Please select the file that contains the current target price.");

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
				Scene secondScene = new Scene(secondRoot, SECOND_WINDOW_WIDTH,
						SECOND_WINDOW_HEIGHT);
				secondScene.getStylesheets().add("application/application.css");

				// create the stage for the new window
				Stage newWindow = new Stage();
				newWindow.setTitle("Update Price");
				newWindow.setScene(secondScene);

				// set position of the new window, related to primary window.
				newWindow.setX(primaryStage.getX() + OFFSET_X_SECOND_WINDOW);
				newWindow.setY(primaryStage.getY() + OFFSET_Y_SECOND_WINDOW);
				newWindow.show();
				/**
				 * Event handlers
				 */
				buttonBrowse.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						// TODO: should actually import the data
						File file = fileChooser.showOpenDialog(newWindow);
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

						Stage newWindow = new Stage();
						newWindow.setTitle("Under Construction.");
						newWindow.setScene(thirdScene);

						newWindow.setX(primaryStage.getX() + OFFSET_X_THIRD_WINDOW);
						newWindow.setY(primaryStage.getY() + OFFSET_Y_THIRD_WINDOW);

						newWindow.show();
					}
				});
				buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						newWindow.close();
					}
				});
			}

		});
		/**
		 * Define the stage based on the scene
		 */
		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
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
		Text ratioInvestor = new Text("Targte Stock/Bond Ratio: " + investor.getTargetRatio());
		Text returnInvestor = new Text("Return of Investment: " + investor.getRateReturn() + "%");
		box.getChildren().add(numberInvestor);
		box.getChildren().add(nameInvestor);
		box.getChildren().add(ratioInvestor);
		box.getChildren().add(returnInvestor);
		return box;
	}

	/**
	 * Create the pie chart data for the portfolio pie chart. The data will be used
	 * by the constructor PieChart().
	 * 
	 * @param investor the investor's portfolio
	 * @return ObservableList<PieChart.Data> that will be used by the constructor
	 *         PieChart().
	 */
	private static ObservableList<PieChart.Data> createPortfolioChartData(Investor investor) {
		// get the portfolio
		Hashtable<String, Double> portfolio = investor.getPortfolio();
		// get the target names
		Set<String> namesTargets = portfolio.keySet();
		// the list to return
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		// add portfolio information to the list
		for (String thisTarget : namesTargets) {
			pieChartData.add(new PieChart.Data(thisTarget, portfolio.get(thisTarget)));
		}
		return pieChartData;

	}

	/**
	 * Load in demo investor data.
	 * 
	 * @param tableInvestors the empty table
	 * @return the table filled by the demo data
	 */
	private static Hashtable<String, Investor> loadDemoInvestors(
			Hashtable<String, Investor> tableInvestors) {
		Investor investorA = new Investor("Andy", Double.valueOf(0.8), Double.valueOf(5.5),
				new Hashtable<String, Double>());
		investorA.getPortfolio().put("VTI", 8.2);
		investorA.getPortfolio().put("VGK", 3.2);
		investorA.getPortfolio().put("VWO", 1.4);
		investorA.getPortfolio().put("IEI", 1.2);
		tableInvestors.put(investorA.getName(), investorA);

		Investor investorB = new Investor("Amy", Double.valueOf(0.6), Double.valueOf(1.5),
				new Hashtable<String, Double>());
		investorB.getPortfolio().put("VTI", 2.5);
		investorB.getPortfolio().put("VGK", 1.4);
		investorB.getPortfolio().put("VWO", 5.0);
		investorB.getPortfolio().put("IEI", 3.0);
		tableInvestors.put(investorB.getName(), investorB);
		return tableInvestors;
	}

}