package com.purple.hello.dto.tool;

import com.purple.hello.enu.UserRoomRole;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class MemberDTO {
    long userId;
    String name;
    String profileUrl;
    UserRoomRole userRoomRole;
    Date birth;

    @Builder
    public MemberDTO(long userId, String name, String profileUrl, UserRoomRole userRoomRole, Date birth) {
        this.userId = userId;
        this.name = name;
        this.profileUrl = profileUrl;
        this.userRoomRole = userRoomRole;
        this.birth = birth;
    }
}
