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
@Table(name="alarm")
@Entity
public class Alarm implements Serializable {

    @Id
    @Column(name="alarm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(length = 100, nullable = false)
    private String content;

    public void setUser(User user){
        this.user = user;
        this.user.getAlarms().add(this);
    }
}
