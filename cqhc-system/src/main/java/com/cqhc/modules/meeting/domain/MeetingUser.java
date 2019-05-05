package com.cqhc.modules.meeting.domain;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-28
*/
@Entity
@Data
@Table(name="meeting_user")
public class MeetingUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "meeting_id",nullable = false)
    private Long meetingId;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    /**
     * 1-主持人
            2-出席人员
            3-列席人员
            4-其他人员
     */
    @Column(name = "type",nullable = false)
    private short type;

    @Column(name = "address")
    private String address;

    @Column(name = "create_time")
    private Timestamp createTime;
}