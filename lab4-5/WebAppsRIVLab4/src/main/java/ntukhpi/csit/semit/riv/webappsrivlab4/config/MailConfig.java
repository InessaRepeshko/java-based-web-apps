package ntukhpi.csit.semit.riv.webappsrivlab4.config;

import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${spring.mail.host:smtp.ethereal.email}")
    private String mailHost;
    
    @Value("${spring.mail.port:587}")
    private int mailPort;
    
    @Value("${spring.mail.username}")
    private String mailUsername;
    
    @Value("${spring.mail.password}")
    private String mailPassword;
    
    @Value("${spring.mail.properties.mail.debug:false}")
    private boolean mailDebug;
    
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.debug", String.valueOf(mailDebug));
        properties.put("mail.smtp.ssl.trust", mailHost);
        properties.put("mail.smtp.timeout", "5000");
        properties.put("mail.smtp.connectiontimeout", "5000");
        properties.put("mail.smtp.writetimeout", "5000");

        return mailSender;
    }
}
