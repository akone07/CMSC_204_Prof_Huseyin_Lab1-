package courseDatabase;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.File;
import java.io.FileNotFoundException;

public class MainApp extends Application {

    private CourseDBManager dataMgr;
    private File inputFile;
    private TextField textfield1, textfield2, textfield3, textfield4, textfield5, textfield6;
    private Button inputButton, dbButton, showDB, getCourse, clear, exitButton;  // Added exitButton

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        dataMgr = new CourseDBManager();

        // Setting up the GUI components
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 600, 400);

        // Creating text fields for course information
        textfield1 = new TextField();
        textfield1.setPromptText("Course ID");
        textfield2 = new TextField();
        textfield2.setPromptText("CRN");
        textfield3 = new TextField();
        textfield3.setPromptText("Credits");
        textfield4 = new TextField();
        textfield4.setPromptText("Room Number");
        textfield5 = new TextField();
        textfield5.setPromptText("Instructor");
        textfield6 = new TextField();
        textfield6.setPromptText("CRN to Retrieve");

        // Buttons
        inputButton = new Button("Load File");
        dbButton = new Button("Add Course");
        showDB = new Button("Show All Courses");
        getCourse = new Button("Find Course");
        clear = new Button("Clear");
        exitButton = new Button("Exit");  // Exit button added

        // Adding buttons and text fields to the layout
        layout.getChildren().addAll(textfield1, textfield2, textfield3, textfield4, textfield5, textfield6,
                inputButton, dbButton, showDB, getCourse, clear, exitButton);  // Added exitButton

        // Disabling buttons initially
        dbButton.setDisable(true);
        showDB.setDisable(true);
        getCourse.setDisable(true);

        // Event handler for buttons
        ButtonHandler handler = new ButtonHandler();
        inputButton.setOnAction(handler);
        dbButton.setOnAction(handler);
        showDB.setOnAction(handler);
        getCourse.setOnAction(handler);
        clear.setOnAction(handler);
        exitButton.setOnAction(handler);  // Added event handler for exitButton

        // Set up the stage
        primaryStage.setTitle("Course Database");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event.getTarget() == clear) {
                textfield1.clear();
                textfield2.clear();
                textfield3.clear();
                textfield4.clear();
                textfield5.clear();
                textfield6.clear();
            } else if (event.getTarget() == inputButton) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Choose a file to read from");
                inputFile = chooser.showOpenDialog(null);

                if (inputFile != null && inputFile.canRead()) {
                    try {
                        dataMgr.readFile(inputFile);

                        // Display a popup after reading the file successfully
                        Alert fileReadAlert = new Alert(AlertType.INFORMATION);
                        fileReadAlert.setTitle("File Read");
                        fileReadAlert.setHeaderText(null);
                        fileReadAlert.setContentText("File read successfully!");
                        fileReadAlert.showAndWait();

                        showDB.setDisable(false);
                        getCourse.setDisable(false);

                    } catch (FileNotFoundException e) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("File Not Found");
                        alert.setHeaderText(null);
                        alert.setContentText("The file could not be found.");
                        alert.showAndWait();
                    }
                }
            } else if (event.getTarget() == dbButton) {
                // Add the course to the database from the fields
                String id = textfield1.getText();
                int crn = Integer.parseInt(textfield2.getText());
                int credits = Integer.parseInt(textfield3.getText());
                String roomNum = textfield4.getText();
                String instructor = textfield5.getText();

                dataMgr.add(id, crn, credits, roomNum, instructor);

                // Popup for successfully added course
                Alert courseAddedAlert = new Alert(AlertType.INFORMATION);
                courseAddedAlert.setTitle("Course Added");
                courseAddedAlert.setHeaderText(null);
                courseAddedAlert.setContentText("Course added successfully: " + id + ", CRN: " + crn);
                courseAddedAlert.showAndWait();

                // Clear the fields after adding
                textfield1.clear();
                textfield2.clear();
                textfield3.clear();
                textfield4.clear();
                textfield5.clear();
            } else if (event.getTarget() == showDB) {
                // Display all courses in the TextArea
                StringBuilder courses = new StringBuilder();
                for (String course : dataMgr.showAll()) {
                    courses.append(course).append("\n");
                }

                // Show all courses in a popup
                Alert showCoursesAlert = new Alert(AlertType.INFORMATION);
                showCoursesAlert.setTitle("All Courses");
                showCoursesAlert.setHeaderText(null);
                showCoursesAlert.setContentText(courses.toString());
                showCoursesAlert.showAndWait();
            } else if (event.getTarget() == getCourse) {
                // Get course by CRN
                int crn = Integer.parseInt(textfield6.getText());
                CourseDBElement course = dataMgr.get(crn);

                if (course != null) {
                    // Show the course in a popup
                    Alert courseFoundAlert = new Alert(AlertType.INFORMATION);
                    courseFoundAlert.setTitle("Course Found");
                    courseFoundAlert.setHeaderText(null);
                    courseFoundAlert.setContentText(course.toString());
                    courseFoundAlert.showAndWait();
                } else {
                    // Display error message if course is not found
                    Alert courseNotFoundAlert = new Alert(AlertType.ERROR);
                    courseNotFoundAlert.setTitle("Course Not Found");
                    courseNotFoundAlert.setHeaderText(null);
                    courseNotFoundAlert.setContentText("Course with CRN " + crn + " not found.");
                    courseNotFoundAlert.showAndWait();
                }
            } else if (event.getTarget() == exitButton) {
                // Exit the application
                System.exit(0);
            }
        }
    }
}
