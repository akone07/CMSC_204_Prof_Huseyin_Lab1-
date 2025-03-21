package courseDatabase;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseDBManager_STUDENT_Test {

    @Test
    void testAddCourse() {
        CourseDBManager manager = new CourseDBManager();
        
        // Add course
        manager.add("CS102", 12345, 3, "Room 101", "Dr. Smith");
        
        // Retrieve course by CRN
        CourseDBElement course = manager.get(12345);
        
        // Assert that the course ID is "CS102"
        assertNotNull(course);
        assertEquals("CS102", course.getId());
    }

    @Test
    void testAddDuplicateCourse() {
        CourseDBManager manager = new CourseDBManager();

        // Add course
        manager.add("CS102", 12345, 3, "Room 101", "Dr. Smith");

        // Try to add the same course again
        manager.add("CS102", 12345, 3, "Room 102", "Dr. Johnson");

        // Retrieve course by CRN
        CourseDBElement course = manager.get(12345);
        
        // Ensure that only one instance exists
        assertNotNull(course);
        assertEquals("CS102", course.getId());  // The course ID should match
        assertEquals(1, manager.showAll().length);  // Only one course should exist in the database
    }

    @Test
    void testGetCourseByCRN() {
        CourseDBManager manager = new CourseDBManager();
        
        // Add course
        manager.add("CS102", 12345, 3, "Room 101", "Dr. Smith");
        
        // Get course by CRN
        CourseDBElement course = manager.get(12345);
        
        // Assert that the course ID is "CS102"
        assertNotNull(course);
        assertEquals("CS102", course.getId());
    }

    @Test
    void testShowAllCourses() {
        CourseDBManager manager = new CourseDBManager();
        
        // Add courses
        manager.add("CS102", 12345, 3, "Room 101", "Dr. Smith");
        manager.add("CS103", 12346, 4, "Room 102", "Dr. Johnson");
        
        // Show all courses
        String[] courses = manager.showAll();
        
        // Assert that two courses are shown
        assertEquals(2, courses.length);
        assertTrue(courses[0].contains("CS102"));
        assertTrue(courses[1].contains("CS103"));
    }
}
