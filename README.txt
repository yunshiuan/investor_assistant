based on:
../a2/

-----------------
application
-----------------
- the package that could be opened by Eclipse and run to launch the program.
- based on the design proposal:
  - refer to googledrive/PhD program/UW-Madison/Courses/2020 Summer/CS 400/final project/a1

-----------------
screenshots
-----------------
- the screenshots of the program

-----------------
data
-----------------
- the data read by the program.

-----------------
output
-----------------
- the data output by the program.

-----------------
manifest.txt
-----------------
- the file required for creating JAR file.
- https://canvas.wisc.edu/courses/202692/pages/create-a-java-archive-jar

-----------------
executable.jar
-----------------
- created based on manifest.txt
- https://canvas.wisc.edu/courses/202692/pages/create-a-java-archive-jar
- commands:
  jar -cfm executable.jar manifest.txt .
  java --module-path "/Users/vimchiz/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar

-----------------
upload/
-----------------
- contains the files for assignment submission
