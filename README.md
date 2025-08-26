<div align="center"><img src="screens/favicon.png" width="200"/></div>
<h1 align="center">Student Database Viewer</h1>

<p align="center">
   <img src="https://img.shields.io/badge/Java-007396?logo=java&logoColor=white" alt="Java" />
   <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot" />
   <img src="https://img.shields.io/badge/Spring_Security-6DB33F?logo=springsource&logoColor=white" alt="Spring Security" />
   <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?logo=springsource&logoColor=white" alt="Spring Data JPA" />
   <img src="https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=white" alt="Hibernate" />
   <img src="https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white" alt="MySQL" />
   <img src="https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white" alt="Maven" />
   <img src="https://img.shields.io/badge/Tomcat-F8DC75?logo=apachetomcat&logoColor=black" alt="Tomcat" />
   <img src="https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf&logoColor=white" alt="Thymeleaf" />
   <img src="https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white" alt="HTML5" />
   <img src="https://img.shields.io/badge/CSS3-1572B6?logo=css3&logoColor=white" alt="CSS3" />
   <img src="https://img.shields.io/badge/Bootstrap-7952B3?logo=bootstrap&logoColor=white" alt="Bootstrap" />
   <img src="https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black" alt="JavaScript" />
   <img src="https://img.shields.io/badge/JUnit-25A162?logo=junit5&logoColor=white" alt="JUnit" />
   <img src="https://img.shields.io/badge/Lombok-2A2A2A?logo=lombok&logoColor=white" alt="Lombok" />
</p>



## Table of Contents
- [Java-based Web Applications](#java-based-web-applications)
- ["Student Database Viewer" App](#student-database-viewer-app)
  - [Overview: Student Admission and Management System](#overview-student-admission-and-management-system)
  - [Project Structure](#project-structure)
  - [Features](#features)
  - [Technologies Used](#technologies-used)
      - [Stack](#stack)
      - [Key Technologies](#key-technologies)
  - [Database Diagram](#database-diagram)
    - [Tables](#tables)
  - [Setup and Installation](#setup-and-installation)
      - [Prerequisites](#prerequisites)
      - [Steps](#steps)
      - [Testing](#testing)
  - [Fake data](#fake-data)
  - [Usage](#usage)
  - [Web Application Results](#web-application-results)
      - [1 Registering a New User and Displaying Available Functionality for a Student Viewer](#1-registering-a-new-user-and-displaying-available-functionality-for-a-student-viewer)
      - [2 Available Functionality for an Entrant Viewer](#2-available-functionality-for-an-entrant-viewer)
      - [3 Available Functionality for an Entrant Manager](#3-available-functionality-for-an-entrant-manager)
      - [4 Available Functionality for a Student Manager](#4-available-functionality-for-a-student-manager)
      - [5 Available Functionality for a Administrator](#5-available-functionality-for-a-administrator)



# Java-based Web Applications

The repository contains the results of the Laboratory Trainings for the course "Java-based Web Applications" (2024) during studies at National Technical University "Kharkiv Polytechnic Institute". 

The course provides knowledge to create, debug, and deploy web applications in Java, including Servlets, JSP, JSTL, Hibernate, Spring, authentication, session management, working with cookies, and sending emails through mail services; practice building and deploying web projects with client-server databases.



# "Student Database Viewer" App

## Overview: Student Admission and Management System

The system manages data for university applicants (entrants/abiturients) and students, with role-based access control to ensure secure CRUD operations on related entities.

The application uses a multi-layered architecture to handle user registration, login, data viewing/editing, and session timeouts. It interacts with a MySQL database and employs Thymeleaf for dynamic HTML rendering.



## Project Structure

The project is structured as a series of incremental laboratory works (labs), each building on the previous one, to demonstrate progressive web development skills.

<table width="100%" border="0" cellpadding="1" align="center">  
    <tr>
        <th>Lab Training</th>
        <th>Topic</th>
        <th>Report</th>
        <th>Score</th>
    </tr>
    <tr>
        <td><a href="https://github.com/InessaRepeshko/java-based-web-apps/tree/main/lab1">Lab 1</a></td>
        <td>Exploring the Java technology stack for creating and deploying web applications using servlets and JSP</td>
        <td><a href="https://github.com/InessaRepeshko/java-based-web-apps/blob/main/reports/RepeshkoIV_CS222a_Lab1.pdf">lab1-report.pdf</a></td>
        <td>100</td>
    </tr>
    <tr>
        <td><a href="https://github.com/InessaRepeshko/java-based-web-apps/tree/main/lab2">Lab 2</a></td>
        <td>Study of the principles of using Hibernate, JPA and HQL to access data in web applications</td>
        <td><a href="https://github.com/InessaRepeshko/java-based-web-apps/blob/main/reports/RepeshkoIV_CS222a_Lab2.pdf">lab2-report.pdf</a></td>
        <td>100</td>
    </tr>
    <tr>
        <td><a href="https://github.com/InessaRepeshko/java-based-web-apps/tree/main/lab3">Lab 3</a></td>
        <td>Study of the principles of creating complex projects using Spring Boot, Spring Data, Thymeleaf</td>
        <td><a href="https://github.com/InessaRepeshko/java-based-web-apps/blob/main/reports/RepeshkoIV_CS222a_Lab3.pdf">lab3-report.pdf</a></td>
        <td>100</td>
    </tr>
    <tr>
        <td><a href="https://github.com/InessaRepeshko/java-based-web-apps/tree/main/lab4-5">Lab 4</a></td>
        <td>Study of the principles of using Spring Security to implement authentication, authorization and session support</td>
        <td rowspan="2"><a href="https://github.com/InessaRepeshko/java-based-web-apps/blob/main/reports/RepeshkoIV_CS222a_Lab4.pdf">lab4-report.pdf</a></td>
        <td>100</td>
    </tr>
    <tr>
        <td><a href="https://github.com/InessaRepeshko/java-based-web-apps/tree/main/lab4-5">Lab 5</a></td>
        <td>Study of Java components for organizing email distribution</td>
        <td>100</td>
    </tr>
</table><br />



## Features

1. **User Authentication & Registration**: Secure login form with password hashing (BCrypt). Admin-only user creation; self-registration assigns "STUDENT_VIEWER" role.
2. **Role-Based Authorization**:
   * <span style="color: #fd7e14;">**ADMIN**</span>: Full CRUD on all entities (users, entrants, students).
   * <span style="color: #198754;">**ENTRANT_MANAGER**</span>: Full CRUD on entrants; read-only on students.
   * <span style="color: #6f42c1;">**STUDENT_MANAGER**</span>: Full CRUD on students; read-only on entrants.
   * <span style="color: #20c997;">**ENTRANT_VIEWER**</span>: Read-only on entrants.
   * <span style="color: #6610f2;">**STUDENT_VIEWER**</span>: Read-only on students.
3. **Email notification**: Automatic sending of an email with a link to reset the password to newly created users by the administrator. Sending an email to reset the password at the user's request.
4. **CRUD Operations**: Create, read, update, delete for entrants, students, and users (with foreign key constraints).
5. **Data Management**: Filtering, sorting, searching, and pagination for entity tables.
6. **Session Handling**: Display logged-in user name/role; automatic logout after inactivity.
7. **Validation**: Custom validators for fields (e.g., IDs, birthdays, usernames, emails).
8. **Frontend**: Responsive UI with Bootstrap; Thymeleaf templates for dynamic content.
9. **Database Integration**: MySQL schema with tables for entrants, students, and users (one-to-one relationship between entrants and students).
10. **Testing**: JUnit tests for services and application functionality.
11. **Logging**: Configured via Logback.



## Technologies Used

### Stack

* **Backend**: Java 17, Spring Boot 3.x, Spring Security, Spring Data JPA, Hibernate ORM.
* **Database**: MySQL 8.0.30.
* **Frontend**: Thymeleaf, HTML5, CSS (Bootstrap 5.3), JavaScript.
* **Build Tool**: Maven (pom.xml with dependencies like Lombok, JUnit, etc.).
* **Other**: Lombok for boilerplate reduction, Custom annotations for validation.

### Key technologies:

* **Java** for core development.
* **Servlets**, **JSP** for initial web pages and dynamic content.
* **Hibernate** for object-relational mapping (ORM) and database interactions.
* **Spring Boot** for application framework, including dependency injection, MVC architecture, and RESTful services.
* **Spring Data JPA** for repository management.
* **Spring Security** for authentication, authorization, and session management.
* **MySQL** as the relational database.
* **Maven** for build automation.
* **Tomcat** as the web/application server.
* **Thymeleaf / HTML, CSS, Bootstrap 5.3, JavaScript** for frontend templating (replacing plain JSP in later stages).
* **JUnit** for unit testing CRUD operations.



## Project Structure

The project follows a multi-layered architecture:
1. Configuration
2. Controller
3. Model
4. Repository
5. Service
6. Validation
7. View and resources

The project architecture is shown below.
![Project architecture 1](screens/architecture1.png)
![Project architecture 2](screens/architecture2.png)
![project architecture 3](screens/architecture3.png)


## Database diagram

![Database diagram](screens/db_scheme.png)

### Tables:

* **entrants**: stores applicant data;
    ![Entrants Table](screens/db_entrants.png)
* **students**: additional student info;
    ![Students Table](screens/db_students.png)
* **users**: user accounts.
    ![Users Table](screens/db_users.png)


## Setup and Installation

### Prerequisites

* Java 17+ JDK
* Maven 3.x
* MySQL 8.0+ (create a database named as per application.properties)
* IntelliJ IDEA or Eclipse (recommended for development)

### Steps

1. Clone the repository:
    ```text
   git clone https://github.com/yourusername/WebAppsRIVLab4.git 
    cd WebAppsRIVLab4
   ```
2. Configure the database:
   * Update [application.properties](lab4-5/WebAppsRIVLab4/src/main/resources/application.properties) with your MySQL credentials (e.g., username, password, URL).
   * Run the application to auto-generate tables via Hibernate (or import schema manually).
3. Build the project:
    ```text
    mvn clean install
    ```
4. Run the application:
    ```text
    mvn spring-boot:run
    ```
    * Access at [http://localhost:8080](http://localhost:8080).

### Testing

* Run unit tests:
    ```
    mvn test
   ```
* Covers CRUD operations for services.

The structure of the test sets is shown below.
![EntrantServiceTest](screens/test_entrantServiceTest.png)
![StudentServiceTest](screens/test_studentServiceTest.png)
![UserServiceTest](screens/test_userServiceTest.png)



## Fake data

Here is the fake data for presentations.

| Role | Username | Password | Email |
|:-:|:-|:-|-:|
|<span style="color: #fd7e14;">**ADMIN**</span>|```shavlii_oleksii```|```Password123!$```|```shavlii.oleksii@ntu.khpi.edu.ua```|
|<span style="color: #198754;">**ENTRANT_MANAGER**</span>|```burulka-liudmyla```|```Password123!$```|```burulka.liudmyla@ntu.khpi.edu.ua```|
|<span style="color: #6f42c1;">**STUDENT_MANAGER**</span>|```voloshkov-tymur```|```Password123!$```|```voloshkov.tymur@ntu.khpi.edu.ua```|
|<span style="color: #20c997;">**ENTRANT_VIEWER**</span>|```kulish.volodymyr```|```Password123!$```|```kulish.volodymyr@cs.khpi.edu.ua```|
|<span style="color: #6610f2;">**STUDENT_VIEWER**</span>|```stepova.alina```|```Password123!$```|```stepova.alina@cs.khpi.edu.ua```|



## Usage

1. Login/Register:
   * Visit `/login` for authentication.
   * Admins can add users at `/users/add`.
   * Self-register at `/register` (assigns `STUDENT_VIEWER` role).
2. Home Page: Redirects based on role; displays navigation menu.
3. Entity Management:
   * `/entrants`: View/edit applicants (access varies by role).
   * `/students`: View/edit students.
   * `/users`: Manage users (ADMIN only).
4. Search/Filter/Sort: Available on table views (e.g., search by name, filter by role).
5. Logout: Automatic after inactivity (configurable in SecurityConfig).

Screenshots of results are below, showing interfaces for different roles.



## Web Application Results

## 1 Registering a New User and Displaying Available Functionality for a Student Viewer

1. Registration Page with Entered Credentials for a New User ![Figure 1.1 - Registration Page with Entered Credentials for a New User](screens/image_1_1.png)
2. Authentication Page with a Message About Successful Registration of a New User ![Figure 1.2 - Authentication Page with a Message About Successful Registration of a New User](screens/image_1_2.png)
3. Authentication Page with Entered Credentials of a Registered User ![Figure 1.3 - Authentication Page with Entered Credentials of a Registered User](screens/image_1_3.png)
4. Home Page of the Web Application for a Registered Student Viewer ![Figure 1.4 - Home Page of the Web Application for a Registered Student Viewer](screens/image_1_4.png)
5. Profile Page of a Registered User with the Student Viewer Role ![Figure 1.5 - Profile Page of a Registered User with the Student Viewer Role](screens/image_1_5.png)
6. User Credentials Change Page ![Figure 1.6 - User Credentials Change Page](screens/image_1_6.png)
7. User Credentials Change Page with a Message About Successful Update ![Figure 1.7 - User Credentials Change Page with a Message About Successful Update](screens/image_1_7.png)
8. Re-authentication Page After Data Change with Updated Credentials for a Student Viewer ![Figure 1.8 - Re-authentication Page After Data Change with Updated Credentials for a Student Viewer](screens/image_1_8.png)
9. Home Page for a User with Updated Credentials and the Student Viewer Role ![Figure 1.9 - Home Page for a User with Updated Credentials and the Student Viewer Role](screens/image_1_9.png)
10. Students Table Page for a Student Viewer ![Figure 1.10 - Students Table Page for a Student Viewer](screens/image_1_10.png)

## 2 Available Functionality for an Entrant Viewer

1. Authentication Page with Credentials of an Entrant Viewer ![Figure 2.1 - Authentication Page with Credentials of an Entrant Viewer](screens/image_2_1.png)
2. Home Page of the Web Application for an Entrant Viewer ![Figure 2.2 - Home Page of the Web Application for an Entrant Viewer](screens/image_2_2.png)
3. Profile Page of an Entrant Viewer ![Figure 2.3 - Profile Page of an Entrant Viewer](screens/image_2_3.png)
4. Entrants Table Page for an Entrant Viewer ![Figure 2.4 - Entrants Table Page for an Entrant Viewer](screens/image_2_4.png)

## 3 Available Functionality for an Entrant Manager

1. Authentication Page with Credentials of an Entrant Manager ![Figure 3.1 - Authentication Page with Credentials of an Entrant Manager](screens/image_3_1.png)
2. Home Page of the Web Application for an Entrant Manager ![Figure 3.2 - Home Page of the Web Application for an Entrant Manager](screens/image_3_2.png)
3. Profile Page of an Entrant Manager ![Figure 3.3 - Profile Page of an Entrant Manager](screens/image_3_3.png)
4. Entrants Table Page for an Entrant Manager ![Figure 3.4 - Entrants Table Page for an Entrant Manager](screens/image_3_4.png)
5. Entrant Editing Page by an Entrant Manager ![Figure 3.5 - Entrant Editing Page by an Entrant Manager](screens/image_3_5.png)
6. Students Table Page for an Entrant Manager ![Figure 3.6 - Students Table Page for an Entrant Manager](screens/image_3_6.png)

## 4 Available Functionality for a Student Manager

1. Authentication Page with Credentials of a Student Manager ![Figure 4.1 - Authentication Page with Credentials of a Student Manager](screens/image_4_1.png)
2. Home Page of the Web Application for a Student Manager ![Figure 4.2 - Home Page of the Web Application for a Student Manager](screens/image_4_2.png)
3. Profile Page of a Student Manager ![Figure 4.3 - Profile Page of a Student Manager](screens/image_4_3.png)
4. Entrants Table Page for a Student Manager ![Figure 4.4 - Entrants Table Page for a Student Manager](screens/image_4_4.png)
5. Students Table Page for a Student Manager ![Figure 4.5 - Students Table Page for a Student Manager](screens/image_4_5.png)
6. Student Editing Page by a Student Manager ![Figure 4.6 - Student Editing Page by a Student Manager](screens/image_4_6.png)

## 5 Available Functionality for a Administrator

1. Authentication Page with Administrator Credentials ![Figure 5.1 - Authentication Page with Administrator Credentials](screens/image_5_1.png)
2. Home Page of the Web Application for an Administrator ![Figure 5.2 - Home Page of the Web Application for an Administrator](screens/image_5_2.png)
3. Profile Page of an Administrator ![Figure 5.3 - Profile Page of an Administrator](screens/image_5_3.png)
4. Page for Creating a New User by an Administrator ![Figure 5.4 - Page for Creating a New User by an Administrator](screens/image_5_4.png)
5. User Editing Page by an Administrator with a Message About Successful Creation of a New User ![Figure 5.5 - User Editing Page by an Administrator with a Message About Successful Creation of a New User](screens/image_5_5.png)
6. User Editing Page by an Administrator with a Message About Successful Update of Existing User Data ![Figure 5.6 - User Editing Page by an Administrator with a Message About Successful Update of Existing User Data](screens/image_5_6.png)
7. Users Table Page for an Administrator ![Figure 5.7 - Users Table Page for an Administrator](screens/image_5_7.png)
8. User Deletion Page by an Administrator with a Confirmation Prompt ![Figure 5.8 - User Deletion Page by an Administrator with a Confirmation Prompt](screens/image_5_8.png)
9. Users Table Page for an Administrator with a Message About User Deletion ![Figure 5.9 - Users Table Page for an Administrator with a Message About User Deletion](screens/image_5_9.png)
10. Users Table Page for an Administrator with Applied Search, Filtering, and Sorting ![Figure 5.10 - Users Table Page for an Administrator with Applied Search, Filtering, and Sorting](screens/image_5_10.png)



___

© Inessa Repeshko. 2024
