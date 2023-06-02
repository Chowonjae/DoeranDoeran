package com.purple.hello.dao;

import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.CreateUserRoomJoinOutDTO;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.UserRoomRole;

import java.util.List;
import java.util.Map;

public interface UserRoomDAO {
    CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId)throws Exception;
    CreateUserRoomJoinOutDTO createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO)throws Exception;
    boolean updateRoomName(UpdateRoomNameInDTO updateRoomNameInDTO) throws Exception;
    boolean updateUserName(UpdateUserNameInDTO updateUserNameInDTO) throws Exception;
    boolean updateMoveAlarm(UpdateMoveAlarmInDTO updateMoveAlarmInDTO)throws Exception;
    boolean updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO) throws Exception;
    void deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO);
    UserRoom readUserRoomByUserIdAndRoomId(long userId, long roomId);
    CreateUserRoomJoinOutDTO updateUserRoomRejoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO)throws Exception;
    void updateUserRoomRoleByUserRoomId(long userRoomId, UserRoomRole userRoomRole);
    UserRoom readUserRoomByUserRoomId(long userRoomId) throws Exception;
    List<UserRoom> readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(long roomId, long userRoomId, int userRoomLimit) throws Exception;
    Map<Long, Integer> getMemberCount(List<Long> roomList);
    List<UserRoom> readUserRoomsByUserId(long userId);
}
