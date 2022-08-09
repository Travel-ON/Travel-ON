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
@Table(name="report")
@Entity
public class Report implements Serializable {
    @Id
    @Column(name="report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;

    @Column(name="reporting_id", length = 20, nullable = false)
    private String reportingId;

    @Column(name="reported_id", length = 20, nullable = false)
    private String reportedId;

    @Column(name="report_date")
    private String reportDate;

    @Column(name="report_content")
    private String reportContent;

}
