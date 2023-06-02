package com.purple.hello.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReadUserInfoOutDTO {
    long userId;
    Date birth;
    String profileURL;

    @Builder
    public ReadUserInfoOutDTO(long userId, Date birth, String profileURL){
        this.userId = userId;
        this.birth = birth;
        this.profileURL = profileURL;
    }
}
