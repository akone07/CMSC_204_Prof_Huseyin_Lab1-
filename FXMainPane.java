package courseDatabase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;

public class FXMainPane extends VBox {
    private RadioButton courseFile, courseArray;
    Button inputButton, dbButton, showDB, getCourse, clear, exit, findCourseButton;
    Label label1 = new Label("Course ID");
    Label label2 = new Label("CRN");
    Label label3 = new Label("Credits");
    Label label4 = new Label("Room Number");
    Label label5 = new Label("Instructor");
    Label label6 = new Label("CRN to retrieve");

    private File inputFile;
    ToggleGroup group;
    TextField textfield1, textfield2, textfield3, textfield4, textfield5, textfield6;
    TextArea courseDisplayArea;  // TextArea to display courses

    HBox hBox1, hBox2, hBox3;
    VBox vBox1, vBox2, vBox3, vBox4, vBox5, vBox6;

    CourseDBManager dataMgr;

    public void createRadioButtons() {
        HBox radioPanel = new HBox();
        courseFile = new RadioButton("Create Database from File");
        courseArray = new RadioButton("Add to Database from Fields");
        group = new ToggleGroup();
        courseFile.setToggleGroup(group);
        courseArray.setToggleGroup(group);
        courseFile.setSelected(true);

        dbButton.setDisable(true);
        showDB.setDisable(true);
        getCourse.setDisable(true);
        findCourseButton.setDisable(true);

        courseFile.setOnAction(new RadioListener());
        courseArray.setOnAction(new RadioListener());

        radioPanel.getChildren().addAll(courseFile, courseArray);
        TitledPane radioTitlePane = new TitledPane("Please Select from Following Options:", radioPanel);
        radioTitlePane.setCollapsible(false);

        getChildren().addAll(radioTitlePane);
    }

    private class RadioListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            boolean fileSelected = courseFile.isSelected();
            inputButton.setDisable(!fileSelected);
            dbButton.setDisable(fileSelected);
            textfield1.setDisable(fileSelected);
            textfield2.setDisable(fileSelected);
            textfield3.setDisable(fileSelected);
            textfield4.setDisable(fileSelected);
            textfield5.setDisable(fileSelected);
        }
    }

    FXMainPane() {
        inputButton = new Button("Input File");
        dbButton = new Button("Add to DB");
        showDB = new Button("Show DB");
        getCourse = new Button("Get Course");
        clear = new Button("Clear");
        exit = new Button("Exit");
        findCourseButton = new Button("Find Course");

        hBox1 = new HBox();
        hBox2 = new HBox();
        hBox3 = new HBox();

        vBox1 = new VBox();
        vBox2 = new VBox();
        vBox3 = new VBox();
        vBox4 = new VBox();
        vBox5 = new VBox();
        vBox6 = new VBox();

        textfield1 = new TextField();
        textfield2 = new TextField();
        textfield3 = new TextField();
        textfield4 = new TextField();
        textfield5 = new TextField();
        textfield6 = new TextField();

        courseDisplayArea = new TextArea();  // Initialize the TextArea
        courseDisplayArea.setEditable(false);  // Make it non-editable
        courseDisplayArea.setPrefHeight(300);  // Set the preferred height for the TextArea

        inputButton.setOnAction(new ButtonHandler());
        dbButton.setOnAction(new ButtonHandler());
        showDB.setOnAction(new ButtonHandler());
        getCourse.setOnAction(new ButtonHandler());
        findCourseButton.setOnAction(new ButtonHandler());
        clear.setOnAction(new ButtonHandler());
        exit.setOnAction(new ButtonHandler());

        dataMgr = new CourseDBManager();
        createRadioButtons();

        hBox1.getChildren().addAll(vBox1, vBox2, vBox3, vBox4, vBox5);
        vBox1.getChildren().addAll(label1, textfield1);
        vBox2.getChildren().addAll(label2, textfield2);
        vBox3.getChildren().addAll(label3, textfield3);
        vBox4.getChildren().addAll(label4, textfield4);
        vBox5.getChildren().addAll(label5, textfield5);
        vBox6.getChildren().addAll(label6, textfield6, findCourseButton);

        hBox2.getChildren().addAll(inputButton, dbButton, showDB, getCourse, clear, exit);
        hBox3.getChildren().addAll(vBox6, courseDisplayArea);  // Add the TextArea here

        getChildren().addAll(hBox1, hBox2, hBox3);

        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox3.setAlignment(Pos.CENTER);

        Insets inset = new Insets(10);
        hBox1.setPadding(inset);
        hBox2.setPadding(inset);
        hBox3.setPadding(inset);
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
                courseDisplayArea.clear();  // Clear the TextArea when clearing fields
            } else if (event.getTarget() == inputButton) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Choose a file to read from");
                inputFile = chooser.showOpenDialog(null);

                if (inputFile != null && inputFile.canRead()) {
                    try {
                        dataMgr.readFile(inputFile);
                        showDB.setDisable(false);
                        getCourse.setDisable(false);
                    } catch (FileNotFoundException e) {
                        alert.setContentText("File not found.");
                        alert.show();
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
                courseDisplayArea.appendText("Course added: " + id + ", CRN: " + crn + "\n");

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
                courseDisplayArea.setText(courses.toString());
            } else if (event.getTarget() == getCourse) {
                // Get course by CRN
                int crn = Integer.parseInt(textfield6.getText());
                CourseDBElement course = dataMgr.get(crn);

                if (course != null) {
                    courseDisplayArea.setText(course.toString());
                } else {
                    courseDisplayArea.setText("Course with CRN " + crn + " not found.");
                }
            }
        }
    }
}

