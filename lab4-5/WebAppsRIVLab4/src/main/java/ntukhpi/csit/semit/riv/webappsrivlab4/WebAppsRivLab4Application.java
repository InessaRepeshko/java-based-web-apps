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
import org.springframework.core.env.Environment;

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
    private final Environment environment;

    @Autowired
    public WebAppsRivLab4Application(EntrantService entrantService, StudentService studentService, UserService userService, Environment environment) {
        this.entrantService = entrantService;
        this.studentService = studentService;
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public void run(String... args) {
        // Ініціалізація тестових даних тільки для development профілю
        String[] activeProfiles = environment.getActiveProfiles();
        boolean isProductionMode = false;
        
        for (String profile : activeProfiles) {
            if ("prod".equals(profile)) {
                isProductionMode = true;
                break;
            }
        }
        
        if (isProductionMode) {
            logger.info("Production mode detected. Skipping test data initialization.");
            return;
        }
        
        // Перевіряємо, чи вже є дані в базі
        try {
            List<UserEntity> existingUsers = userService.getAllUsers();
            if (!existingUsers.isEmpty()) {
                logger.info("Database already contains user data. Skipping test data initialization.");
                return;
            }
        } catch (Exception e) {
            logger.warn("Could not check existing users, proceeding with initialization: " + e.getMessage());
        }
        
        logger.info("Initializing test data...");
        
        try {
            List<UserEntity> userEntityList = UserList.INSTANCE.getUsers().stream()
                    .map(UserEntity::getUserEntityFromUserTestData)
                    .toList();
            userService.saveAllUsers(userEntityList);
            logger.info("User test data saved successfully.");

            List<Entrant> entrantList = EntrantList.INSTANCE.getEntrants();
            entrantService.saveAllEntrants(entrantList);
            logger.info("Entrant test data saved successfully.");

            studentService.saveAllStudents(StudentList.INSTANCE.getStudents());
            logger.info("Student test data saved successfully.");

            entrantList.forEach(entrant -> {
                entrant.setStudentId(
                        entrantService.findStudentIdForEntrant(entrant.getId()));
                entrantService.updateEntrant(entrant);
            });
            logger.info("Test data initialization completed successfully.");
            
        } catch (Exception e) {
            logger.error("Error during test data initialization: " + e.getMessage());
            // У production режимі не падаємо, просто логуємо помилку
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(WebAppsRivLab4Application.class, args);
    }
}
