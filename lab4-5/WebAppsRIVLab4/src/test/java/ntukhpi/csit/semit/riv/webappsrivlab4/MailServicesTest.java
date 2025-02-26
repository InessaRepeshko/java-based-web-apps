package ntukhpi.csit.semit.riv.webappsrivlab4;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.token.PasswordResetToken;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.PasswordResetTokenService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.mail.MailContentService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.mail.MailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * Unit test class for testing the functionality of the `MailService` and `MailContentService` classes.
 * This class tests the generation of email content and the sending of emails for the password reset and welcome functionalities.
 * It ensures that email content is properly constructed and emails are sent without exceptions.
 * <p>
 * Key functionalities tested:
 * - Generating the content for the password reset email.
 * - Verifying that the generated email content contains necessary information like the username and reset URL.
 * - Sending a password reset email and verifying that no exceptions occur during the process.
 * - Generating and sending a welcome email with similar verifications as the password reset functionality.
 * - Sending a basic email and an email with an attachment to ensure that the service works as expected.
 * <p>
 * Each test method verifies that the `MailService` and `MailContentService` behave as expected, focusing on email content creation and successful email dispatch.
 * <p>
 * Test annotations:
 * - `@BeforeEach` to set up necessary test data before each test method.
 * - `@AfterEach` to clean up the test data after each test method.
 * <p>
 * Dependencies:
 * - `MailContentService`: Responsible for constructing email content for different scenarios.
 * - `MailService`: Provides methods for sending emails, including those with attachments.
 * - `UserService`: Used to retrieve user data for the email content.
 * - `PasswordResetTokenService`: Used to manage password reset tokens for the user.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see UserService
 * @see PasswordResetToken
 * @see PasswordResetTokenService
 * @see MailContentService
 * @see MailService
 */

@SpringBootTest
class MailServicesTest {

    @Autowired
    private MailContentService mailContentService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService tokenService;

    private UserEntity testUser;

    private PasswordResetToken testToken;

    @BeforeEach
    void setUp() {
        testUser = userService.findUserById(1L);

        PasswordResetToken testTokenData = new PasswordResetToken(testUser);
        testToken = tokenService.saveToken(testTokenData);
    }

    @AfterEach
    void reset() {
        if (testToken.getId() != null) {
            tokenService.deleteTokenById(testToken.getId());
        }
    }

    @Test
    void testConstructAndSendResetPasswordMailContent() {
        String contextPath = "http://localhost";
        String content = mailContentService.constructResetPasswordMailContent(
                contextPath,
                testToken.getToken(),
                testUser
        );

        assertNotNull(content, "Reset password email content should be generated.");

        String resetUrl = contextPath + "/change-password?token=" + testToken.getToken();

        assertTrue("The password reset email should contain the username.",
                content.contains(testUser.getUsername()));
        assertTrue("The password reset email should contain the link to reset password.",
                content.contains(resetUrl));

        assertDoesNotThrow(() ->
                mailService.sendEmail(
                        testUser.getCorporateEmail(),
                        "TEST | Reset Password Mail",
                        content,
                        true),
                "Email should be sent without exceptions.");
    }

    @Test
    void testConstructAndSendWelcomeMailContent() {
        String contextPath = "http://localhost";
        String content = mailContentService.constructWelcomeMailContent(
                contextPath,
                testToken.getToken(),
                testUser
        );

        assertNotNull(content, "Welcome email content should be generated.");

        String resetUrl = contextPath + "/change-password?token=" + testToken.getToken();

        assertTrue("The password reset email should contain the username.",
                content.contains(testUser.getUsername()));
        assertTrue("The password reset email should contain the link to reset password.",
                content.contains(resetUrl));

        assertDoesNotThrow(() ->
                        mailService.sendEmail(
                                testUser.getCorporateEmail(),
                                "TEST | Welcome Mail",
                                content,
                                true),
                "Email should be sent without exceptions.");
    }

    @Test
    void testSendEmail() {
        assertDoesNotThrow(() -> {
            mailService.sendEmail("test@example.com", "Test Subject", "Test Content", false);
        }, "Email should be sent without exceptions.");
    }

    @Test
    void testSendEmailWithAttachment() {
        byte[] attachmentContent = "Test Attachment".getBytes();
        assertDoesNotThrow(() -> {
            mailService.sendEmailWithAttachment(
                    "test@example.com",
                    "Test Subject",
                    "Test Content",
                    false,
                    "test.txt",
                    attachmentContent
            );
        }, "Email with attachment should be sent without exceptions.");
    }
}

