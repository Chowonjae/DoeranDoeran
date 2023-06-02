package com.purple.hello.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DeleteRoomInDTO {
    long roomId;
    long userId;
    @Builder
    public DeleteRoomInDTO(long roomId, long userId) {
        this.roomId = roomId;
        this.userId = userId;
    }
}
