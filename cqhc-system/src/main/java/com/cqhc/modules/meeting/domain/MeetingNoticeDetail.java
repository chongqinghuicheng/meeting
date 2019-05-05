package com.cqhc.modules.meeting.domain;

import com.cqhc.modules.system.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Set;

/**
* @author huicheng
* @date 2019-04-26
*/
@Entity
@Data
@Table(name="meeting_notice_detail")
public class MeetingNoticeDetail implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ApiModelProperty(name="notice_id",notes="通知的基本信息",required = true)
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY) //对面通知删除，这边该通知下的明细也自动删除
    @JoinColumn(name = "notice_id",nullable = false)
    private MeetingNotice meetingNotice;

    @NotNull
    @OneToMany
    @ApiModelProperty(name="user_id",notes="通知的接收人的ID",required = true)
    @Column(name = "user_id",nullable = false)
    private Set<User> users;

    /**
     * 数据字典meeting_notice_detail_status
            
            0-未查看
            1-已查看
     */
    @NotNull
    @ApiModelProperty(name="status",notes="查看状态，数据字典meeting_notice_detail_status",required = true)
    @Column(name = "status",nullable = false)
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")//设置日期格式
    @ApiModelProperty(name="lock_time",notes="查看时间")
    @Column(name = "lock_time")
    private Timestamp lockTime;

    /**
     * 数据字典meeting_notice_detail_back
            true-参加
            false-不参加
     */
    @Column(name = "enabled")
    @ApiModelProperty(name="enabled",notes="数据字典meeting_notice_detail_back提供")
    private Boolean enabled;

    @ApiModelProperty(name="remark",notes="反馈意见")
    @Column(name = "remark")
    private String remark;
}