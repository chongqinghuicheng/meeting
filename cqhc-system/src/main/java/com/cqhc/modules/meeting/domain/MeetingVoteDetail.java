package com.cqhc.modules.meeting.domain;

import com.cqhc.modules.system.domain.User;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "会议投票明细id", required = true)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(notes = "用户id", required = true)
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @ApiModelProperty(notes = "投票id", required = true)
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
    @ApiModelProperty(notes = "状态", required = true)
    @Column(name = "status",nullable = false)
    private short status;

    @ApiModelProperty(notes = "创建日期", required = true)
    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;
}