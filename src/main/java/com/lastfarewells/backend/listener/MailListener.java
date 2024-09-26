package com.lastfarewells.backend.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastfarewells.backend.constants.LFareWellConstants;
import com.lastfarewells.backend.dto.SqsNotificationDto.BouncedRecipients;
import com.lastfarewells.backend.dto.SqsNotificationDto.CommonHeaders;
import com.lastfarewells.backend.dto.SqsNotificationDto.Message;
import com.lastfarewells.backend.entity.FailedEmails;
import com.lastfarewells.backend.repository.FailedEmailsRepository;
import com.lastfarewells.backend.service.MessagesService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailListener {


    private final FailedEmailsRepository failedEmailsRepo;
    private final MessagesService        messagesService;

    @SqsListener("${sqs.queueName}")
    public void receiveMessage(String message) {
        log.info("Failed Message Handler for message : {}", message);
        List<FailedEmails> failedMessages = convertMessageToFailedEmail(message);
        failedEmailsRepo.saveAll(failedMessages);

        for (FailedEmails failedMessage : failedMessages) {
            log.info("Failed Message Saved for email: " + failedMessage.getEmailAddress() + " Subject: "
                + failedMessage.getSubject());
            messagesService.updateMessageStatusForEmail(failedMessage.getEmailAddress());
        }
    }

    public List<FailedEmails> convertMessageToFailedEmail(String notificationMsg) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<FailedEmails> failedEmailsList = new ArrayList();
        try {
            JsonNode jsonNode = objectMapper.readTree(notificationMsg);
            String message = jsonNode.get(LFareWellConstants.MESSAGE).asText();
            Message messageObj = objectMapper.readValue(message, Message.class);
            String mailObj = objectMapper.writeValueAsString(messageObj.getMail());
            JsonNode mailNode = objectMapper.readTree(mailObj);
            String commonHeadersStr = mailNode.get(LFareWellConstants.COMMONHEADERS).toPrettyString();
            CommonHeaders commonHeadersObj = objectMapper.readValue(commonHeadersStr, CommonHeaders.class);
            List<BouncedRecipients> bounceRecipients = messageObj.getBounce().getBouncedRecipients();
            String dateTimeString = mailNode.get(LFareWellConstants.TIMESTAMP).asText();
            Instant date = Instant.parse(dateTimeString);
            for (BouncedRecipients bounce : bounceRecipients) {
                FailedEmails failedMessage = FailedEmails.builder().notificationType(messageObj.getNotificationType())
                    .emailAddress(bounce.getEmailAddress()).action(bounce.getAction())
                    .diagnosticCode(bounce.getDiagnosticCode()).subject(commonHeadersObj.getSubject()).date(date)
                    .createdOn(Instant.now()).updatedOn(Instant.now()).build();
                failedEmailsList.add(failedMessage);
            }

        } catch (Exception e) {
            log.error("Exception while convert SQS Message To FailedEmail " + e.getMessage());
        }
        return failedEmailsList;

    }

}
