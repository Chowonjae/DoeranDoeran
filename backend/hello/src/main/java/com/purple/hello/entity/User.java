package com.purple.hello.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userId;
    String oauthId;
    String email;
    String name;
    String userProfileUrl;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    Date birth;
    String accessToken;
    String refreshToken;
    String deviceToken;
    // mapping
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserRoom> userRoom = new ArrayList<>();
    @OneToMany(mappedBy = "srcUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Alarm> srcAlarm = new ArrayList<>();
    @OneToMany(mappedBy = "dstUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Alarm> dstAlarm = new ArrayList<>();

    @Builder
    public User(long userId, String oauthId, String email, String name, String userProfileUrl, Date createAt, Date birth, String accessToken, String refreshToken, List<UserRoom> userRoom, List<Alarm> srcAlarm, List<Alarm> dstAlarm) {
        this.userId = userId;
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.userProfileUrl = userProfileUrl;
        this.createAt = createAt;
        this.birth = birth;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userRoom = userRoom;
        this.srcAlarm = srcAlarm;
        this.dstAlarm = dstAlarm;
    }
}
