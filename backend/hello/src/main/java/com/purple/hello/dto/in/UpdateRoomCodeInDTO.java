package com.purple.hello.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRoomCodeInDTO {
    long roomId;
    String roomCode;
    @Builder
    public UpdateRoomCodeInDTO(long roomId, String roomCode) {
        this.roomId = roomId;
        this.roomCode = roomCode;
    }
}
