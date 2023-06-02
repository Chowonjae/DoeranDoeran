package com.purple.hello.service.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.CreateUserRoomJoinOutDTO;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.service.RoomService;
import com.purple.hello.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoomServiceImpl implements UserRoomService {
    @Autowired
    private final UserRoomDAO userRoomDAO;
    @Autowired
    private final RoomService roomService;
    public UserRoomServiceImpl(UserRoomDAO userRoomDAO, RoomService roomService){
        this.userRoomDAO = userRoomDAO;
        this.roomService = roomService;
    }
    @Override
    public CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) throws Exception{
        return this.userRoomDAO.createUserRoom(createUserRoomInDTO, roomId);
    }

    @Override
    @Transactional
    public CreateUserRoomJoinOutDTO createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) throws Exception{
        boolean isAlreadyUser = checkUserExist(createUserRoomJoinInDTO.getUserId(), createUserRoomJoinInDTO.getRoomId());
        if(isAlreadyUser){
            return this.userRoomDAO.updateUserRoomRejoin(createUserRoomJoinInDTO);
        }else{
            return this.userRoomDAO.createUserRoomJoin(createUserRoomJoinInDTO);
        }
    }

    @Override
    @Transactional
    public boolean updateRoomName(UpdateRoomNameInDTO updateRoomNameInDTO) throws Exception{
        if (updateRoomNameInDTO.getRoomName() == null)
            throw new IllegalArgumentException("bad request");

        return userRoomDAO.updateRoomName(updateRoomNameInDTO);
    }

    @Override
    @Transactional
    public boolean updateUserName(UpdateUserNameInDTO updateUserNameInDTO) throws Exception{
        return userRoomDAO.updateUserName(updateUserNameInDTO);
    }

    @Override
    @Transactional
    public boolean updateMoveAlarm(UpdateMoveAlarmInDTO updateMoveAlarmInDTO) throws Exception{
        return userRoomDAO.updateMoveAlarm(updateMoveAlarmInDTO);
    }

    @Override
    @Transactional
    public boolean updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO) throws Exception {
        return userRoomDAO.updateSafeAlarm(updateSafeAlarmInDTO);
    }

    @Override
    @Transactional
    public boolean deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO) throws Exception {
        final int USER_ROOM_LIMIT = 1;
        UserRoom userRoom = userRoomDAO.readUserRoomByUserRoomId(deleteUserRoomInDTO.getUserRoomId());

        if (userRoom.getUserRoomRole() == UserRoomRole.ROLE2)
            userRoomDAO.updateUserRoomRoleByUserRoomId(userRoom.getUserRoomId(), UserRoomRole.ROLE3);

        else {
            List<UserRoom> userRooms = userRoomDAO.readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(userRoom.getRoom().getRoomId(), userRoom.getUserRoomId(), USER_ROOM_LIMIT);

            if (userRooms.size() > 0) {
                userRoomDAO.updateUserRoomRoleByUserRoomId(userRoom.getUserRoomId(), UserRoomRole.ROLE3);
                userRoom = userRooms.get(0);
                userRoomDAO.updateUserRoomRoleByUserRoomId(userRoom.getUserRoomId(), UserRoomRole.ROLE1);
            }

            else {
                DeleteRoomInDTO deleteRoomInDTO = new DeleteRoomInDTO(userRoom.getRoom().getRoomId(), userRoom.getUser().getUserId());

                if (roomService.deleteRoom(deleteRoomInDTO))
                    return true;

                else
                    return false;
            }
        }
        return true;
    }

    private boolean checkUserExist(long userId, long roomId)throws Exception {
        UserRoom userRoom = userRoomDAO.readUserRoomByUserIdAndRoomId(userId, roomId);
        if(userRoom != null) {
            return true;
        }else {
            return false;
        }
    }
}
