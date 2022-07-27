package com.travel.travel_on.dto;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table (name="notice")
@Entity
public class Notice {

    @Id
    @Column(name="notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId;

    @Column(length = 70, nullable = false)
    private String title;

    @Column(nullable = false) // sql text 타입은 length 안줘도 되겠지?
    private String content;

    @Column(name = "notice_date", nullable = false)
    private String noticeDate;

    @Column(nullable = false)
    private Integer hits;

    @Column(name = "fixation_flag", nullable = false)
    private boolean fixationFlag;
}