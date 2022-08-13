package com.axilog.cov.service;

import com.axilog.cov.domain.User;
import com.axilog.cov.dto.command.QuotationCommand;
import io.github.jhipster.config.JHipsterProperties;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class OtpMailService {
    private final Logger log = LoggerFactory.getLogger(OtpMailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    @Autowired
    private JHipsterProperties jHipsterProperties;

    @Autowired
    private JavaMailSender otpMailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${otpSourceEmail}")
    private String otpSourceEmail;

    @Value("${activationContactEmail}")
    private String activationContactEmail;

    @Value("${logoImage}")
    private String logoImage;

    @Value("${servicesImage}")
    private String servicesImage;

    @Async
    public void sendEmailWithAttachment(String to, String subject, String content, boolean isMultipart, boolean isHtml, File fileToAttach) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = otpMailSender.createMimeMessage();
        //FileSystemResource file = new FileSystemResource(new File(fileToAttach));
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(otpSourceEmail);
            message.setSubject(subject);
            message.setText(content, isHtml);
            message.addAttachment("invoice.pdf", fileToAttach);
            otpMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailWithAttachmentAndMultiple(String[] to, String subject, String content, boolean isMultipart, boolean isHtml, File fileToAttach) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = otpMailSender.createMimeMessage();
        FileSystemResource file = new FileSystemResource(fileToAttach);
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(otpSourceEmail);
            message.setSubject(subject);
            message.setText(content, isHtml);
            message.addAttachment("orders.pdf", file);
            otpMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = otpMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(otpSourceEmail);
            message.setSubject(subject);
            message.setText(content, isHtml);
            otpMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromQuotationTemplate(QuotationCommand quotationCommand, String templateName, String titleKey) {
        if (quotationCommand.getCustomerEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", quotationCommand.getCustomerName());
            return;
        }
        Context context = new Context(Locale.getDefault());
        context.setVariable("quotation", quotationCommand);
        context.setVariable("servicesImage", servicesImage);
        context.setVariable("logoImage", logoImage);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, Locale.getDefault());
        sendEmail(quotationCommand.getCustomerEmail(), subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable("activationContactEmail", activationContactEmail);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }
}
