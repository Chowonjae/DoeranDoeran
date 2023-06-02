package com.purple.hello.dao;

import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.dto.tool.DeviceWithQuestionDTO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface HistoryDAO {
    ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date currentDateKST) throws IOException;
    List<DeviceWithQuestionDTO> readDevicesWithDailyQuestionByBeginTime(int beginTime) throws Exception;
    List<DeviceWithQuestionDTO> readDevicesNotUploadedByBeginTime(int beginTime) throws Exception;
    void createFirstHistory(Long roomId);
}
