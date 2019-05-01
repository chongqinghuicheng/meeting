package com.cqhc.modules.meeting.domain;

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
@Table(name="meeting_vote")
public class MeetingVote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "会议投票id", required = true)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "meeting_id",nullable = false)
    @ApiModelProperty(notes = "会议", required = true)
    private MeetingInfo meeting;

    @ApiModelProperty(notes = "标题", required = true)
    @Column(name = "title",nullable = false)
    private String title;

    /**
     * 数据字典meeting_vote_type
            1-会议投票
            2-满意度测评
     */
    @ApiModelProperty(notes = "类型", required = true)
    @Column(name = "type",nullable = false)
    private Integer type;

    @ApiModelProperty(notes = "实到人数")
    @Column(name = "actual_number")
    private short actualNumber;

    /**
     * 数据字典meeting_vote_status
            0-未进行
            1-进行中
            2-已结束
     */
    @ApiModelProperty(notes = "状态", required = true)
    @Column(name = "status",nullable = false)
    private Integer status;

    @ApiModelProperty(notes = "创建日期", required = true)
    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;

    @ApiModelProperty(notes = "描述", required = true)
    @Column(name = "remark")
    private String remark;
}