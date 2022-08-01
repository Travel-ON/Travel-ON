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
@Table(name="visitation")
@Entity
public class Visitation implements Serializable {

    @Id
    @Column(name="visitation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    private Integer visitationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

//    @Column(name="user_id")
//    private Integer userId;

    @Column(name="sido_name", length = 20, nullable = false)
    private String sidoName;

    @Column(nullable = false)
    private int count;

    public void setUser(User user){
        this.user = user;
        this.user.getVisitations().add(this);
    }
}
