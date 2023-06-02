package com.purple.hello.service.impl;

import com.google.firebase.messaging.*;
import com.purple.hello.dto.tool.CommonNotificationDTO;
import com.purple.hello.dto.tool.NotificationDTO;
import com.purple.hello.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public int sendNotifications(List<NotificationDTO> notificationDTOS) {
        List<Message> messages = notificationDTOS.stream().map((notificationDTO) -> Message.builder()
                   .putData("time", LocalDateTime.now().toString())
                   .putData("title", notificationDTO.getTitle())
                   .putData("body", notificationDTO.getContent())
                   .setToken(notificationDTO.getDeviceToken())
                   .build()).collect(Collectors.toList());

        // 요청에 대한 응답을 받을 response
        BatchResponse response;
        try {
            // 알림 발송
            response = FirebaseMessaging.getInstance().sendAll(messages);
            // 요청에 대한 응답 처리
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        failedTokens.add(notificationDTOS.get(i).getDeviceToken());
                    }
                }
                log.error("List of tokens are not valid FCM token : " + failedTokens);
            }
        } catch (FirebaseMessagingException e) {
            log.error("cannot send to memberList push message. error info : {}", e.getMessage());
        }

        return notificationDTOS.size();
    }

    @Override
    public int sendCommonNotifications(List<CommonNotificationDTO> commonNotificationDTOS){
        int notificationCount = 0;
        for(CommonNotificationDTO commonNotificationDTO : commonNotificationDTOS) {
            List<String> tokenList = commonNotificationDTO.getTokenList();
            String title = commonNotificationDTO.getTitle();
            String content = commonNotificationDTO.getContent();
            this.sendCommonNotifications(tokenList, title, content);
            notificationCount += tokenList.size();
        }
        return notificationCount;
    }

    @Override
    public void sendCommonNotifications(List<String> tokenList, String title, String body){
        List<Message> messages = tokenList.stream().map(token -> Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .putData("title", title)
                .putData("body", body)
                .setToken(token)
                .build()).collect(Collectors.toList());

        // 요청에 대한 응답을 받을 response
        BatchResponse response;
        try {
            // 알림 발송
            response = FirebaseMessaging.getInstance().sendAll(messages);
            // 요청에 대한 응답 처리
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        failedTokens.add(tokenList.get(i));
                    }
                }
                log.error("List of tokens are not valid FCM token : " + failedTokens);
            }
        } catch (FirebaseMessagingException e) {
            log.error("cannot send to memberList push message. error info : {}", e.getMessage());
        }
    }
}
