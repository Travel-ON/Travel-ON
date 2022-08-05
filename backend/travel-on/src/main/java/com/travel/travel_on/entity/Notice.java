package com.travel.travel_on.entity;

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

    @Column(nullable = false)
    private String content;

    @Column(name = "notice_date")
    private String noticeDate;

    @Column
    private Integer hits;

    @Column(name = "fixation_flag")
    private boolean fixationFlag;


    public Notice(String title, String content){
        this.title = title;
        this.content = content;
    }

}