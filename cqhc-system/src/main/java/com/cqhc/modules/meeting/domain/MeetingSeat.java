package com.cqhc.modules.meeting.domain;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "会议座次id", required = true)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "type_id",nullable = false)
    @ApiModelProperty(notes = "类型", required = true)
    private MeetingType type;

    /**
     * 可以为空，有可能是直接某个会议类型下
     */
    @ApiModelProperty(notes = "会议id")
    @Column(name = "meeting_id")
    private Long meetingId;

    @ApiModelProperty(notes = "用户id")
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(notes = "行号", required = true)
    @Column(name = "rows",nullable = false)
    private short rows;

    @ApiModelProperty(notes = "列号", required = true)
    @Column(name = "columns",nullable = false)
    private short columns;

    public @interface Update {}
}