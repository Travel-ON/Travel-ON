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
@Table(name="visit_place")
@Entity
public class VisitPlace implements Serializable {

    @Id
    @Column(name = "visit_place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer visitPlaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "visited_place", length = 50, nullable = false)
    private String visitedPlace;

    @Column(name = "rate_point", nullable = false)
    private Double ratePoint;

    @Column(name = "reivew", nullable = true)
    private String reivew;

    @Column(name = "sido_name", length = 20, nullable = false)
    private String sidoName;

    @Column(name = "gugun_name", length=20, nullable = false)
    private String gugunName;

    @Column(name = "visit_date", nullable = false)
    private String visitDate;

    public  void setUser(User user){
        this.user = user;
        this.user.getVisitPlaces().add(this);
    }
}
