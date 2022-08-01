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
@Table(name="achievement")
@Entity
public class Achievement implements Serializable {

    @Id
    @Column(name="title", length = 20, nullable = false, unique = true)
    private String title;

    private int count;
}
