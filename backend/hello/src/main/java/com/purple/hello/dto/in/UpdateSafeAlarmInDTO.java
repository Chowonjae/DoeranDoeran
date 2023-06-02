package com.purple.hello.dto.in;

import com.purple.hello.enu.BoolAlarm;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSafeAlarmInDTO {
    long roomId;
    long userId;
    BoolAlarm safeAlarm;

    @Builder
    public UpdateSafeAlarmInDTO(long roomId, long userId, BoolAlarm safeAlarm) {
        this.roomId = roomId;
        this.userId = userId;
        this.safeAlarm = safeAlarm;
    }
}
