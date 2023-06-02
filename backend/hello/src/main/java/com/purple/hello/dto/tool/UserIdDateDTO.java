package com.purple.hello.dto.tool;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class UserIdDateDTO {
    long userId;
    Date date;

    @Builder
    public UserIdDateDTO(long userId, Date date) {
        this.userId = userId;
        this.date = date;
    }
}