package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReadUserRoomDeviceDTO {
    String roomName;
    String deviceToken;

    @Builder
    public ReadUserRoomDeviceDTO(String roomName, String deviceToken) {
        this.roomName = roomName;
        this.deviceToken = deviceToken;
    }
}
