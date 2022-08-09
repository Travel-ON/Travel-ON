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
@Table(name="achievement")
@Entity
public class Achievement implements Serializable {

    @Id
    @Column(name="title", length = 20, nullable = false, unique = true)
    private String title;

    @Column
    private int count;
}
