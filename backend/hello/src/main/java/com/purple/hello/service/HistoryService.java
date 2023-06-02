package com.purple.hello.service;

import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.dto.tool.NotificationDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public interface HistoryService {
    ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException, NullPointerException;
    List<NotificationDTO> createNewQuestionNotificationsByBeginTime(int beginTime) throws Exception;
    List<NotificationDTO> createRemindQuestionNotificationsByBeginTime(int beginTime) throws Exception;
    public void createFirstHistory(Long roomId);
}
