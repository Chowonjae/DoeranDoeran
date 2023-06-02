package com.purple.hello.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRoomJoinInDTO {
    long roomId;
    long userId;
    String roomName;
    String userName;
    String roomPassword;
    @Builder
    public CreateUserRoomJoinInDTO(long roomId, long userId, String roomName, String userName, String roomPassword) {
        this.roomId = roomId;
        this.userId = userId;
        this.roomName = roomName;
        this.userName = userName;
        this.roomPassword = roomPassword;
    }
}
