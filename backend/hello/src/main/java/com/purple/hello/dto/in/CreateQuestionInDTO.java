package com.purple.hello.dto.in;

import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateQuestionInDTO {
    long roomId;
    long no;

    @Builder
    public CreateQuestionInDTO(long roomId, long no) {
        this.no = no;
        this.roomId = roomId;
    }
}
