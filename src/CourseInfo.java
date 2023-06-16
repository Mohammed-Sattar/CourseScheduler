import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CourseInfo {
    
    private String courseID;
    private String courseName;
    private String courseSection;
    private String courseDays;
    private String courseStartTime;
    private String courseEndTime;
    private String courseDays_Times;
    private String labDays;
    private String cLabStartTime;
    private String cLabEndTime;
    private String labDays_Times;


    public CourseInfo () {
        this.cLabStartTime = null;
        this.cLabEndTime = null;
    }

    public CourseInfo (String courseID, String courseName, String courseSection, String courseDays, String courseStartTime, String courseEndTime, String courseDays_Times) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseSection = courseSection;
        this.courseDays = courseDays;
        this.courseStartTime = timeFormatter(courseStartTime);
        this.courseEndTime = timeFormatter(courseEndTime);
        this.courseDays_Times = courseDays_Times;
    }

    public void setCourseID (String courseID) {
        this.courseID = courseID;
    }
    public String getCourseID () {
        return this.courseID;
    }

    public void setCourseName (String courseName) {
        this.courseName = courseName;
    }
    public String getCourseName () {
        return this.courseName;
    }

    public void setCourseSection (String courseSection) {
        this.courseSection = courseSection;
    }
    public String getCourseSection () {
        return this.courseSection;
    }

    public void setCourseDays (String courseDays) {
        this.courseDays = courseDays;
    }
    public String getCourseDays () {
        return this.courseDays;
    }

    public void setCourseStartTime (String courseStartTime) {
        this.courseStartTime = timeFormatter(courseStartTime);
    }
    public String getCourseStartTime () {
        return this.courseStartTime;
    }

    public void setCourseEndTime (String courseEndTime) {
        this.courseEndTime = timeFormatter(courseEndTime);
    }
    public String getCourseEndTime () {
        return this.courseEndTime;
    }

    public void setCourseDays_Times (String courseDays_Times) {
        this.courseDays_Times = courseDays_Times;
    }
    public String getCourseDays_Times () {
        return this.courseDays_Times;
    }

    public void setLabDays (String labDays) {
        this.labDays = labDays;
    }
    public String getLabDays () {
        return this.labDays;
    }

    public void setLabStartTime (String cLabStartTime) {
        this.cLabStartTime = timeFormatter(cLabStartTime);
    }
    public String getLabStartTime () {
        return this.cLabStartTime;
    }

    public void setLabEndTime (String cLabEndTime) {
        this.cLabEndTime = timeFormatter(cLabEndTime);
    }
    public String getLabEndTime () {
        return this.cLabEndTime;
    }
    
    public void setLabDays_Times (String labDays_Times) {
        this.labDays_Times = labDays_Times;
    }
    public String getLabDays_Times () {
        return this.labDays_Times;
    }


    private static String timeFormatter(String time) { //3:30PM
        time = time.strip();
        time = time.replaceAll("\\s+", "");
        String mins = "";
        
        try {
            mins = time.substring(0, time.indexOf(":"));
        } catch (StringIndexOutOfBoundsException e) {
            return "To Be Announced";
        }
        
        String temp = mins;
            // System.out.println(mins);
            int intMins = Integer.parseInt(mins);

            if (intMins < 10) {
                mins = "0" + mins;
                time = time.replaceFirst(temp, mins);
            }
            String ampm = time.substring(5);
            time = time.replace(ampm, (" " + ampm));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            LocalTime localTime = LocalTime.parse(time, formatter);
            String time24 = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            // System.out.println("Time in 24 hour format is " + time24);
        
        return time24;
    }

    // public void fileWriter () {

    // }
    public static void writeToFile (String text) {
        try {
            File objFile = new File("C:\\Users\\muham\\OneDrive - University of Prince Mugrin\\CS Projects\\CS112 Project\\Course Arranger\\src\\objects_file.txt");
            if (objFile.createNewFile()) { // checking if file news to be created
                System.out.println("File Created: " + objFile.getName());
            } // or already exists
            else {
                // System.out.println("File already exists.");
                objFile.delete();
                objFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

        // Using try-with-resources statement his code creates a new instance of the PrintWriter class each time it writes to the file. The try-with-resources 
        // statement automatically closes the PrintWriter, BufferedWriter, and FileWriter objects when it is done with them.
        try (FileWriter objWriteTxt = new FileWriter("C:\\Users\\muham\\OneDrive - University of Prince Mugrin\\CS Projects\\CS112 Project\\Course Arranger\\src\\objects_file.txt", true);
            BufferedWriter bw = new BufferedWriter(objWriteTxt);
            PrintWriter pw = new PrintWriter(bw);) {
            // The FileWriter class is used to write data into the file. 
            // The BufferedWriter class is used to improve the performance of writing data into the file by buffering the output. 
            // The PrintWriter class is used to write formatted text into the file.
            pw.println(text);
            // pw.println(text.replaceAll("\n", " | "));

            // System.out.println("Succefully wrote to file");
        } catch (IOException e) {
            System.out.println("An error occured while writing to file.");
            e.printStackTrace();
        } 
    }

    public String toString() {

        if (this.getLabStartTime() == null) {
            return "Course ID: " + courseID + "\n" +
                    "Course Name: " + courseName + "\n" +
                    "Section ID: " + courseSection + "\n" +
                    "Days: " + courseDays + "\n" +
                    "Times: " + courseStartTime + " - " + courseEndTime + "\n" +
                    "Lecture Days & Times: " + courseDays_Times;
        }

        return "Course ID: " + courseID + "\n" +
                "Course Name: " + courseName + "\n" +
                "Section ID: " + courseSection + "\n" +
                "Days: " + courseDays + "\n" +
                "Times: " + courseStartTime + " - " + courseEndTime + "\n" +
                "Lecture Days & Times: " + courseDays_Times + "\n" +
                "Lab Start Time: " + cLabStartTime + "\n" +
                "Lab End Time: " + cLabEndTime + "\n" +
                "Lab Days & Times: " + labDays_Times;

    }
}
