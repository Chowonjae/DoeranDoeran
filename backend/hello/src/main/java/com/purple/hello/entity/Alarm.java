package com.purple.hello.entity;

import com.purple.hello.enu.AlarmType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alarms")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long alarmId;
    @Enumerated(EnumType.STRING)
    AlarmType alarmType;
    String content;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    // mapping
    @JoinColumn(name = "src_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    User srcUser;
    @JoinColumn(name = "dst_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    User dstUser;
    @JoinColumn(name = "room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Room room;
}
