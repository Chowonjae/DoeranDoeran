package com.purple.hello.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateRoomInDTO {
    String userName;
    String roomName;
    String roomPassword;
    String roomQuestion;
    @Builder
    public CreateRoomInDTO(String userName, String roomName, String roomPassword, String roomQuestion) {
        this.userName = userName;
        this.roomName = roomName;
        this.roomPassword = roomPassword;
        this.roomQuestion = roomQuestion;
    }
}
