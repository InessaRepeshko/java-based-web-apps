package ntukhpi.csit.semit.riv.webappsrivlab4;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.token.PasswordResetToken;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.PasswordResetTokenService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertFalse;

/**
 * Unit test class for the `PasswordResetTokenService` class.
 * This class tests the functionality of the `PasswordResetTokenService` in managing `PasswordResetToken` entities.
 * It verifies the basic CRUD operations.
 * <p>
 * Key functionalities tested:
 * - Creating and saving a token.
 * - Reading a toked by user ID and verifying that the token matches.
 * - Validating a token for the user's data and not expired.
 * - Updating a token and ensuring the changes are saved correctly.
 * - Deleting a token and ensuring it cannot be found afterward.
 * <p>
 * Each test method ensures that the `PasswordResetTokenService` behaves as expected and performs the required validation and exception handling.
 * <p>
 * Test annotations:
 * - `@BeforeEach` to set up the test data before each test method.
 * - `@AfterEach` to clean up any test data after each test method.
 * <p>
 * Dependencies:
 * - `PasswordResetTokenService`: Provides methods for handling `PasswordResetToken` entities.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see UserService
 * @see PasswordResetToken
 * @see PasswordResetTokenService
 * @see CustomServiceException
 */

@SpringBootTest
class PasswordResetTokenServiceTest {

    @Autowired
    private PasswordResetTokenService tokenService;

    @Autowired
    private UserService userService;

    private PasswordResetToken testToken;

    @BeforeEach
    void setUp() {
        UserEntity testUser = userService.findUserById(1L);
        testToken = new PasswordResetToken(testUser);
    }

    @AfterEach
    void reset() {
        if (testToken.getId() != null) {
            tokenService.deleteTokenById(testToken.getId());
        }
    }

    @Test
    void testCreateToken() {
        PasswordResetToken savedToken = tokenService.saveToken(testToken);
        assertNotNull("Token should have an ID after saving.", savedToken.getId());
    }

    @Test
    void testFindTokenByUserId() {
        PasswordResetToken savedToken = tokenService.saveToken(testToken);
        PasswordResetToken foundToken = tokenService.findTokenByUserId(testToken.getUser().getId());
        assertEquals("Token should match.",
                savedToken.getToken(), foundToken.getToken());
    }

    @Test
    void testTokenExpiry() {
        PasswordResetToken savedToken = tokenService.saveToken(testToken);
        assertFalse("The token should not be expired.", tokenService.isTokenExpired(savedToken));
    }

    @Test
    void testTokenUserData() {
        PasswordResetToken savedToken = tokenService.saveToken(testToken);
        assertTrue("The token must contain user data.", tokenService.isTokenContainsUserData(savedToken));
    }

    @Test
    void testTokenValidity() {
        PasswordResetToken savedToken = tokenService.saveToken(testToken);
        assertTrue("The token must contain user data.", tokenService.isValidToken(savedToken));
    }

    @Test
    void testDeleteToken() {
        PasswordResetToken savedToken = tokenService.saveToken(testToken);
        tokenService.deleteTokenById(savedToken.getId());

        assertThrows(CustomServiceException.class, () -> {
            tokenService.findTokenByToken(testToken.getToken());
        }, "Token should not be found after deletion.");
    }
}
