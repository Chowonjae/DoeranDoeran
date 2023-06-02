package com.purple.hello.dto.out;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ReadUserRoomJoinOutDTO {
    long roomId;
    String roomName;
    String roomQuestion;
    @Builder
    public ReadUserRoomJoinOutDTO(long roomId, String roomName, String roomQuestion) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomQuestion = roomQuestion;
    }
}
