package com.purple.hello.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadUserRoomNotificationOutDTO {
    int notificationCount;

    @Builder
    public ReadUserRoomNotificationOutDTO(int notificationCount) {
        this.notificationCount = notificationCount;
    }
}
