package com.travel.travel_on.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table (name="qna")
@Entity
public class QNA implements Serializable {

    @Id
    @Column(name="qna_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qnaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="id", length = 20,nullable = false)
    private String realId;

    @Column(name="nickname", length = 20,nullable = false)
    private String nickname;

    @Column(name="title", length = 70, nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="qna_date", nullable = false)
    private String qnaDate;

    @Column(name="answer_flag", nullable = false)
    private boolean answerFlag;

    @Column(name="answer", nullable = true)
    private String answer;

    @Column(name="answer_date", nullable = true)
    private String answerDate;

    public  void setUser(User user){
        this.user = user;
        this.user.getQnas().add(this);
    }
}
