package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRoomUserCountDTO {
    Long roomId;
    Integer memberCount;

    @Builder
    public UserRoomUserCountDTO(Long roomId, Integer memberCount){
        this.roomId = roomId;
        this.memberCount = memberCount;
    }
}
