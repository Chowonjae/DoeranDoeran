package com.purple.hello.dto.out;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReadQuestionOutDTO {
    long questionId;
    String content;

    @Builder
    public ReadQuestionOutDTO(long questionId, String content) {
        this.questionId = questionId;
        this.content = content;
    }
}
