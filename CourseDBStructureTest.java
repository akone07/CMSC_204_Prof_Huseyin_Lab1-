package courseDatabase;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the test file for the CourseDBManager which is implemented from the
 * CourseDBManagerInterface
 * 
 */
public class CourseDBStructureTest {
    private CourseDBStructure cds, testStructure;

    @Before
    public void setUp() throws Exception {
        cds = new CourseDBStructure(20);
        testStructure = new CourseDBStructure(20);
    }

    @After
    public void tearDown() throws Exception {
        cds = testStructure = null;
    }

    /**
     * Test the tableSize for CourseDBStructures constructed with both constructors
     */
    @Test
    public void testGetTableSize() {
        assertEquals(19, cds.getTableSize());  // This is the size we expect based on constructor
        assertEquals(19, testStructure.getTableSize());
    }

    @Test
    public void testHashTable() throws IOException {

        // Create a course 
        CourseDBElement cde1 = new CourseDBElement("CMSC500", 39999, 4, "SC100", "Nobody InParticular");

        cds.add(cde1);  // Add to data structure
        cds.add(cde1);  // Add it again. The add method should ignore it

        ArrayList<String> courseList = cds.showAll();
        assertTrue(courseList.size() == 1);  // Ensure the course is added only once

        // Create another course
        CourseDBElement cde2 = new CourseDBElement("CMSC600", 4000, 4, "SC100", "Nobody InParticular");

        // Ensure cde1 can be retrieved
        CourseDBElement retrievedCourse1 = cds.get(cde1.getCRN());
        assertNotNull(retrievedCourse1);  // Ensure it is not null
        assertEquals(39999, retrievedCourse1.getCRN());

        // Ensure cde2 is not yet added
        CourseDBElement retrievedCourse2 = cds.get(cde2.getCRN());
        assertNull(retrievedCourse2);  // It should return null since cde2 was not added yet

        // Now add cde2 to the structure
        cds.add(cde2);
        courseList = cds.showAll();
        assertTrue(courseList.size() == 2);  // Ensure cde2 is added correctly

        // Ensure cde2 can be retrieved
        CourseDBElement retrievedCourse2Updated = cds.get(cde2.getCRN());
        assertNotNull(retrievedCourse2Updated);  // Ensure it is not null
        assertEquals(4000, retrievedCourse2Updated.getCRN());

        // Update cde1 with new information (same CRN)
        CourseDBElement cde1Update = new CourseDBElement("CMSC500-updated", 39999, 4, "SC100", "updated");
        cds.add(cde1Update);  // Same CRN, updated information
        courseList = cds.showAll();
        assertTrue(courseList.size() == 2);  // Size should still be 2 (duplicate ignored)

        // Ensure the updated information for cde1 is correct
        CourseDBElement retrievedUpdatedCourse1 = cds.get(cde1Update.getCRN());
        assertNotNull(retrievedUpdatedCourse1);  // Ensure it is not null
        assertEquals(39999, retrievedUpdatedCourse1.getCRN());
       // assertEquals("CMSC500-updated", retrievedUpdatedCourse1.getID());  // Check if updated
        assertEquals("updated", retrievedUpdatedCourse1.getInstructor());  // Check if updated

        // Testing on the testStructure
        testStructure.add(cde1);
        courseList = testStructure.showAll();
        assertTrue(courseList.size() == 1);
    }
}
