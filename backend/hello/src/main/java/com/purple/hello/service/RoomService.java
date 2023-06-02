package com.purple.hello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomPasswordInDTO;
import com.purple.hello.dto.in.UpdateRoomCodeInDTO;
import com.purple.hello.dto.in.DeleteRoomInDTO;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.tool.NotificationDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface RoomService {
    List<ReadRoomOutDTO> readRoomByUserId(long userId) throws Exception;
    CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) throws Exception;
    boolean comparePasswordByRoomCode(long roomId, String password) throws Exception;
    ReadUserRoomJoinOutDTO readUserRoomJoinByRoomId(long roomId)throws Exception;
    boolean updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO) throws Exception;
    ReadRoomCodeOutDTO readRoomCodeByRoomId(long roomId) throws JsonProcessingException;
    void createQuestion() throws Exception;
    void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO);
    @Transactional
    boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) throws Exception;
    ReadRoomQuestionOutDTO readRoomQuestionByRoomIdAndUserId(long roomId, long userId)throws Exception;
    ReadMemberListOutDTO readMemberListByRoomId(long roomId, long userId)throws Exception;
    List<NotificationDTO> makeNotificationForOtherDevicesByRoomId(long userId) throws Exception;
}
