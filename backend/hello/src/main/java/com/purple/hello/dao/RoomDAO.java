package com.purple.hello.dao;

import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.*;
import com.purple.hello.entity.Room;

import java.util.List;
import java.util.Map;

public interface RoomDAO {
    List<ReadRoomOutDTO> readRoomByUserId(long userId)throws Exception;
    CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO)throws Exception;
    String comparePasswordByRoomCode(long roomId) throws Exception;
    ReadUserRoomJoinOutDTO readUserRoomJoinByRoomId(long roomId) throws Exception;
    boolean updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO)  throws Exception;
    String readRoomCodeByRoomId(long roomId);
    void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO);
    boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) throws Exception;
    List<Room> getRoom();
    Map<Long, List<HistoryTypeDTO>> getHistoryTypeCount(List<Long> roomListIdx);
    Map<Long, List<HistoryTypeDTO>> getHistoryTypeFeedCount(List<Long> roomListIdx);
    Map<String, HistoryMinMaxDTO> getHistoryMinMax();
    Map<Long, List<HistoryCurrent>> getHistoryCurrent(List<Long> roomListIdx);
    ReadRoomQuestionOutDTO readRoomQuestionByRoomIdAndUserId(long roomId, long userId) throws Exception;
    List<MemberDTO> readMemberListByRoomId(long roomId, long userId);
    Integer readBeginTimeByRoomId(long roomId);
    Room readRoomByUserRoomId(long userRoomId);
}
