package ntukhpi.csit.semit.riv.webappsrivlab4.service.mail.implementation;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.mail.MailContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Service implementation for constructing email content.
 * This service uses Thymeleaf templates to dynamically generate email content based on provided templates and variables.
 * <p>
 * Key functionalities:
 * - Generate generic email content from a specified Thymeleaf template and variables.
 * - Construct email content for password reset requests, including a personalized reset URL.
 * - Construct welcome email content with a setup link for new users.
 * <p>
 * Implementation details:
 * - Integrates with Thymeleaf's `TemplateEngine` to process templates.
 * - Uses a `Context` object to set variables dynamically and bind them to the template.
 * <p>
 * Dependencies:
 * - Relies on Thymeleaf for template rendering.
 * - Accepts domain objects like `UserEntity` to populate email content dynamically.
 *
 * @author Inessa Repeshko CS-222a
 * @see MailContentService
 * @see UserEntity
 * @see TemplateEngine
 * @see Context
 * @see Map
 */

@Service
public class MailContentServiceImpl implements MailContentService {
    private final TemplateEngine templateEngine;

    @Autowired
    public MailContentServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private String constructMailContent(String templateName, Map<String, Object> variables) {
        Context context = new Context();

        context.setVariables(variables);

        return templateEngine.process(templateName, context);
    }

    @Override
    public String constructResetPasswordMailContent(String contextPath, String token, UserEntity userEntity) {
        String url = contextPath + "/change-password?token=" + token;

        Map<String, Object> variables = new HashMap<>();
        variables.put("username", userEntity.getUsername());
        variables.put("resetUrl", url);

        return constructMailContent("mail/ResetPasswordMail", variables);
    }

    @Override
    public String constructWelcomeMailContent(String contextPath, String token, UserEntity userEntity) {
        String url = contextPath + "/change-password?token=" + token;

        Map<String, Object> variables = new HashMap<>();
        variables.put("username", userEntity.getUsername());
        variables.put("resetUrl", url);

        return constructMailContent("mail/WelcomeMail", variables);
    }
}
