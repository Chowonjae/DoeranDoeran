package com.purple.hello.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserNameInDTO {
    long userId;
    long userRoomId;
    String userName;

    @Builder
    public UpdateUserNameInDTO(long userId, long userRoomId, String userName) {
        this.userId = userId;
        this.userRoomId = userRoomId;
        this.userName = userName;
    }
}
