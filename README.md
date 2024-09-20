Exam Ninja: User Management - Backend Project.

  This project is the backend RESTful service built using Spring Boot. It includes  Registration, Login and change password API.

***Table of Contents

Features

Technologies Used

Prerequisites

Installation

 -Running the Project

 -Testing

 -API Documentation


**Features

*User Management: Registration, login, password management.

*Global Exception Handling: Uses @RestControllerAdvice for handling exceptions.


**Technologies Used

-Spring Boot (Java)

-JPA/Hibernate (for ORM)

-MySQL (as the data store)

-Swagger/OpenAPI (for API documentation)

-JUnit and Mockito (for unit and integration testing)

-Maven (for build automation)


**Prerequisites

Before running the project, ensure the following should be installed:

-Java 17 or above

-IntelliJ IDEA (Ultimate or Community Edition)

-Maven 4.0.0 (or higher version than 3.6)

-MySQL (or any other database)

-Git (for version control)

**Installation

1. Clone the Repository

-Open IntelliJ IDEA.

-Navigate to File → New → Project from Version Control.

-From the drop down box choose Git and paste the repository URL:


2. Open the Project in IntelliJ

Once the project is cloned:
IntelliJ will automatically detect it as a Maven project and start importing dependencies. If not, click the notification at the bottom right that says "Import Maven projects."
Wait for IntelliJ to download all the necessary dependencies from Maven. You should see a message in the Event Log when the import is complete.


3. Configure the Database

-Navigate to src/main/resources/application.properties (or application.yml) and update the database credentials:

properties-

spring.datasource.url=jdbc:mysql://localhost:3306/exam_ninja

spring.datasource.username=<username-to-be-inserted>

spring.datasource.password=<password-to-be-inserted>

-Make sure that the MySQL server is running, and the database exam_ninja is created. You have to create the database manually using MySQL Workbench or command line:

CREATE DATABASE exam_ninja;


4. Build the Project

Open the Maven Tool Window from the right sidebar or navigate to View → Tool Windows → Maven.
In the Maven window, expand your project folder.
Under Lifecycle, double-click on clean and install. This will clean any previous builds and install the necessary dependencies.


5. Run the Project

Open the ExamNinjaApplication.java file located in src/main/java/com/exam/ninja/ExamNinjaApplication.java.

Right-click on the file and choose Run 'ExamNinjaApplication'.

IntelliJ will start the application, and you should see the Spring Boot banner in the Run window.


6. Access the Application

-Once the application starts, you can access it in your browser:

http://localhost:8081

-Swagger API Docs: 

http://localhost:8081/swagger-ui/index.html


7. Run the Tests

To run tests:

Goto src/test directory.

You can right-click on individual test classes or methods and choose Run to run specific tests.

8. For Manual Tests.

 -Use PostMan
