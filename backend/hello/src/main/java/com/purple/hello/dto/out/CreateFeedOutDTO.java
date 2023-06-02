package com.purple.hello.dto.out;

import com.purple.hello.enu.FeedType;
import com.purple.hello.util.DateUtils;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateFeedOutDTO {
    long feedId;
    long userRoomId;
    String content;
    Date createAt;
    FeedType feedType;
    String feedUrl;
    @Builder
    public CreateFeedOutDTO(long feedId, long userRoomId, String content, Date createAt, FeedType feedType, String feedUrl) {
        this.feedId = feedId;
        this.userRoomId = userRoomId;
        this.content = content;
        this.createAt = createAt;
        this.feedType = feedType;
        this.feedUrl = feedUrl;
    }
}
