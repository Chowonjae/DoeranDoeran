package com.purple.hello.entity;

import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_rooms")
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userRoomId;
    @Enumerated(EnumType.STRING)
    UserRoomRole userRoomRole;
    @Column(nullable = false)
    String userName;
    @Column(nullable = false)
    String roomName;
    @Enumerated(EnumType.STRING)
    BoolAlarm safeAlarm;
    @Enumerated(EnumType.STRING)
    BoolAlarm moveAlarm;
    @Enumerated(EnumType.STRING)
    BoolAlarm dayAlarm;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    // mapping
    @OneToMany(mappedBy = "userRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Feed> feed = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    Room room;
    //construct
    @Builder
    public UserRoom(long userRoomId, UserRoomRole userRoomRole, String userName, String roomName, BoolAlarm safeAlarm, BoolAlarm moveAlarm, BoolAlarm dayAlarm, Date createAt, User user, Room room) {
        this.userRoomId = userRoomId;
        this.userRoomRole = userRoomRole;
        this.userName = userName;
        this.roomName = roomName;
        this.safeAlarm = safeAlarm;
        this.moveAlarm = moveAlarm;
        this.dayAlarm = dayAlarm;
        this.createAt = createAt;
        this.user = user;
        this.room = room;
    }
}
