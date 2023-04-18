package com.purple.hello.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRoomJoinInDTO {
    long roomId;
    long userId;
    String roomCode;
    String roomName;
    String userName;
    String roomPassword;
}
