## Program Purpose & Function

This Java project was created for the students and faculty of Prince Mugrin University (UPM). It has 2 functions: 
1. It generates all the possible term schedules for a student
2. It helps the faculty find the common breaks between students in order to add or change lecture times 


## How the Program Works
This program was developed using Java. Through the use of a web-driver, it uses [Selenium](https://www.selenium.dev/) to scrape the neccassary data from the university's Student Information System (SIS). 

## How to use this program

1. This program was created in VS Code. Therefore, the project and folder structure is the format that is supported by VS Code. In order to run this program, you can:
   - Learn how to setup a Java project with Selenium in your respective IDE, then copy the source code (`.java`) files from the `src` folder to the `src` folder of your own project.
   - Download [VS Code](https://code.visualstudio.com/). Then download a JDK. Finally, install the [VS Code Coding Pack for Java](https://code.visualstudio.com/docs/languages/java). Run a simple HelloWorld program to test that VS Code is functioning properly.
2. Find out the exact browser version that you use and download the corresponding web-driver. Save the web-driver to the [src/drivers](src/drivers) folder.
3. Rename the web-driver that you just downloaded to "webdriver.exe"
4. If you are using Microsoft Edge browser Skip this step. If you are using Firefox, delete or comment lines (30, 39 & 46) and uncomment the lines (31, 40 & 47) in [WebScraping.java](src/WebScraping.java). If you are using Chrome, delete or comment lines (30 , 39 & 46) and uncomment the lines (32, 41 & 48) in [WebScraping.java](src/WebScraping.java).


## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.



## Developers
- Mohammed Sattar
- Youssef ElNahas
- Hamza AlKaf
- Abobakar Waziri


## Supervisor
Dr. Mohamed Zayed
