package com.purple.hello.service;

import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.CreateUserRoomJoinOutDTO;
import com.purple.hello.enu.BoolAlarm;
import org.springframework.stereotype.Service;

@Service
public interface UserRoomService {
    CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) throws Exception;
    CreateUserRoomJoinOutDTO createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO)throws Exception;
    boolean updateRoomName(UpdateRoomNameInDTO updateRoomNameInDTO) throws Exception;
    boolean updateUserName(UpdateUserNameInDTO updateUserNameInDTO) throws Exception;
    boolean updateMoveAlarm(UpdateMoveAlarmInDTO updateMoveAlarmInDTO) throws Exception;
    boolean updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO) throws Exception;
    boolean deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO) throws Exception;
}
