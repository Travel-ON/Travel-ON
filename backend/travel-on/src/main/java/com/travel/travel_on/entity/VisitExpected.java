package com.travel.travel_on.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="visit_expected")
@Entity
public class VisitExpected {
    @Id
    @Column(name = "visit_expected_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer visitExpectedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expected_place", length = 50, nullable = false)
    private String expectedPlace;

    @Column(name = "sido_name", length = 20, nullable = false)
    private String sidoName;

    @Column(name = "gugun_name", length=20, nullable = false)
    private String gugunName;

    @Column(name = "expected_date", nullable = false)
    private String expectedDate;

    public  void setUser(User user){
        this.user = user;
        this.user.getVisitExpecteds().add(this);
    }
}
