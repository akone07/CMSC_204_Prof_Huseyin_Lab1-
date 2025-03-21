package courseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CourseDBStructure {
    private List<CourseDBElement>[] table;
    private int tableSize;

    public CourseDBStructure(int size) {
        // Adjust table size to be prime for better hashing distribution
        tableSize = size - 1;  // Prevent prime numbers for simplicity
        table = new ArrayList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = new ArrayList<>();
        }
    }

    public int getTableSize() {
        return tableSize;
    }

    // Add method - Handles duplicates by updating the existing course if the CRN matches
    public void add(CourseDBElement course) {
        int index = hash(course.getCRN());
        List<CourseDBElement> bucket = table[index];

        // Check if the course with the same CRN already exists, and update it if found
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getCRN() == course.getCRN()) {
                bucket.set(i, course);  // Update the existing course
                return;
            }
        }

        // If course does not exist, add it to the bucket
        bucket.add(course);
    }

    // Retrieve a course by its CRN
    public CourseDBElement get(int crn) {
        int index = hash(crn);
        List<CourseDBElement> bucket = table[index];
        for (CourseDBElement course : bucket) {
            if (course.getCRN() == crn) {
                return course;
            }
        }
        return null;
    }

    // Simple hash function to map CRN to an index in the table
    private int hash(int crn) {
        return crn % tableSize;
    }

    // Show all courses stored in the structure
    public ArrayList<String> showAll() {
        ArrayList<String> courseList = new ArrayList<>();
        for (List<CourseDBElement> bucket : table) {
            for (CourseDBElement course : bucket) {
                courseList.add(course.toString());
            }
        }
        return courseList;
    }
}
