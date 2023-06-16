import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class WebScraping {


    static JDialog dialog = new JDialog();
    static EdgeOptions headlessOpt = new EdgeOptions();

    static  {
        dialog.setAlwaysOnTop(true);
        headlessOpt.addArguments("headless");
    } 

    final static WebDriver driver = new EdgeDriver(headlessOpt);   // Initializing webdriver object
    static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

    static {
        // Setting the webdriver and the path to it
        System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
    }



    public static void logInPage () {
        int tries = 0; // Counting the number of times the user has tried to log in

        do {

            driver.get("https://sis.upm.edu.sa/psp/ps/?cmd=login"); // telling the driver to open this URL
            System.out.println(driver.getTitle()+ "\n"); // Printing title of webpage to terminal

            final String userID = JOptionPane.showInputDialog(dialog, "Enter your user ID to login:"); // using dialog box to accept user ID input
            WebElement userId_txtbx = driver.findElement(By.id("userid")); // finding the textbox element on wbpage that accepts user ID
            userId_txtbx.sendKeys(userID); // Sending users input to the textbox

            final String userPass = JOptionPane.showInputDialog(dialog, "Enter your account password: ");
            driver.findElement(By.id("pwd")).sendKeys(userPass);

            driver.findElement(By.name("Submit")).click();
            // Finding the log in button and clicking automatically

            tries++;
            if (tries > 3) {
                JOptionPane.showMessageDialog(dialog,
                        "You have to tried to login too many times. Please try restarting the program.");
                System.exit(0);
            } else if (driver.getCurrentUrl().toLowerCase().contains("login")) {
                JOptionPane.showMessageDialog(dialog, "Invalid User ID or Password. Please try again.");
            }

        } while (driver.getCurrentUrl().toLowerCase().contains("login")); // condition checks if user able to login or still on login page

    }

    public static void searchAddCoursesPage () {

        int rowNum = -2;    // counting the row number in the courses table
        int coursesFound = 0; //counting the number of courses found that match users input of course ID
        int courseRow = 0;  // saves the row number that has the course that will be clicked on
        
        driver.get("https://sis.upm.edu.sa/psc/ps/EMPLOYEE/HRMS/c/SA_LEARNER_SERVICES.SSR_SSENRL_CART.GBL?Page=SSR_SSENRL_CART&amp;Action=A");
        driver.findElement(By.id("DERIVED_REGFRM1_SSR_PB_SRCH")).click();
        // finding and clicking on the search button that shows all courses available for registration
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("SSS_CRSPLN_ITEM$scroll$0")));

        WebElement table = driver.findElement(By.id("SSS_CRSPLN_ITEM$scroll$0")); // the ID of the table that has all the courses
        List<WebElement> rows = table.findElements(By.tagName("tr"));  // saving all the rows in the table as a list of WebElements

        final String courseId = JOptionPane.showInputDialog(dialog, "Enter the ID of the course you would like to add:").toUpperCase();

        for (WebElement row : rows) { 
            // Looping through the courses table to search for course ID entered by the user

            if ((row.getText().contains(courseId)) && !(row.getText().contains("Not offered in"))) { // If row contains course ID does then ...
                courseRow = rowNum;
                coursesFound++;
            }
            rowNum++;
        }

        if (coursesFound < 1) {
            JOptionPane.showMessageDialog(dialog, "No course with the given ID was found in your course planner.\n"+
                                                        "Please make sure that this course is available to you and try again.");
            
            searchAddCoursesPage();
        } 

        driver.findElement(By.id("CRSE_TITLE$"+courseRow)).click();  // clicking on the row that has the course ID the user entered
        // courseDetailsPage();
        // return courseId;
    }

    public static String courseDetailsPage () {
        int noOfSections = 0;  //saves the total number of sections available per course
        String courseId, courseName, secId = "";  //saves the course ID & name e.g. MATH 102 - Calculus II, and the section ID
        int idNum;  // a counter variable, used and incremented in a loop to count the number of the element ID

        //save the lecture information for each course section
        String[] secDays;
        String[] secStartTime;
        String[] secEndTime;
        String[] courseDays_Times;

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("View All")));
            driver.findElement(By.linkText("View All")).click();
            // clicks on 'View All' to ensure that all course sections are displayed on page
        } catch (org.openqa.selenium.NoSuchElementException  | org.openqa.selenium.TimeoutException ex) {
            // System.out.println("No Additional Sections!");
        }

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("PSGRIDCOUNTER")));
            // waits until the element is present, then saves the total number of sections
            String secno = driver.findElement(By.className("PSGRIDCOUNTER")).getText();
            secno = secno.substring(secno.indexOf("of ")+3);
            noOfSections = Integer.parseInt(secno);
            // JOptionPane.showMessageDialog(dialog, noOfSections);
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException ex) {
            System.out.println("Error with counting Sections!");
        }

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CLASS_SECTION$" + (noOfSections-1))));
            // ensures all the sections that were counted are visible
        } catch (org.openqa.selenium.NoSuchElementException  | org.openqa.selenium.TimeoutException ex) {
            System.out.println("Error with accessing all sections!");
        }

        // System.out.println("\n");

        courseId = driver.findElement(By.id("DERIVED_CRSECAT_DESCR200")).getText();
        courseId = courseId.substring(0, (courseId.indexOf("-") - 1));
        int subSpace = courseId.indexOf(" ");
        if (courseId.charAt(subSpace-1)==' ' || courseId.charAt(subSpace+1)==' ') {
            courseId.replaceFirst(" ", "");
        }
        courseName = driver.findElement(By.id("DERIVED_CRSECAT_DESCR200")).getText();

        secDays = new String[1];
        secStartTime = new String[1];
        secEndTime = new String[1];
        courseDays_Times = new String[1];

        idNum = 0;
        StringBuilder sb = new StringBuilder();
        CourseInfo course = new CourseInfo();
        List<CourseInfo> courses = new ArrayList<>();
        
        for (int secNo = 0; secNo < noOfSections; secNo++) {
            
            secId = driver.findElement(By.id("CLASS_SECTION$" + secNo)).getText();
            String tableId = "CLASS_MTGPAT$scroll$" + secNo;
            List<WebElement> rows = driver.findElements(By.xpath("//table[@id='" + tableId + "']/tbody/tr"));  //the web table ID that holds all the section lectures
            int rowCount = rows.size();  //calculating the number of rows in the table

            for (int row = 1; row < rowCount; row++) {
                secDays[0] = driver.findElement(By.id("MTGPAT_DAYS$" + idNum)).getText();
                secStartTime[0] = driver.findElement(By.id("MTGPAT_START$" + idNum)).getText();
                secEndTime[0] = driver.findElement(By.id("MTGPAT_END$" + idNum)).getText();


                StringBuilder newSb = new StringBuilder();

                course.setCourseID(courseId);
                course.setCourseName(courseName);
                course.setCourseSection(secId);
                course.setCourseDays(secDays[0]);
                course.setCourseStartTime(secStartTime[0]);
                course.setCourseEndTime(secEndTime[0]);

                sb = new StringBuilder();
                sb.append(course.getCourseStartTime()).append("-").append(course.getCourseEndTime()).append(" ")
                        .append(course.getCourseDays());


                String section = course.getCourseSection();
                int endSecNum = section.indexOf("-");
                String secNum = section.substring(endSecNum - 2, endSecNum);

                // if statement checks if the current info is LAB/TUT (part of a section), or more lectures continuation of a section, or a new section
                if (secId.contains("LAB") || secId.contains("TUT")) {
                    
                    courses.stream().filter(o -> o.getCourseSection().contains(secNum) && o.getCourseSection().contains("LEC"))
                    .forEach(o -> {o.setLabDays(secDays[0]); o.setLabStartTime(secStartTime[0]); o.setLabEndTime(secEndTime[0]); 
                        newSb.append(o.getLabStartTime()).append("-").append(o.getLabEndTime()).append(" ").append(o.getLabDays());
                        o.setLabDays_Times(newSb.toString()); });

                } else if (secId.contains("LEC") && courses.stream().anyMatch(o -> o.getCourseSection().contains(secNum))) {

                    String x = sb.toString();
                    
                    courses.stream().filter(o -> o.getCourseSection().contains(secNum) && o.getCourseSection().contains("LEC"))
                    .forEach(o -> {o.setCourseDays_Times(o.getCourseDays_Times() +"//"+ x); }); 
                    
                } else {
                    
                    courseDays_Times[0] = sb.toString();
                    courses.add(new CourseInfo(courseId, courseName, secId, secDays[0], secStartTime[0], secEndTime[0], courseDays_Times[0]));
                    
                    course.setCourseDays_Times(courseDays_Times[0]);
                }


                idNum++;
            }
        }
        
        String out = courseName + "   ";
        for (CourseInfo object : courses) { //looping through all the objects in the courses list
                // each object = one section
            out += object.getCourseDays_Times();  //append the lecture days & times to the string
            
            if (object.getLabStartTime() != null) {
                //if the course has a lab, then append the days & times to the string
                out += "//" + object.getLabDays_Times();
            }

            if (courses.indexOf(object) != (courses.size() - 1)) {
                //if this is not the final object (section) in the list, then separate each object using ' |'
                out += " |";
            }
        }
        System.out.println(out + "\n");
        // course.writeToFile(out);
        return out;

    }

    
    public static void commonBreaks () {

        // driver.get("https://sis.upm.edu.sa/psp/ps/EMPLOYEE/HRMS/c/SCC_ADMIN_OVRD.SSS_STUDENT_CENTER.GBL");

        // driver.switchTo().frame(0);
        String[] temp = new String[1000];
        int count = 0;

        String [] skipCourseIds = new String[5];

        for (int i = 0; i < skipCourseIds.length; i++) { 
            skipCourseIds[i] = JOptionPane.showInputDialog(dialog,
                "Enter the course ID you would like to exclude while\n"+"searching for common breaks. " + 
                "If there are none enter 'na'").toLowerCase().strip();

            if (skipCourseIds[i].equalsIgnoreCase("na")) { 
                break;
            }

            JOptionPane.showMessageDialog(dialog, "The course " + skipCourseIds[i] + " will be excluded");

        }


        studIdInput: do {

            String studentId = JOptionPane
                    .showInputDialog(dialog, "Enter the student ID you would like to search for. \n" + 
                                                                "The student ID's can be entered individually or all at once separated by a space,\n" + 
                                                                "e.g. 440000 440001 440002");
            String [] students = studentId.split(" ");

            for (String studId : students) {

                driver.switchTo().defaultContent();
                driver.get("https://sis.upm.edu.sa/psp/ps/EMPLOYEE/HRMS/c/SCC_ADMIN_OVRD.SSS_STUDENT_CENTER.GBL");
                driver.switchTo().frame(0);

                WebElement studSearch_txtbx = driver.findElement(By.id("PEOPLE_SRCH_EMPLID"));
                studSearch_txtbx.sendKeys(studId.strip());

                driver.findElement(By.id("#ICSearch")).click();

                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("STDNT_WEEK_SCHD$scroll$0")));
                    driver.findElement(By.id("STDNT_WEEK_SCHD$scroll$0"));
                } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException ex) {
                    System.out.println("Table not found!");
                    JOptionPane.showMessageDialog(dialog, "Unable to find the schedule for this student. \nPlease try re-entering the student ID.");
                    continue studIdInput;
                }

                WebElement studCoursetable = driver.findElement(By.id("STDNT_WEEK_SCHD$scroll$0"));
                List<WebElement> rows = studCoursetable.findElements(By.tagName("tr"));
                // System.out.println(rows.size());

                CourseInfo course = new CourseInfo();
                // List<CourseInfo> courses = new ArrayList<>();

                allCoursesLoop: for (int i = 2; i < rows.size(); i++) {
                    // StringBuilder sb = new StringBuilder();

                    List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));

                    String courseName_Section = cols.get(2).getText();

                    for (String skipCourseId: skipCourseIds) { 

                        if (skipCourseId.equalsIgnoreCase("na")) {
                            break;
                        } else if (courseName_Section.toLowerCase().contains(skipCourseId)) {
                            continue allCoursesLoop;
                        }

                    }

                    

                    int indexDash = courseName_Section.indexOf("-");
                    int index_lastSpace = courseName_Section.lastIndexOf(" ");

                    String courseId = courseName_Section.substring(0, indexDash);
                    String courseSection = courseName_Section.substring(indexDash + 1, index_lastSpace).replace("\n",
                            "\s");

                    // System.out.println(courseName_Section);

                    String courseDays_Times = cols.get(3).getText();
                    String[] lines = courseDays_Times.split("\n");

                    course.setCourseID(courseId);
                    course.setCourseSection(courseSection);

                    // int endSecNum = courseName_Section.indexOf("\n");
                    // String secNum = courseName_Section.substring(endSecNum - 2, endSecNum);

                    String[] days = new String[1];
                    String startTime[] = new String[1];
                    String endTime[] = new String[1];
                    // String[] days_Times = new String[1];

                    int lineNum = 0;
                    int indexSpace = 0;
                    int indexDash2 = 0;
                    int indexEnd = 0;

                    do {
                        StringBuilder timeArrayBuilder = new StringBuilder();
                        String line = lines[lineNum];

                        try {
                            indexSpace = line.indexOf(" ");
                            indexDash2 = line.indexOf("-");
                            indexEnd = line.length();

                            days[0] = line.substring(0, indexSpace);
                            startTime[0] = line.substring(indexSpace + 1, indexDash2 - 1);
                            endTime[0] = line.substring(indexDash2 + 1, indexEnd);

                            course.setCourseDays(days[0]);
                            course.setCourseStartTime(startTime[0]);
                            course.setCourseEndTime(endTime[0]);
                        } catch (StringIndexOutOfBoundsException e) {
                            System.out.println("The course " + course.getCourseID() + " has unassgined times, so it will not be taken into account.");
                            lineNum += 2;
                            continue allCoursesLoop;
                        }

                        // sb.append(course.getCourseStartTime()).append("-").append(course.getCourseEndTime())
                        //         .append(" ").append(course.getCourseDays()).append(course.getCourseID()).append(" ").append("L" + secNum);
                        //         course.setCourseDays_Times(sb.toString());

                        timeArrayBuilder.append(course.getCourseStartTime()).append("-")
                                .append(course.getCourseEndTime())
                                .append(" ").append(course.getCourseDays());

                        temp[count] = timeArrayBuilder.toString();
                        count++;


                        lineNum += 2;
                        // if (lineNum < lines.length) {
                        //     sb.append("//");
                        //     continue;
                        // }

                    } while (lineNum < lines.length);

                    // if ((courses.stream().anyMatch(o -> o.getCourseID().contains(courseId)))
                    //         && (courseName_Section.contains("LAB") || courseName_Section.contains("TUT"))) {
                    //     StringBuilder newSb = new StringBuilder();

                    //     courses.stream()
                    //             .filter(o -> o.getCourseSection().contains("LEC") && o.getCourseID().contains(courseId))
                    //             .forEach(o -> {
                    //                 o.setLabDays(days[0]);
                    //                 o.setLabStartTime(startTime[0]);
                    //                 o.setLabEndTime(endTime[0]);
                    //                 newSb.append(o.getLabStartTime()).append("-").append(o.getLabEndTime()).append(" ")
                    //                         .append(o.getLabDays()).append(" ").append(course.getCourseID()).append(" ")
                    //                         .append("L" + secNum);
                    //                 if (!(o.getLabDays_Times() == null)) {
                    //                     o.setLabDays_Times(o.getLabDays_Times() + "//" + newSb.toString());
                    //                 } else {
                    //                     o.setLabDays_Times(newSb.toString());
                    //                 }

                    //             });

                    // } else if (!(courses.stream().anyMatch(o -> o.getCourseID().contains(courseId)))) {
                    //     days_Times[0] = sb.toString();
                    //     courses.add(new CourseInfo(courseId, null, courseSection, days[0], startTime[0], endTime[0],
                    //             days_Times[0]));
                    //     course.setCourseDays_Times(days_Times[0]);
                    // }
                }

            }
            // System.out.println(sb.toString());
    
            // for (CourseInfo object : courses) {
    
            //     System.out.print(object.getCourseDays_Times());
    
            //     if (object.getLabDays_Times() != null) {
            //         System.out.print("//" + object.getLabDays_Times());
            //     }
    
            //     if (courses.indexOf(object) != (courses.size() - 1)) {
            //         System.out.println(" |");
            //     }
            // }

            String addStudent = JOptionPane.showInputDialog(dialog, "Would you like to add another student? (y/n)");
            switch (addStudent.toLowerCase().strip()) {
                case "y":
                    break;

                case "n":
                    break studIdInput;

                default:
                    JOptionPane.showMessageDialog(dialog, "Invalid input. Program will continue.");
            }

            // if (addStudent.equalsIgnoreCase("n")) {
            //     break;
            // }

        } while (true);



        String[] allTimes = Arrays.copyOf(temp, count);
        // System.out.println("\n\n" + Arrays.toString(allTimes));
        // System.out.println("\n\n" + Arrays.toString(allTimes) + "\n");


        // ScheduleTools initCommonBreaks = new ScheduleTools();
        // initCommonBreaks.commonBreak(reserve(arrayCopy));

        String [] commonBreaks = ScheduleTools.commonBreak(ScheduleTools.reserve(allTimes));

        System.out.println("\n\n");
        Output commonBreakOutput = new Output();
        commonBreakOutput.CommonBreakOut(commonBreaks);
    }



    public static void initCourseProcessing () {
        String[][] Sets = new String[7][];

        for (byte i = 0; i < Sets.length; i++)
            Sets[i] = new String[] { ScheduleTools.noTime };

        Course[] C = new Course[7];

        int newlines = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\objects_file.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                
                int dashIndex = line.indexOf(" - ");
                String courseId = line.substring(0, dashIndex);

                C[newlines] = new Course(courseId);
                
                newlines++;
            }

            reader.close();
            // System.out.println("Number of newlines: " + newlines);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        switch (Course.numOfCourses) {
            case 7:
                Sets[0] = C[6].getSections();
            case 6:
                Sets[1] = C[5].getSections();
            case 5:
                Sets[2] = C[4].getSections();
            case 4:
                Sets[3] = C[3].getSections();
            case 3:
                Sets[4] = C[2].getSections();
            case 2:
                Sets[5] = C[1].getSections();
            case 1:
                Sets[6] = C[0].getSections();

        }
        // 11:00-12:15 SuTu//13:00-14:50 Th AI 407 L01, 20:00-20:50 SuTu AI 372 L01,
        // 18:15-19:30 MoWe AI 312 L01, 11:00-12:15 SuTu AI 306 L02
        String [][] net =  ScheduleTools.combine(Sets);
        Output.ScheduleArrangerOut(net);

    }

}
