package com.cqhc.modules.meeting.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-25
*/
@Entity
@Data
@Table(name="meeting_seat")
public class MeetingSeat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type_id",nullable = false)
    private Long typeId;

    /**
     * 可以为空，有可能是直接某个会议类型下
     */
    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "rows",nullable = false)
    private Integer rows;

    @Column(name = "columns",nullable = false)
    private Integer columns;
}