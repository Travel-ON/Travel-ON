package com.travel.travel_on.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="gugun")
@Entity
public class Gugun implements Serializable {

    @Id
    @Column(name="gugun_code", length = 10)
    private String gugunCode;

    @Column(name="gugun_name", length = 20)
    private String gugunName;
}
