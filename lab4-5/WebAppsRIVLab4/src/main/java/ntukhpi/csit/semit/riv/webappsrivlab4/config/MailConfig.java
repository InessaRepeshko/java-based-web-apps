package ntukhpi.csit.semit.riv.webappsrivlab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuration class for setting up the JavaMailSender bean.
 * This class is annotated with @Configuration, indicating that it defines bean configurations
 * for the Spring container to manage and use in the application.
 * <p>
 * The JavaMailSender bean is configured to send emails using the specified SMTP server.
 * It uses the Ethereal email service for testing purposes. Ethereal is a fake SMTP service
 * that allows developers to send test emails without actually delivering them to real recipients.
 * <p>
 * Configuration details include:
 * - SMTP server host and port.
 * - Username and password for authentication.
 * - Additional email properties such as debugging, starttls, and DTO configuration.
 *
 * @author Inessa Repeshko CS-222a
 * @see Configuration
 * @see JavaMailSender
 * @see org.springframework.mail.javamail.JavaMailSenderImpl
 */
@Configuration
public class MailConfig {
    @Bean
    JavaMailSender createMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("wilmer.ankunding@ethereal.email");
        mailSender.setPassword("GVDW5ZTPSSXuAnRh1y");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.debug", true);
        properties.put("mail.smtp.DTO", true);
        properties.put("mail.smtp.starttls.enable", true);

        return mailSender;
    }
}
