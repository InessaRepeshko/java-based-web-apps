package ntukhpi.csit.semit.riv.webappsrivlab4.service.mail.implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Service implementation for sending emails.
 * This class provides functionalities to send emails with optional HTML content
 * and attachments using Spring's `JavaMailSender`.
 * <p>
 * Key functionalities:
 * - Send basic emails with plain text or HTML content.
 * - Send emails with attachments.
 * - Exception handling to ensure robust error management during the email-sending process.
 * <p>
 * Implementation details:
 * - Leverages `MimeMessage` and `MimeMessageHelper` for email creation.
 * - Supports both plain text and HTML-formatted email content.
 * - Allows adding attachments to emails using `ByteArrayResource`.
 * <p>
 * Dependencies:
 * - Uses `JavaMailSender` for sending emails.
 *
 * @author Inessa Repeshko CS-222a
 * @see MailService
 * @see MimeMessage
 * @see MimeMessageHelper
 * @see ByteArrayResource
 * @see CustomServiceException
 * @see JavaMailSender
 */

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String receiver,
                          String subject,
                          String content,
                          boolean isHtmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, isHtmlContent);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new CustomServiceException("Failed to send a password reset email. " +
                    "An error occurred while generating the email.");
        }
    }

    @Override
    public void sendEmailWithAttachment(String receiver,
                                        String subject,
                                        String content,
                                        boolean isHtmlContent,
                                        String attachmentName,
                                        byte[] attachmentContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, isHtmlContent);

            ByteArrayResource resource = new ByteArrayResource(attachmentContent);
            helper.addAttachment(attachmentName, resource);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new CustomServiceException("An error occurred while generating the email.");
        }
    }
}

