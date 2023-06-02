package com.purple.hello.dto.out;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class ReadRoomQuestionOutDTO {
    long roomId;
    String roomQuestion;

    @Builder
    public ReadRoomQuestionOutDTO(long roomId, String roomQuestion) {
        this.roomId = roomId;
        this.roomQuestion = roomQuestion;
    }
}
