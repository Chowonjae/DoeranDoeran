package com.purple.hello.dto.in;

import com.purple.hello.enu.BoolAlarm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMoveAlarmInDTO {
    long userId;
    long userRoomId;
    BoolAlarm moveAlarm;
}
