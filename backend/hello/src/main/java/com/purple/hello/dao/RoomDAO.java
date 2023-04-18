package com.purple.hello.dao;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.out.ReadUserRoomJoinOutDTO;

import java.util.List;

public interface RoomDAO {
    List<ReadRoomOutDTO> readRoomByUserId(long userId);
    CreateRoomOutDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO);

    boolean comparePasswordByRoomCode(long roomId, String password);

    ReadUserRoomJoinOutDTO readUserRoomJoin(String roomCode);
}