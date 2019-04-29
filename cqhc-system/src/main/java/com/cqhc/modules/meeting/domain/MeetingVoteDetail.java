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
@Table(name="meeting_vote_detail")
public class MeetingVoteDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "vote_id",nullable = false)
    private Long voteId;

    /**
     * 会议投票时，显示以下选项
            数据字典meeting_vote_detail_status
            0-未按
            1-赞成
            2-反对
            3-弃权
            
            满意度测评时，显示以下选项
            数据字典meeting_evaluation_status
            0-未按
            1-满意
     
     */
    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;
}