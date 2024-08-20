package com.lastfarewells.backend.scheduler;

import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.repository.MessagesRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageScheduler {

    private final MessagesRepository messagesRepository;

    //@Scheduled(cron = "0 0/15 * * * ?")
    @Scheduled(fixedDelay = 10000)
    @SchedulerLock(name = "MessageScheduler.sendScheduleMessages", lockAtLeastFor = "PT2H", lockAtMostFor = "PT5H")
    @Transactional
    public void sendScheduleMessages() {
        log.info("Scheduler for sending Messages : " + LocalDateTime.now());

        List<Messages> todaysMessages = messagesRepository.findAllByDeliveryDate(new Date());
        todaysMessages.forEach(message -> {

        });
    }

}
