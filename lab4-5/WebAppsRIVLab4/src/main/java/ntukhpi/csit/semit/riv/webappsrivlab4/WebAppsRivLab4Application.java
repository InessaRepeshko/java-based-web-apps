package ntukhpi.csit.semit.riv.webappsrivlab4;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.testDataList.entrant.EntrantList;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.testDataList.student.StudentList;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.testDataList.user.UserList;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.StudentService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Main entry point for the Spring Boot application `WebAppsRivLab4Application`.
 * This class is responsible for starting the application, initializing necessary services,
 * and populating the database with initial test data.
 * <p>
 * Key functionalities:
 * - Loads initial data into the application by interacting with the `UserService`, `EntrantService`, and `StudentService`.
 * - Saves test data for users, entrants, and students into the database.
 * - Associates each entrant with a student and updates the entrant records accordingly.
 * <p>
 * Dependencies:
 * - `EntrantService`: Manages operations related to the `Entrant` entities.
 * - `StudentService`: Handles operations related to the `Student` entities.
 * - `UserService`: Manages operations related to the `User` entities.
 * <p>
 * The `run` method is executed on application startup to populate the application with test data, saving all user, entrant, and student records.
 * <p>
 *
 * @author Inessa Repeshko CS-222a
 * @see EntrantService
 * @see StudentService
 * @see UserService
 * @see Entrant
 * @see UserEntity
 * @see Student
 */

@SpringBootApplication
public class WebAppsRivLab4Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(WebAppsRivLab4Application.class);
    private final EntrantService entrantService;
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public WebAppsRivLab4Application(EntrantService entrantService, StudentService studentService, UserService userService) {
        this.entrantService = entrantService;
        this.studentService = studentService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        List<UserEntity> userEntityList = UserList.INSTANCE.getUsers().stream()
                .map(UserEntity::getUserEntityFromUserTestData)
                .toList();
        userService.saveAllUsers(userEntityList);

        List<Entrant> entrantList = EntrantList.INSTANCE.getEntrants();
        entrantService.saveAllEntrants(entrantList);

        studentService.saveAllStudents(StudentList.INSTANCE.getStudents());

        entrantList.forEach(entrant -> {
            entrant.setStudentId(
                    entrantService.findStudentIdForEntrant(entrant.getId()));
            entrantService.updateEntrant(entrant);
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(WebAppsRivLab4Application.class, args);
    }
}
