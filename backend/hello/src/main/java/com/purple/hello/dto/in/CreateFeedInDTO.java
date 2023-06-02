package com.purple.hello.dto.in;

import com.purple.hello.enu.FeedType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CreateFeedInDTO {
    MultipartFile feedFile;
    String feedUrl;
    String content;
    FeedType feedType;
    long userRoomId;

    @Builder
    public CreateFeedInDTO(MultipartFile feedFile, String feedUrl, String content, FeedType feedType, long userRoomId) {
        this.feedFile = feedFile;
        this.feedUrl = feedUrl;
        this.content = content;
        this.feedType = feedType;
        this.userRoomId = userRoomId;
    }
}
