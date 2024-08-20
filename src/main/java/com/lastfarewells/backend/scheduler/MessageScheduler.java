package com.lastfarewells.backend.scheduler;

import com.lastfarewells.backend.constants.LFareWellConstants;
import com.lastfarewells.backend.dto.EmailMessage;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.repository.MessagesRepository;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.EmailService;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageScheduler {

    private final MessagesRepository messagesRepository;
    private final UsersRepository    usersRepository;
    private final EmailService       emailService;

    @Value("${spring.mail.from}")
    private String fromAddress;

    //@Scheduled(cron = "0 0/15 * * * ?")
    @Scheduled(fixedDelay = 10000)
    @SchedulerLock(name = "MessageScheduler.sendScheduleMessages", lockAtLeastFor = "PT2H", lockAtMostFor = "PT5H")
    @Transactional
    public void sendScheduleMessages() {
        log.info("Scheduler for sending Messages : " + LocalDateTime.now());

        List<Messages> todaysMessages = messagesRepository.findAllByDeliveryDate(new Date());
        todaysMessages.forEach(message -> {
            messageBuilderAndSend(message);
        });
    }

    private void messageBuilderAndSend(Messages message) {
        Optional<Users> sender = usersRepository.findById(message.getUserId());
        if (!sender.isPresent()) {
            log.error("Cannot send mail for message Id {}, since sender user is not present", message.getId());
            return;
        }

        String toAddress = "";
        // Send email to Messenger
        Map<String, Object> props = new HashedMap<>();
        if (message.getMessenger() == null) {
            props.put(LFareWellConstants.RECIPIENT,
                message.getRecipient().getFirstName() + (StringUtils.isNotEmpty(message.getRecipient().getLastName()) ? " " + message.getRecipient()
                    .getLastName() : ""));
            toAddress = message.getRecipient().getEmail();
        } else {
            props.put(LFareWellConstants.RECIPIENT,
                message.getMessenger().getFirstName() + (StringUtils.isNotEmpty(message.getMessenger().getLastName()) ? " " + message.getMessenger()
                    .getLastName() : ""));
            toAddress = message.getMessenger().getEmail();
        }

        if (StringUtils.isEmpty(toAddress)) {
            log.error("Cannot send mail for message Id {}, since toAddress is empty", message.getId());
            return;
        }

        props.put(LFareWellConstants.USER_NAME,
            sender.get().getFirstName() + (StringUtils.isNotEmpty(sender.get().getLastName()) ? " " + sender.get().getLastName() : ""));
        props.put(LFareWellConstants.CONTENT, StringUtils.isNotEmpty(message.getContent()) ? message.getContent() : "Message added as attachment");

        EmailMessage emailMsg = new EmailMessage(fromAddress, toAddress, message.getTitle(), LFareWellConstants.MESSENGER_INVITATION_SUBJECT, props);

        emailService.sendEmail(emailMsg);
    }

}
