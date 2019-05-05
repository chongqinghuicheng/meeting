package com.cqhc.modules.meeting.domain;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-30
*/
@Entity
@Data
@Table(name="meeting_notes")
public class MeetingNotes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titile",nullable = false)
    private String titile;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "meeting_id")
    private Long meetingId;

    /**
     * 登记人
     */
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;
}