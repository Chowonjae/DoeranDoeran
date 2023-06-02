package com.purple.hello.service.impl;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.dto.tool.DeviceWithQuestionDTO;
import com.purple.hello.dto.tool.NotificationDTO;
import com.purple.hello.service.HistoryService;
import com.purple.hello.util.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final RoomDAO roomDAO;
    private final HistoryDAO historyDAO;

    public HistoryServiceImpl(RoomDAO roomDAO, HistoryDAO historyDAO) {
        this.roomDAO = roomDAO;
        this.historyDAO = historyDAO;
    }

    @Override
    public ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException, NullPointerException {
        if(date == null)
            throw new IllegalArgumentException();

        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = DateUtils.addHours(new Date(), 9);
            date = DateUtils.addHours(date, 9);
            if(currentDate.compareTo(date) < 0)
                throw new IllegalArgumentException();

            if(simpleDateFormat.format(currentDate).equals(simpleDateFormat.format(date))) {
                int beginTime = roomDAO.readBeginTimeByRoomId(roomId);
                int currentHour = LocalDateTime.now().plusHours(9).getHour();

                if(currentHour < beginTime)
                    throw new IllegalArgumentException();
            }
            return historyDAO.readQuestionByRoomIdAndDate(roomId, date);
        }
    }

    @Override
    public List<NotificationDTO> createNewQuestionNotificationsByBeginTime(int beginTime) throws Exception {
        List<DeviceWithQuestionDTO> deviceWithQuestionDTOS = historyDAO.readDevicesWithDailyQuestionByBeginTime(beginTime);
        String sentence = "의 오늘의 질문이 생성되었습니다.";
        return parseNotificationList(deviceWithQuestionDTOS, sentence);
    }

    @Override
    public List<NotificationDTO> createRemindQuestionNotificationsByBeginTime(int beginTime) throws Exception {
        List<DeviceWithQuestionDTO> deviceWithQuestionDTOS = historyDAO.readDevicesNotUploadedByBeginTime(beginTime);
        String sentence = "의 오늘의 피드를 아직 올리지 않으셨습니다.";
        return parseNotificationList(deviceWithQuestionDTOS, sentence);
    }

    @Override
    public void createFirstHistory(Long roomId) {
        historyDAO.createFirstHistory(roomId);
    }

    private List<NotificationDTO> parseNotificationList(List<DeviceWithQuestionDTO> deviceWithQuestionDTOS, String sentence) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for(DeviceWithQuestionDTO deviceWithQuestionDTO : deviceWithQuestionDTOS) {
            String deviceToken = deviceWithQuestionDTO.getDeviceToken();
            String title = deviceWithQuestionDTO.getRoomName() + sentence;
            String content = deviceWithQuestionDTO.getContent();
            notificationDTOS.add(NotificationDTO.builder()
                    .deviceToken(deviceToken)
                    .title(title)
                    .content(content)
                    .build());
        }
        return notificationDTOS;
    }
}
