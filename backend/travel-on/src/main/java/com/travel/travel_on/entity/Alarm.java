package com.travel.travel_on.entity;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="alarm")
@Entity
public class Alarm implements Serializable {

    @Id
    @Column(name="alarm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    private Integer alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @NotFound(action = NotFoundAction.IGNORE) // 값이 발견되지 않으면 무시
    private User user;
//
//    @Column(name="user_id")
//    private Integer userId;

    @Column(length = 100, nullable = false)
    private String content;

    public void setUser(User user){
        this.user = user;
        user.getAlarms().add(this);
    }
}
