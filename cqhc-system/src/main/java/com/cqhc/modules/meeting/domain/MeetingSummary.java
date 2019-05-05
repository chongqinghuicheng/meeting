package com.cqhc.modules.meeting.domain;

import io.swagger.annotations.ApiModelProperty;
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
@Table(name="meeting_summary")
public class MeetingSummary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "会议纪要id", required = true)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "meeting_id")
    @ApiModelProperty(notes = "会议id", required = true)
    private MeetingInfo meeting;

    @ApiModelProperty(notes = "标题", required = true)
    @Column(name = "title",nullable = false)
    private String title;

    /**
     * 1-主持人
            2-出席人员
            3-列席人员
            4-其他人员
     */
    @ApiModelProperty(notes = "内容", required = true)
    @Column(name = "content",nullable = false)
    private String content;

    @ApiModelProperty(notes = "创建人")
    @Column(name = "creater")
    private Long creater;

    @ApiModelProperty(notes = "创建时间")
    @Column(name = "create_time")
    private Timestamp createTime;
}