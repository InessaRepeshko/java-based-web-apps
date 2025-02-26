package ntukhpi.csit.semit.riv.webappsrivlab3;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityNotFoundException;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.EntrantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.StudentService;

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
        studentService.deleteStudent(testStudent);
    }

    @Test
    void testCreateStudent() {
        Student savedStudent = studentService.saveStudent(testStudent);
        assertNotNull(savedStudent.getId(), "Студент повинен мати ID після збереження.");
    }

    @Test
    void testReadStudent() {
        Student savedStudent = studentService.saveStudent(testStudent);
        Student foundStudent = studentService.findStudentById(savedStudent.getId());
        assertNotNull(foundStudent, "Студента має бути знайдено за ID.");
        assertEquals(savedStudent.getCorporateEmail(), foundStudent.getCorporateEmail(), "Корпоративна пошта повинна збігатися.");
    }

    @Test
    void testUpdateStudent() {
        String newEmail = "shevchenko.taras@cs.khpi.edu.ua";
        Student savedStudent = studentService.saveStudent(testStudent);
        savedStudent.setCorporateEmail(newEmail);
        Student updatedStudent = studentService.saveStudent(savedStudent);

        Student foundStudent = studentService.findStudentById(savedStudent.getId());
        assertNotNull(foundStudent, "Оновленого студента має бути знайдено за ID.");
        assertEquals(newEmail, foundStudent.getCorporateEmail(), "Корпоративна пошта має бути оновлена.");
    }

    @Test
    void testDeleteStudent() {
        Student savedStudent = studentService.saveStudent(testStudent);
        Long id = savedStudent.getId();
        studentService.deleteStudent(savedStudent);

        assertThrows(EntityNotFoundException.class, () -> {
            studentService.findStudentById(id);
        }, "Студента не повинно бути знайдено після видалення.");
    }

    @Test
    void testSaveStudentWithDuplicateCorporateEmail() {
        studentService.saveStudent(testStudent);
        Student duplicateStudent = new Student(testEntrant, "бюджет", "звичайна", "franko.ivan@cs.khpi.edu.ua");

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.saveStudent(duplicateStudent);
        }, "Дубльована корпоративна пошта має викликати IllegalArgumentException.");
    }
}

