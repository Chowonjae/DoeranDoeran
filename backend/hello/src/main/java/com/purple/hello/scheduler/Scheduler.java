package com.purple.hello.scheduler;

import com.purple.hello.dto.tool.NotificationDTO;
import com.purple.hello.service.HistoryService;
import com.purple.hello.service.NotificationService;
import com.purple.hello.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class Scheduler {
    private final NotificationService notificationService;
    private final HistoryService historyService;
    private final RoomService roomService;
    private final int EXPIRATION_PERIOD = 12;
    public Scheduler(NotificationService notificationService, HistoryService historyService, RoomService roomService) {
        this.notificationService = notificationService;
        this.historyService = historyService;
        this.roomService = roomService;
    }
    @Scheduled(cron = "0 0 23,0,1,2 * * ?")
    public void createQuestionAlarm() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int beginTime = localDateTime.plusHours(9).getHour();
        try {
            List<NotificationDTO> notificationDTOS = historyService.createNewQuestionNotificationsByBeginTime(beginTime);
            int notificationCount = notificationService.sendNotifications(notificationDTOS);
            log.info(notificationCount + " NewQuestionNotifications sent");
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
    @Scheduled(cron = "0 0 10,11,12,13 * * ?")
    public void remindQuestionAlarm() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int beginTime = localDateTime.plusHours(9).getHour() - EXPIRATION_PERIOD + 1;
        try {
            List<NotificationDTO> notificationDTOS = historyService.createRemindQuestionNotificationsByBeginTime(beginTime);
            int notificationCount = notificationService.sendNotifications(notificationDTOS);
            log.info(notificationCount + " RemindQuestionNotifications sent");
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
   @Scheduled(cron = "0 0 15 * * ?")
    public void createQuestion() {
        try {
            roomService.createQuestion();
            log.info("Questions Created");
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
