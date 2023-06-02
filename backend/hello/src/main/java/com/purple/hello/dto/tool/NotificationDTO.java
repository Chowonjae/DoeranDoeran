package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class NotificationDTO {
    String deviceToken;
    String title;
    String content;

    @Builder
    public NotificationDTO(String deviceToken, String title, String content) {
        this.deviceToken = deviceToken;
        this.title = title;
        this.content = content;
    }
}