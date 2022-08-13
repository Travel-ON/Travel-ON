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
@Table(name="dong")
@Entity
public class Dong implements Serializable {

    @Id
    @Column(name="dong_code", length = 10)
    private String dongCode;

    @Column(name="dong_name", length = 20)
    private String dongName;
}
