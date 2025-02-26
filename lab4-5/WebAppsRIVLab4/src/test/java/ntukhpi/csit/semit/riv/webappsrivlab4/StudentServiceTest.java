package ntukhpi.csit.semit.riv.webappsrivlab4;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * Unit test class for the `StudentService` class.
 * This class tests the functionality of the `StudentService` in managing `Student` entities, including creating, reading, updating, and deleting student records.
 * It also checks for validation rules, such as ensuring that duplicate corporate emails are not allowed.
 * <p>
 * Key functionalities tested:
 * - Creating and saving a student, ensuring it has an ID after saving.
 * - Reading a student by ID and verifying that the corporate email matches.
 * - Updating a student's information, specifically updating the corporate email.
 * - Deleting a student and ensuring it cannot be found afterward.
 * - Checking that attempting to save a student with a duplicate corporate email throws an exception.
 * <p>
 * Each test method ensures that the `StudentService` behaves as expected and performs the required validation and exception handling.
 * <p>
 * Test annotations:
 * - `@BeforeEach` to set up the test data before each test method.
 * - `@AfterEach` to clean up any test data after each test method.
 * <p>
 * Dependencies:
 * - `StudentService`: Provides methods for handling `Student` entities.
 * - `EntrantService`: Provides methods for handling `Entrant` entities, which are related to students.
 *
 * @author Inessa Repeshko CS-222a
 * @see Student
 * @see Entrant
 * @see StudentService
 * @see CustomServiceException
 */


@SpringBootTest
class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private EntrantService entrantService;
    private Student testStudent;
    private Entrant testEntrant;

    @BeforeEach
    void setUp() {
        testEntrant = new Entrant("КН22-9876", "Франко", "Іван", "Якович", "2002-01-15", "TRUE", "150.0");
        testStudent = new Student(testEntrant, "бюджет", "звичайна", "franko.ivan@cs.khpi.edu.ua");
        entrantService.saveEntrant(testEntrant);
    }

    @AfterEach
    void reset() {
        studentService.deleteStudent(testStudent.getId());
    }

    @Test
    void testCreateStudent() {
        Student savedStudent = studentService.saveStudent(testStudent);
        assertNotNull("Student should have an ID after saving.", savedStudent.getId());
    }

    @Test
    void testReadStudent() {
        Student savedStudent = studentService.saveStudent(testStudent);
        Student foundStudent = studentService.findStudentById(savedStudent.getId());
        assertNotNull("Student should be found by ID.", foundStudent);
        assertEquals("Corporate email should match.",
                savedStudent.getCorporateEmail(), foundStudent.getCorporateEmail());
    }

    @Test
    void testUpdateStudent() {
        String newEmail = "shevchenko.taras@cs.khpi.edu.ua";
        Student savedStudent = studentService.saveStudent(testStudent);
        savedStudent.setCorporateEmail(newEmail);
        Student updatedStudent = studentService.saveStudent(savedStudent);

        Student foundStudent = studentService.findStudentById(savedStudent.getId());
        assertNotNull("Updated Student should be found by ID.", foundStudent);
        assertEquals("Corporate email should be updated.",
                newEmail, foundStudent.getCorporateEmail());
    }

    @Test
    void testDeleteStudent() {
        Student savedStudent = studentService.saveStudent(testStudent);
        Long id = savedStudent.getId();
        studentService.deleteStudent(savedStudent.getId());

        assertThrows(CustomServiceException.class, () -> {
            studentService.findStudentById(id);
        }, "Student should not be found after deletion.");
    }

    @Test
    void testSaveStudentWithDuplicateCorporateEmail() {
        studentService.saveStudent(testStudent);
        Student duplicateStudent = new Student(testEntrant, "бюджет", "звичайна", "franko.ivan@cs.khpi.edu.ua");

        assertThrows(CustomServiceException.class, () -> {
            studentService.saveStudent(duplicateStudent);
        }, "Duplicate corporate email should throw CustomServiceException.");
    }
}

