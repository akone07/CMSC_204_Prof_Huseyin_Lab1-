package courseDatabase;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourseDBManagerTest {
    private CourseDBManagerInterface dataMgr = new CourseDBManager();

    @Before
    public void setUp() throws Exception {
        dataMgr = new CourseDBManager();
    }

    @After
    public void tearDown() throws Exception {
        dataMgr = null;
    }

    @Test
    public void testAddToDB() {
        try {
            dataMgr.add("CMSC203", 30504, 4, "SC450", "Joey Bag-O-Donuts");
        } catch (Exception e) {
            fail("This should not have caused an Exception");
        }
    }

    @Test
    public void testShowAll() {
        dataMgr.add("CMSC203", 30504, 4, "SC450", "Joey Bag-O-Donuts");
        dataMgr.add("CMSC203", 30503, 4, "SC450", "Jill B. Who-Dunit");
        dataMgr.add("CMSC204", 30559, 4, "SC450", "BillyBob Jones");
        
        String[] list = dataMgr.showAll();

        // Check that the course details match
        assertTrue(list[0].contains("CMSC203"));
        assertTrue(list[1].contains("CMSC203"));
        assertTrue(list[2].contains("CMSC204"));
    }

    @Test
    public void testRead() {
        try {
            // Create a file to simulate reading data from a file
            File inputFile = new File("Test1.txt");
            PrintWriter inFile = new PrintWriter(inputFile);
            inFile.println("CMSC203 30504 4 SC450 Joey Bag-O-Donuts");
            inFile.println("CMSC204 30503 4 SC450 Jill B. Who-Dunit");
            inFile.close();

            // Call readFile to add courses to the manager
            dataMgr.readFile(inputFile);

            // Test the courses added from the file
            assertEquals("CMSC203", dataMgr.get(30504).getId());
            assertEquals("CMSC204", dataMgr.get(30503).getId());
            assertEquals("SC450", dataMgr.get(30503).getRoomNum());
        } catch (Exception e) {
            fail("Should not have thrown an exception");
        }
    }
}
