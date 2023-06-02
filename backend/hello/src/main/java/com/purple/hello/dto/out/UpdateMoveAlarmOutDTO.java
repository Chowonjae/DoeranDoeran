package com.purple.hello.dto.out;

import com.purple.hello.enu.BoolAlarm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMoveAlarmOutDTO {
    BoolAlarm moveAlarm;
}
