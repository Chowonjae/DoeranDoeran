package com.purple.hello.dto.in;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRoomNameInDTO implements Serializable {
    long userId;
    long userRoomId;
    String roomName;

    @Builder
    public UpdateRoomNameInDTO(long userId, long userRoomId, String roomName) {
        this.userId = userId;
        this.userRoomId = userRoomId;
        this.roomName = roomName;
    }
}
