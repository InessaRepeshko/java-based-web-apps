package ntukhpi.csit.semit.riv.webappsrivlab4.service.mail;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;

import java.util.Map;

/**
 * Service interface for constructing email content.
 * This interface provides methods for generating email content dynamically
 * based on templates and context variables.
 * <p>
 * Key functionalities:
 * - Construct content for reset password emails.
 * - Construct content for welcome emails for new users.
 * <p>
 * Implementation details:
 * - Relies on a template engine (e.g., Thymeleaf) for generating email content.
 * - Provides flexibility in creating email templates with placeholders for dynamic content.
 * <p>
 * Validation and Usage:
 * - Ensures email content is constructed based on valid templates and user-specific data.
 * <p>
 * Dependencies:
 * - `UserEntity` provides user-specific details for email personalization.
 * - `Map` is used to pass variables for dynamic template processing.
 *
 * @author Inessa Repeshko CS-222a
 * @see MailContentService
 * @see UserEntity
 * @see Map
 */

public interface MailContentService {
    String constructResetPasswordMailContent(String contextPath, String token, UserEntity userEntity);

    String constructWelcomeMailContent(String contextPath, String token, UserEntity userEntity);
}
