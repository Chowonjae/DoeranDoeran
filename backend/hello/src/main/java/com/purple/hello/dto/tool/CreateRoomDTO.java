package com.purple.hello.dto.tool;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class CreateRoomDTO {
    long roomId;
    @Builder
    public CreateRoomDTO(long roomId) {
        this.roomId = roomId;
    }
}
