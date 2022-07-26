package com.travel.travel_on.dto;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="visitation")
@Entity
public class Visitation {

    @Id
    @Column(name="visitation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT를 사용
    private Integer visitationId;

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name="sido_name", length = 20, nullable = false, unique = true)
    private String sidoName;

    @Column(nullable = false)
    private int count;
}
