package com.purple.hello.dto.out;

import com.purple.hello.dto.tool.MemberDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReadRoomOutDTO {
    long userRoomId;
    long roomId;
    String roomName;
    List<MemberDTO> members;

    @Builder
    public ReadRoomOutDTO(long userRoomId, long roomId, String roomName, List<MemberDTO> members) {
        this.userRoomId = userRoomId;
        this.roomId = roomId;
        this.roomName = roomName;
        this.members = members;
    }
}
