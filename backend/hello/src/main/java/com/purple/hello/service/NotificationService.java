package com.purple.hello.service;

import com.purple.hello.dto.tool.CommonNotificationDTO;
import com.purple.hello.dto.tool.NotificationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    int sendNotifications(List<NotificationDTO> notificationDTOS);
    int sendCommonNotifications(List<CommonNotificationDTO> commonNotificationDTOS);
    void sendCommonNotifications(List<String> tokenList, String title, String content);
}
