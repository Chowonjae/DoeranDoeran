package com.purple.hello.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long questionId;
    /*
    DAILY => 1 ~ 1000
    GAME => 1001 ~ 2000
    KNOW => 2001 ~ 3000
     */
    @Column(nullable = false)
    long no;
    @Column(nullable = false)
    String content;
    /*
    type :
    GAME - 게임
    KNOW - 알아가기
    DAILY - 일상
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    QuestionType questionType;

    @Builder
    public Question(long questionId, long no, String content, QuestionType questionType){
        this.questionId = questionId;
        this.no = no;
        this.content = content;
        this.questionType = questionType;
    }
}
