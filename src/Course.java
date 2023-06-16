import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Course {

    private String code;

    private String[] sections;
    public static byte numOfCourses = 0;

    Course(){           //default constructor
        this.code ="";
    }
    Course(String cd){ 
        this.code =cd;
        numOfCourses++;
        setSectionTime();

    }
    public void setSectionTime(){
        /*
         This method reads a file where the times of each section are stored and assign each section to its times
         */
        try {
            File file = new File("src\\objects_file.txt");


            Scanner scanner = new Scanner(file);
            String line = "";
            byte start = 0;
            while (scanner.hasNextLine()) { // The condition is wether the file has a next line or not
                line = scanner.nextLine();
                if (line.startsWith(this.code)) {  // Checks the equality of the course code with the one in the file
                    start = (byte) line.indexOf("   ");
                    this.sections = line.substring(start + 3).split("\\|"); // if found it will take the times and assign them to "sections"
                    return;  // to terminate the method if found 
                }
            }                           
                throw new Exception();      // if it went through all the times but didn't find the course then it will throw an exception


        }
        catch(FileNotFoundException fnfe){
            System.out.println("The file containing the needed data was bot found");
        }
        catch (Exception e){
            System.out.println("The Course you are looking for was not found");
        }
    }
    public String[] getSections() {     // returns the sections to implement the processes needed
        return ScheduleTools.filter(this.sections,this.code);
    }

}
