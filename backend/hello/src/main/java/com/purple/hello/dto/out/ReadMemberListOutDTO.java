package com.purple.hello.dto.out;

import com.purple.hello.dto.tool.MemberDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReadMemberListOutDTO {
    long roomId;
    List<MemberDTO> members;
    @Builder
    public ReadMemberListOutDTO(long roomId, List<MemberDTO> members) {
        this.roomId = roomId;
        this.members = members;
    }
}
