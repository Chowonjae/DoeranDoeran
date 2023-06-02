package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionIdRoomIdDTO {
    Long questionID;
    Long roomID;

    @Builder
    public QuestionIdRoomIdDTO(Long questionID, Long roomID){
        this.questionID = questionID;
        this.roomID = roomID;
    }
}
