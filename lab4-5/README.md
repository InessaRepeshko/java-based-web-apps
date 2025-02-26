# Laboratory Training 4 "Study of the principles of using Spring Security to implement authentication, authorization and session support"



## Objectives
1. Deepen knowledge of Spring Security principles and applications.
2. Gain skills in creating Spring applications with authentication and authorization using Spring Security.
3. Learn to define system roles and users.
4. Acquire skills in implementing an authorization system using Spring Security.
5. Develop skills in session management within Spring applications.
6. Learn to use Spring Security tags in Thymeleaf web pages.
7. Improve the ability to create user-friendly applications.



## Task Description
Enhance the Spring application developed in [Lab Work #3](https://github.com/InessaRepeshko/java-based-web-apps/tree/main/lab3/WebAppsRIVLab3) by adding authentication and authorization functionality using Spring Security.

The application must:
- Enable user registration with login and password storage.
- Allow access only to registered users.
- Grant data access and CRUD operations based on user roles assigned during registration.
- Display the logged-in user's name.
- Implement session timeout restrictions for registered users.



## Implementation Steps
### 1. Implement Security System Using Spring Security
1. Configure Roles and Users:
    - Define user roles (`ADMIN`, `USER`).
    - Store users in a database or configuration files for testing.
2. Role-Based Access Control:
    - Configure access to resources and operations based on roles.
    - `ADMIN` users have full access, while `USER` users can only view data.
3. Configure Authentication and Authorization:
    - Implement a login form and authentication mechanism.
    - Use password hashing for secure storage in the database.
4. Secure Data and Interface:
    - Hide CRUD operation elements for `USER` role users.

### 2. Implement Session Management Using Spring
1. Session Configuration:
    - Set up a mechanism to manage user sessions and track activity.
2. Display User Information:
    - Show the logged-in user’s name and role after authentication.
3. Session Timeout Control:
    - Implement session expiration and automatic logout after inactivity.

### 3. Implement User Registration Functionality
1. User Registration Interface:
    - Develop a user-friendly registration page, accessible only to `ADMIN`.
2. User Registration Logic:
    - Create a mechanism for adding new users and validating input data.
3. Spring Security Configuration for New Users:
    - Ensure new users can authenticate using Spring Security.



## Examples of program results

The results of the programs are available in the report at the link:

* [lab4-report.pdf](https://github.com/InessaRepeshko/java-based-web-apps/blob/main/reports/RepeshkoIV_CS222a_Lab4.pdf)



# Laboratory Training 5 "Study of Java components for organizing email distribution"



## Objectives
1. Gain experience using Java Mail API to send emails from a Spring application.
2. Deepen understanding of the purpose and configuration of mail servers.
3. Improve knowledge of data flow processes in web applications deployed on a web server.



## Task Description
Enhance the Spring application from [Lab Work #4](https://github.com/InessaRepeshko/java-based-web-apps/tree/main/lab4-5/WebAppsRIVLab4) by adding functionality to send an email to a specified address using the email service [Ethereal](https://ethereal.email/).



## Implementation Stages
### 1. Mail Server Setup
- Configure an email account to act as a mail server.
- Configure SMTP settings for sending emails.

### 2. Develop Email Sending Functionality
- Programmatically generate an email message.
- Send the email using Java Mail API integrated with Spring Boot.



© Inessa Repeshko. 2024
