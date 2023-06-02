package com.purple.hello.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DeleteUserRoomInDTO {
    long userId;
    long userRoomId;

    @Builder
    public DeleteUserRoomInDTO(long userId, long userRoomId) {
        this.userId = userId;
        this.userRoomId = userRoomId;
    }
}
