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
@Table(name="place")
@Entity
public class Place implements Serializable {

    @Id
    @Column(name="place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer placeId;

    @Column(name = "visit_place", length = 50, nullable = false)
    private String visitPlace;

    @Column(name = "sido_name", length = 20, nullable = false)
    private String sidoName;

    @Column(name = "gugun_name", length=20, nullable = false)
    private String gugunName;
}
