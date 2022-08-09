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
@Table(name="sido")
@Entity
public class Sido implements Serializable {

    @Id
    @Column(name="sido_code", length = 10)
    private String sidoCode;

    @Column(name="sido_name", length = 20)
    private String sidoName;
}
