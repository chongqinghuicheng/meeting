package com.cqhc.modules.meeting.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-30
*/
@Entity
@Data
@Table(name="meeting_notes")
public class MeetingNotes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @ApiModelProperty(name="title",notes="标题",required = true)
    @Column(name = "titile",nullable = false)
    private String titile;

    @NotBlank
    @ApiModelProperty(name="content",notes="内容",required = true)
    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "meeting_id")
    private Long meetingId;

    /**
     * 登记人
     */
    @NotNull
    @ApiModelProperty(name="user_id",notes="登记人ID")
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")//设置日期格式
    @ApiModelProperty(name="create_time",notes="创建时间")
    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;
}