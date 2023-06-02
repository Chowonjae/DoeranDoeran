package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CommonNotificationDTO {
    List<String> tokenList;
    String title;
    String content;

    @Builder
    public CommonNotificationDTO(List<String> tokenList, String title, String content) {
        this.tokenList = tokenList;
        this.title = title;
        this.content = content;
    }
}
