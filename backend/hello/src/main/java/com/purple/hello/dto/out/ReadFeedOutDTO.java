package com.purple.hello.dto.out;

import com.purple.hello.util.DateUtils;
import jnr.constants.platform.Local;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReadFeedOutDTO {
    long feedId;
    String feedUrl;
    String content;
    long userId;
    Date createAt;

    @Builder
    public ReadFeedOutDTO(long feedId, String feedUrl, String content, long userId, Date createAt) {
        this.feedId = feedId;
        this.feedUrl = feedUrl;
        this.content = content;
        this.userId = userId;
        this.createAt = createAt;
    }
}
