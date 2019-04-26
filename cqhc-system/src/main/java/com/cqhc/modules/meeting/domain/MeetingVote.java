package com.cqhc.modules.meeting.domain;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-26
*/
@Entity
@Data
@Table(name="meeting_vote")
public class MeetingVote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "meeting_id",nullable = false)
    private Long meetingId;

    @Column(name = "title",nullable = false)
    private String title;

    /**
     * 数据字典meeting_vote_type
            1-会议投票
            2-满意度测评
     */
    @Column(name = "type",nullable = false)
    private Integer type;

    /**
     * 数据字典meeting_vote_status
            0-未进行
            1-进行中
            2-已结束
     */
    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;

    @Column(name = "remark")
    private String remark;
}