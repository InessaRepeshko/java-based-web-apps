package ntukhpi.csit.semit.riv.webappsrivlab4.service.mail;

import org.springframework.scheduling.annotation.Async;

/**
 * Service interface for managing email sending operations.
 * This interface defines asynchronous methods for sending emails,
 * including support for HTML content and attachments.
 * <p>
 * Key functionalities:
 * - Send plain text or HTML emails to specified recipients.
 * - Support sending emails with file attachments.
 * - Utilize asynchronous processing to avoid blocking operations.
 * <p>
 * Implementation details:
 * - The `@Async` annotation ensures non-blocking email sending.
 * - Emails can include dynamically generated content and attachments.
 * <p>
 * Validation and Usage:
 * - Enforces proper handling of email parameters such as receiver, subject, and content.
 * <p>
 * Dependencies:
 * - `String` for email parameters like subject, content, and receiver.
 * - `byte[]` for handling attachment content.
 *
 * @author Inessa Repeshko CS-222a
 * @see MailService
 * @see Async
 */

public interface MailService {
    @Async
    void sendEmail(String receiver, String subject, String content, boolean isHtmlContent);

    @Async
    void sendEmailWithAttachment(String receiver, String subject, String content, boolean isHtmlContent, String attachmentName, byte[] attachmentContent);
}
