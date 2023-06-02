package com.purple.hello.dto.out;

import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRoomJoinOutDTO {
    long userRoomId;
    UserRoomRole userRoomRole;
    String userName;
    String roomName;
    BoolAlarm safeAlarm;
    BoolAlarm moveAlarm;
    BoolAlarm dayAlarm;
    Date createAt;
    @Builder
    public CreateUserRoomJoinOutDTO(long userRoomId, UserRoomRole userRoomRole, String userName, String roomName, BoolAlarm safeAlarm, BoolAlarm moveAlarm, BoolAlarm dayAlarm, Date createAt) {
        this.userRoomId = userRoomId;
        this.userRoomRole = userRoomRole;
        this.userName = userName;
        this.roomName = roomName;
        this.safeAlarm = safeAlarm;
        this.moveAlarm = moveAlarm;
        this.dayAlarm = dayAlarm;
        this.createAt = createAt;
    }
}
