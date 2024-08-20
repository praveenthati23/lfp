package com.lastfarewells.backend.scheduler;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageScheduler {

    @Scheduled(cron = "0 0/1 * * * ?")
    @SchedulerLock(name = "MessageScheduler.sendScheduleMessages", lockAtLeastFor = "PT2H", lockAtMostFor = "PT5H")
    public void sendScheduleMessages() {
        log.info("Scheduler for sending Messages : " + LocalDateTime.now());

    }

}
