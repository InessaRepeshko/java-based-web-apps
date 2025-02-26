package ntukhpi.csit.semit.riv.webappsrivlab4.controller;

import jakarta.servlet.http.HttpServletRequest;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.token.PasswordResetToken;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.PasswordResetTokenService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.mail.MailContentService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for managing email-related operations.
 * This class provides static methods for sending different types of emails, such as
 * welcome emails and password reset emails, as well as an example endpoint for sending test emails.
 * <p>
 * Key functionalities:
 * - Sending a welcome email to newly registered users.
 * - Sending a password reset email when users request password recovery.
 * - Constructing email content dynamically based on user data and application URLs.
 * - Providing an example endpoint to test email sending functionality.
 * <p>
 * Dependencies:
 * - JavaMailSender: Handles sending of email messages.
 * - UserService: Provides user-specific data for constructing personalized email content.
 * - PasswordResetTokenService: Manages password reset tokens for email verification.
 * - MailContentService: Constructs email content templates.
 * - MailService: Manages the email sending process with additional configurations.
 * <p>
 * This controller integrates with Spring's mail-sending capabilities and supports
 * both static and instance methods to ensure flexibility in email-related operations.
 *
 * @author Inessa Repeshko CS-222a
 * @see JavaMailSender
 * @see UserService
 * @see PasswordResetTokenService
 * @see MailContentService
 * @see MailService
 * @see PasswordResetToken
 * @see HttpServletRequest
 * @see UserEntity
 * @see SimpleMailMessage
 */

@Controller
public class MailController {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);
    private final JavaMailSender mailSender;
    private final UserService userService;
    private static PasswordResetTokenService tokenService;
    private static MailContentService mailContentService;
    private static MailService mailService;

    @Autowired
    public MailController(JavaMailSender mailSender,
                          UserService userService,
                          PasswordResetTokenService tokenService,
                          MailContentService mailContentService,
                          MailService mailService
    ) {
        this.mailSender = mailSender;
        this.userService = userService;
        MailController.tokenService = tokenService;
        MailController.mailContentService = mailContentService;
        MailController.mailService = mailService;
    }

    public UserEntity getCurrentUser() {
        return userService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication()
                        .getName());
    }

    public static String getAppURL(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        return scheme + "://" + serverName + ":" + serverPort;
    }


    public static void sendWelcomeMail(UserEntity userEntity, HttpServletRequest request) {
        if (userEntity != null) {
            PasswordResetToken token = tokenService.saveToken(new PasswordResetToken(userEntity));

            String mailContent = mailContentService.constructWelcomeMailContent(
                    MailController.getAppURL(request),
                    token.getToken(),
                    userEntity
            );

            mailService.sendEmail(
                    userEntity.getCorporateEmail(),
                    "Set Up Your Password",
                    mailContent,
                    true
            );
        }
    }

    public static void sendResetPasswordMail(UserEntity userEntity, HttpServletRequest request) {
        if (userEntity != null) {
            PasswordResetToken token = tokenService.saveToken(new PasswordResetToken(userEntity));

            String mailContent = mailContentService.constructResetPasswordMailContent(
                    MailController.getAppURL(request),
                    token.getToken(),
                    userEntity
            );

            mailService.sendEmail(
                    userEntity.getCorporateEmail(),
                    "Password Reset Request",
                    mailContent,
                    true
            );
        }
    }

    @GetMapping("/send-email")
    public String sendMailExample() {
        String FROM_EMAIL = "noreply@ntu.khpi.edu.ua";
        UserEntity userEntity = getCurrentUser();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(userEntity.getCorporateEmail());
        message.setSubject("Test Message Subject");
        message.setText("Test Message Text");

        mailSender.send(message);

        return "redirect:/home";
    }
}
