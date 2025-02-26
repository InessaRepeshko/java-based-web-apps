package ntukhpi.csit.semit.riv.webappsrivlab4;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * Unit test class for the `UserService` class.
 * This class tests the functionality of the `UserService` in managing `UserEntity` entities.
 * It verifies the basic CRUD operations and ensures that constraints like duplicate usernames or corporate emails are enforced.
 * <p>
 * Key functionalities tested:
 * - Creating and saving a user.
 * - Reading a user by ID and verifying that the username matches.
 * - Updating a user and ensuring the changes are saved correctly.
 * - Deleting a user and ensuring it cannot be found afterward.
 * - Checking that attempting to save a user with a duplicate corporate email throws an exception.
 * <p>
 * Each test method ensures that the `UserService` behaves as expected and performs the required validation and exception handling.
 * <p>
 * Test annotations:
 * - `@BeforeEach` to set up the test data before each test method.
 * - `@AfterEach` to clean up any test data after each test method.
 * <p>
 * Dependencies:
 * - `UserService`: Provides methods for handling `UserEntity` entities.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see UserService
 * @see CustomServiceException
 */

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity(
                "regina.marchuk",
                "Admin123!",
                Role.ADMIN,
                "regina.marchuk@cs.khpi.edu.ua",
                "Марчук",
                "Регіна",
                "Віталіївна");
    }

    @AfterEach
    void reset() {
        if (testUser.getId() != null) {
            userService.deleteUserById(testUser.getId());
        }
    }

    @Test
    void testCreateUser() {
        UserEntity savedUser = userService.saveUser(testUser);
        assertNotNull("User should have an ID after saving.", savedUser.getId());
    }

    @Test
    void testReadUser() {
        UserEntity savedUser = userService.saveUser(testUser);
        UserEntity foundUser = userService.findUserById(savedUser.getId());
        assertNotNull("User should be found by ID.", foundUser);
        assertEquals("Username should match.",
                savedUser.getUsername(), foundUser.getUsername());
    }

    @Test
    void testUpdateUser() {
        String newPassword = "Newadm123!";
        UserEntity savedUser = userService.saveUser(testUser);
        savedUser.setPassword(newPassword);
        UserEntity updatedUser = userService.updateUser(savedUser);

        assertEquals("Password should be updated.",
                savedUser.getPassword(), updatedUser.getPassword());
    }

    @Test
    void testSaveUserWithDuplicateUsername() {
        userService.saveUser(testUser);
        UserEntity duplicateUser = new UserEntity(
                "regina.marchuk",
                "Admin123!",
                Role.ADMIN,
                "regina.marchukkk@cs.khpi.edu.ua",
                "Марчук",
                "Регіна",
                "Віталіївна");

        assertThrows(CustomServiceException.class, () -> {
            userService.saveUser(duplicateUser);
        }, "Duplicate username should throw CustomServiceException.");
    }

    @Test
    void testSaveUserWithDuplicateCorporateEmail() {
        userService.saveUser(testUser);
        UserEntity duplicateUser = new UserEntity(
                "regina.marchukkk",
                "Admin123!",
                Role.ADMIN,
                "regina.marchuk@cs.khpi.edu.ua",
                "Марчук",
                "Регіна",
                "Віталіївна");

        assertThrows(CustomServiceException.class, () -> {
            userService.saveUser(duplicateUser);
        }, "Duplicate corporate email should throw CustomServiceException.");
    }

    @Test
    void testDeleteUser() {
        UserEntity savedUser = userService.saveUser(testUser);
        Long id = savedUser.getId();
        userService.deleteUserById(id);

        assertThrows(CustomServiceException.class, () -> {
            userService.findUserById(id);
        }, "User should not be found after deletion.");
    }
}

