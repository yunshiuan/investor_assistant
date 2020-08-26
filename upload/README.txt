-------------------
sub-directories
-------------------

data/
- the data read by the program.

output/
- the data output by the program.

screenshots/
- the screenshots of the program.

application/
- the package that could be opened by Eclipse and run to launch the program.

-------------------
future works
-------------------
- Allow adding more investors.
- Allow adding more targets.
- Should support 'dividend' as one of the transaction type
- For the button 'show transactions': use tableView to show the transactions instead of a text area.
- Should calculate the average unit cost on a first-one-first-out basis ,i.e., whenever sell, sell the one that was bought earlier). For now, if the investor sells an target with the price higher than the current average unit cost, the average unit cost will decrease.
- Add a 'cost-basis' section. It should show the rate of return of each target.
- Show the current stock/bond ratio and update it dynamically
- Add a 'show all date range' button for 'show transaction'
- Add left margin for secondary windows.

-------------------
known bugs
-------------------

- Pie chart:
  - Adding new transactions sometimes cause the slice color of each target in the pie chart changes. Ideally, the color should bind to a specific target
  - The labels for the targets disappear when the slice becomes too small. Should figure out a way to re-locate the labels so they fit in the box.
  - Some label are covered by the pie chart. The labels should always be above the pie chart.

-------------------
attribution
-------------------

- For my design proposal, please refer to a1.
- based on  my assignment "a1 Milestone1: Design" and "a2 Milestone 2: UI"
 JavaFX-related
- other codes I referred to (but not copied):
 - JavaFX Overview: http://tutorials.jenkov.com/javafx/overview.html
 - pie chart:
   - https://docs.oracle.com/javafx/2/charts/pie-chart.htm
   - change pie label: https://stackoverflow.com/questions/35479375/display-additional-values-in-pie-chart
 - JavaFX css reference guide:
   - https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 - Padding:
   - https://stackoverflow.com/questions/38528328/how-to-only-change-left-padding-in-javafx-css
 - Event handler:
   - open a new window: https://o7planning.org/en/11533/opening-a-new-window-in-javafx
   - encapsulate the handlers: https://stackoverflow.com/questions/51534680/eventhandler-in-a-separate-class
 - File chooser:
   - https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
   - set initial directory: https://stackoverflow.com/questions/44003330/set-programs-directory-as-the-initial-directory-of-javafx-filechooser
   - directory chooser: https://stackoverflow.com/questions/9375938/javafx-filechooser
 - Enable css in new windows: https://stackoverflow.com/questions/36295482/javafx-css-not-loading-when-opening-new-window
 - Styling Charts with CSS: https://docs.oracle.com/javafx/2/charts/css-styles.htm
 - Read in csv filse: https://stackabuse.com/reading-and-writing-csvs-in-java/
 - DataBinding:
   - https://stackoverflow.com/questions/13227809/displaying-changing-values-in-javafx-label
   - using StringProperty: https://softwareengineering.stackexchange.com/questions/367463/javafx-is-there-difference-between-string-and-stringproperty-in-model-classes
 - Date picker:
   - https://www.geeksforgeeks.org/javafx-datepicker-with-examples/
   - set default value: https://stackoverflow.com/questions/36968122/how-to-set-javafx-datepicker-value-correctly
 - Write csv file:
   - https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file
 - Self-defined exceptions: p3/KeyNotFoundException.java
