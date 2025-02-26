package ntukhpi.csit.semit.riv.webappsrivlab3;

import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.EntrantList;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.student.StudentList;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class WebAppsRivLab3Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(WebAppsRivLab3Application.class);
    private final EntrantService entrantService;
    private final StudentService studentService;

    @Autowired
    public WebAppsRivLab3Application(EntrantService entrantService, StudentService studentService) {
        this.entrantService = entrantService;
        this.studentService = studentService;
    }

    @Override
    public void run(String... args) {
        List<Entrant> entrantList = EntrantList.INSTANCE.getEntrants();
        entrantService.saveAllEntrants(entrantList);

        List<Student> studentList = StudentList.INSTANCE.getStudents();
        studentService.saveAllStudents(studentList);

        entrantList.forEach(entrant -> {
            entrant.setStudentId(
                    entrantService.findStudentIdForEntrant(entrant.getId()));
            entrantService.updateEntrant(entrant);
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(WebAppsRivLab3Application.class, args);
    }
}
