package courseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class CourseDBManager implements CourseDBManagerInterface {
    private ArrayList<CourseDBElement> courseList;

    public CourseDBManager() {
        courseList = new ArrayList<>();
    }

    @Override
    public void add(String id, int crn, int credits, String roomNum, String instructor) {
        // Create a new course element
        CourseDBElement newCourse = new CourseDBElement(id, crn, credits, roomNum, instructor);

        // Check if the course already exists in the list (based on CRN)
        if (!courseList.contains(newCourse)) {
            // If the course is not already in the list, add it
            courseList.add(newCourse);
        } else {
            System.out.println("Course with CRN " + crn + " already exists. Duplicate entry not allowed.");
        }
    }

    @Override
    public CourseDBElement get(int crn) {
        for (CourseDBElement course : courseList) {
            if (course.getCRN() == crn) {
                return course;
            }
        }
        return null;
    }

    @Override
    public void readFile(File input) throws FileNotFoundException {
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue; // Skip empty lines

            // Split by spaces, but we need to handle room number and instructor as one field
            String[] data = line.split("\\s+");

            if (data.length < 5) {
                System.err.println("⚠️ Incorrect format, skipping: " + line);
                continue;
            }

            // Extract fields
            String id = data[0].trim();
            int crn = Integer.parseInt(data[1].trim());
            int credits = Integer.parseInt(data[2].trim());
            String roomNum = data[3].trim();
            String instructor = String.join(" ", java.util.Arrays.copyOfRange(data, 4, data.length));

            add(id, crn, credits, roomNum, instructor);
        }
        scanner.close();
    }

    @Override
    public String[] showAll() {
        ArrayList<String> courseDetails = new ArrayList<>();

        // Sort courses by CRN before converting them to string
        Collections.sort(courseList, Comparator.comparingInt(CourseDBElement::getCRN));

        for (CourseDBElement course : courseList) {
            // Add course information as string to the list
            courseDetails.add(course.toString());  // Assuming toString() gives a useful course representation
        }

        // Convert the ArrayList to a String array
        return courseDetails.toArray(new String[0]);
    }
}
