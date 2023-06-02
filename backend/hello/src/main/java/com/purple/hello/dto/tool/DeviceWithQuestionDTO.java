package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeviceWithQuestionDTO {
    long roomId;
    String roomName;
    String deviceToken;
    String content;

    @Builder
    public DeviceWithQuestionDTO(long roomId, String roomName, String deviceToken, String content) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.deviceToken = deviceToken;
        this.content = content;
    }
}
