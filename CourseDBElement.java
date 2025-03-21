package courseDatabase;

public class CourseDBElement {
    private String id;
    private int crn;
    private int credits;
    private String roomNum;
    private String instructor;

    // Constructor to initialize course details
    public CourseDBElement(String id, int crn, int credits, String roomNum, String instructor) {
        this.id = id;
        this.crn = crn;
        this.credits = credits;
        this.roomNum = roomNum;
        this.instructor = instructor;
    }

    // Getter for course ID
    public String getId() {
        return id;
    }

    // Getter for CRN (Course Registration Number)
    public int getCRN() {
        return crn;
    }

    // Getter for course credits
    public int getCredits() {
        return credits;
    }

    // Getter for room number
    public String getRoomNum() {
        return roomNum;
    }

    // Getter for instructor name
    public String getInstructor() {
        return instructor;
    }

    // Override toString method to provide a string representation of the course
    @Override
    public String toString() {
        return "Course:" + id + " CRN:" + crn + " Credits:" + credits + " Instructor:" + instructor + " Room:" + roomNum;
    }

    // Override equals method to compare CourseDBElement objects based on CRN
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Same object, return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Null or different class, return false
        }
        CourseDBElement that = (CourseDBElement) obj;
        return crn == that.crn; // Compare based on CRN (assuming it's unique)
    }

    // Override hashCode method to generate hash code based on CRN
    @Override
    public int hashCode() {
        return Integer.hashCode(crn); // Use CRN to generate the hash code
    }
}
