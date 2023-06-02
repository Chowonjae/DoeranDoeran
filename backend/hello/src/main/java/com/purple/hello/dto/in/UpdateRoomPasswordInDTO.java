package com.purple.hello.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRoomPasswordInDTO {
    long roomId;
    long userId;
    String roomQuestion;
    String roomPassword;

    @Builder
    public UpdateRoomPasswordInDTO(long roomId, long userId, String roomQuestion, String roomPassword) {
        this.roomId = roomId;
        this.userId = userId;
        this.roomQuestion = roomQuestion;
        this.roomPassword = roomPassword;
    }
}
