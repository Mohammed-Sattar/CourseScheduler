## Program Purpose & Function

This Java project was created for the students and faculty of Prince Mugrin University (UPM). It has 2 functions: 
1. It generates all the possible term schedules for a student
2. It helps the faculty find the common breaks between students in order to add or change lecture times 


## How the Program Works
This program was developed using Java. It uses [Selenium](https://www.selenium.dev/) and a web-driver to scrape the neccassary data from the university's Student Information System (SIS). 

## How to use this program

1. This program was created in VS Code. Therefore, the project and folder structure is the format that is supported by VS Code. In order to run this program, you can:
   - Learn how to setup a Java project with Selenium in your respective IDE, then copy the source code (`.java`) files from the `src` folder to the `src` folder of your own project.
   - Download [VS Code](https://code.visualstudio.com/). Then download a JDK. Finally, install the [VS Code Coding Pack for Java](https://code.visualstudio.com/docs/languages/java). Run a simple HelloWorld program to test that VS Code is functioning properly.
2. Find out the exact browser version that you use and download the corresponding web-driver. Save the web-driver to the [src/drivers](src/drivers) folder.
3. Rename the web-driver that you just downloaded to "webdriver.exe"
4. If you are using Microsoft Edge browser Skip this step. If you are using Firefox, delete or comment lines (30, 39 & 46) and uncomment the lines (31, 40 & 47) in [WebScraping.java](src/WebScraping.java). If you are using Chrome, delete or comment lines (30 , 39 & 46) and uncomment the lines (32, 41 & 48) in [WebScraping.java](src/WebScraping.java).
5. If you want to find all the possible schedules for the current term, run the [Main_ScheduleArranger.java](src/Main_ScheduleArranger.java) file. (*Note: This program won't work for students who have prepatory courses in their planner due to their sis layout being different. Also, faculty are unable to use this function due to unpermitted access on sis.*)
6. If you want to find the common breaks between students, run the [Main_CommonBreaks.java](src/Main_CommonBreaks.java) file. (*Note: Students are unable to use this function due to unpermitted access on sis.*)



## Developers
- Mohammed Sattar
- Youssef ElNahas
- Hamza AlKaf
- Abobakar Waziri


## Supervisor
Dr. Mohamed Zayed
