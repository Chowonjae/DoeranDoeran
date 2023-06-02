package com.purple.hello.entity;

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
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long roomId;
    @Column(unique = true)
    String roomCode;
    String roomQuestion;
    String roomPassword;
    int beginTime;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    // mapping
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Alarm> alarm = new ArrayList<>();
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserRoom> userRoom = new ArrayList<>();
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<History> history = new ArrayList<>();
    // construct
    @Builder
    public Room(long roomId, String roomCode, String roomQuestion,
                String roomPassword, int beginTime, Date createAt) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.roomQuestion = roomQuestion;
        this.roomPassword = roomPassword;
        this.beginTime = beginTime;
        this.createAt = createAt;
    }

}
