package com.purple.hello.entity;

import com.purple.hello.enu.FeedType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feeds")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long feedId;
    String feedUrl;
    String content;
    @Column(updatable = false)
    Date createAt;
    @Enumerated(EnumType.STRING)
    FeedType feedType;
    // mapping
    @JoinColumn(name = "user_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    UserRoom userRoom;

    @Builder
    public Feed(long feedId, String feedUrl, String content, Date createAt, FeedType feedType) {
        this.feedId = feedId;
        this.feedUrl = feedUrl;
        this.content = content;
        this.createAt = createAt;
        this.feedType = feedType;
    }
}
