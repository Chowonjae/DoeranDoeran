package com.purple.hello.dto.in;

import com.purple.hello.enu.BoolAlarm;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMoveAlarmInDTO {
    long userId;
    long userRoomId;
    BoolAlarm moveAlarm;
    @Builder
    public UpdateMoveAlarmInDTO(long userId, long userRoomId, BoolAlarm moveAlarm) {
        this.userId = userId;
        this.userRoomId = userRoomId;
        this.moveAlarm = moveAlarm;
    }
}
