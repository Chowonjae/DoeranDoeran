package com.purple.hello.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long historyId;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    Room room;

    @Builder
    public History(Room room, Question question, Date createAt) {
        this.room = room;
        this.question = question;
        this.createAt = createAt;
    }
}
