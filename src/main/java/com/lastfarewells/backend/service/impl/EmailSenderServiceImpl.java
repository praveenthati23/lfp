package com.lastfarewells.backend.service.impl;


import com.lastfarewells.backend.dto.EmailMessage;
import com.lastfarewells.backend.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailService {


    private final JavaMailSender mailSender;


    private final SpringTemplateEngine springTemplateEngine;

    @Async
    public void sendEmail(EmailMessage emailMessage) {
        log.info("Sending mail {}", emailMessage.getTo());
        String content = getHtmlContent(emailMessage);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(emailMessage.getFrom());
            helper.setTo(emailMessage.getTo());
            helper.setSubject(emailMessage.getSubject());
            helper.setText(content, true);
            mailSender.send(message);

        } catch (Exception e) {
            log.error("Error while sending email: " + e.getStackTrace());
        }

    }

    private String getHtmlContent(EmailMessage emailMessage) {
        Context context = new Context();
        context.setVariables(emailMessage.getProps());
        return springTemplateEngine.process(emailMessage.getTemplateName(), context);
    }

}
