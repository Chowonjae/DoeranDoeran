package com.purple.hello.dto.tool;

import com.purple.hello.enu.UserRoomRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ReadRoomDTO {
    long userId;
    long userRoomId;
    long roomId;
    String roomName;
    String userName;
    String userProfileUrl;
    UserRoomRole userRoomRole;
    Date birth;

    @Builder
    public ReadRoomDTO(long userRoomId, long roomId, String roomName, String userName, String userProfileUrl, long userId, UserRoomRole userRoomRole, Date birth) {
        this.userId = userId;
        this.userRoomId = userRoomId;
        this.roomId = roomId;
        this.roomName = roomName;
        this.userName = userName;
        this.userProfileUrl = userProfileUrl;
        this.userRoomRole = userRoomRole;
        this.birth = birth;
    }
}
