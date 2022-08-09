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
@Table(name="liar")
@Entity
public class Liar implements Serializable {

    @Id
    @Column(name="liar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int liarId;

    @Column(length = 20)
    private String topic;

    @Column(length = 20)
    private String keyword;
}
