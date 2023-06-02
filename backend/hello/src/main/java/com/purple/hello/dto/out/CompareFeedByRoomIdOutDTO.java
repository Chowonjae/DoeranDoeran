package com.purple.hello.dto.out;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CompareFeedByRoomIdOutDTO {
    long userId;
    boolean isSubmit;
    @Builder
    public CompareFeedByRoomIdOutDTO(long userId, boolean isSubmit) {
        this.userId = userId;
        this.isSubmit = isSubmit;
    }
}
