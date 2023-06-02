package com.purple.hello.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserInfoInDTO {
    long userId;
    Date birth;
    String profileURL;

    @Builder
    UpdateUserInfoInDTO(long userId, Date birth, String profileURL){
        this.userId = userId;
        this.birth = birth;
        this.profileURL = profileURL;
    }
}
