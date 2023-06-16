import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Main_ScheduleArranger {

    public static void main(String[] args) {

        
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        JOptionPane.showMessageDialog(dialog, "Welcome to the Schedule Arranger Software!");

        WebScraping.logInPage();

        String fileText = "";
        int noOfCourses = 0;
        
        userInputLoop: do {
            String addAnotherCourse = JOptionPane.showInputDialog(dialog, "Would you like to add a course?\n" + "Type:\n"
                            + "0 to Terminate.\n" + "1 to Continue with selected courses.\n"
                            + "2 to Add a course.");

            switch (addAnotherCourse) {
                case "0":
                    System.exit(0);

                case "1":
                    System.out.println("\n\n");
                    CourseInfo.writeToFile(fileText);
                    WebScraping.initCourseProcessing();
                    break userInputLoop;

                case "2":
                    WebScraping.searchAddCoursesPage();

                    if (noOfCourses != 0) {
                        fileText += "\n";
                    }
                    fileText += WebScraping.courseDetailsPage();

                    break;

                default:
                    JOptionPane.showMessageDialog(dialog, "Invalid Input. Try Again.");
                    noOfCourses--;
            }

            noOfCourses++;

        } while (noOfCourses < 7);

        if (noOfCourses >= 7) {
            JOptionPane.showMessageDialog(dialog,
                    "You've reached the maximum number of courses you can add or the maximum number of inputs");
            CourseInfo.writeToFile(fileText);
            WebScraping.initCourseProcessing();
        }

    }
}
