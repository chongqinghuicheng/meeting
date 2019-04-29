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
@Table(name="meeting_notice_detail")
public class MeetingNoticeDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "notice_id",nullable = false)
    private Long noticeId;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    /**
     * 数据字典meeting_notice_detail_status
            
            0-未查看
            1-已查看
     */
    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "lock_time")
    private Timestamp lockTime;

    /**
     * 数据字典meeting_notice_detail_back
            true-参加
            false-不参加
     */
    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "remark")
    private String remark;
}