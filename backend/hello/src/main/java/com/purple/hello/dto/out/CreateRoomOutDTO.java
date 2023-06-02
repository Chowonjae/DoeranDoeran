package com.purple.hello.dto.out;

import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateRoomOutDTO {
    long userRoomId;
    long roomId;
    UserRoomRole userRoomRole;
    String userName;
    String roomName;
    BoolAlarm safeAlarm;
    BoolAlarm moveAlarm;
    BoolAlarm dayAlarm;
    Date createAt;

    @Builder
    public CreateRoomOutDTO(long userRoomId, long roomId, UserRoomRole userRoomRole, String userName, String roomName, BoolAlarm safeAlarm,
                            BoolAlarm moveAlarm, BoolAlarm dayAlarm, Date createAt) {
        this.userRoomId = userRoomId;
        this.roomId = roomId;
        this.userRoomRole = userRoomRole;
        this.userName = userName;
        this.roomName = roomName;
        this.safeAlarm = safeAlarm;
        this.moveAlarm = moveAlarm;
        this.dayAlarm = dayAlarm;
        this.createAt = createAt;
    }
    // mapping
    //List<Feed> feed = new ArrayList<>();
    //User user;
    //Room room;
}
