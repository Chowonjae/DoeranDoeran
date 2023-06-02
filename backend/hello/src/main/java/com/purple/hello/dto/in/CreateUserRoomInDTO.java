package com.purple.hello.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRoomInDTO {
    long userId;
    String userName;
    String roomName;
    String roomPassword;
    String roomQuestion;

    @Builder
    public CreateUserRoomInDTO(long userId, String userName, String roomName, String roomPassword, String roomQuestion) {
        this.userId = userId;
        this.userName = userName;
        this.roomName = roomName;
        this.roomPassword = roomPassword;
        this.roomQuestion = roomQuestion;
    }
}
