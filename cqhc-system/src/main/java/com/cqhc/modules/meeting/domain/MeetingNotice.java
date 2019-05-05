package com.cqhc.modules.meeting.domain;

import com.cqhc.modules.system.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @author huicheng
* @date 2019-04-26
*/
@Entity
@Data
@Table(name="meeting_notice")
public class MeetingNotice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "meeting_id")
    @ApiModelProperty(name="meeting_id",notes="会议ID")
    private Long meetingId;

    @NotBlank
    @Column(name = "title",nullable = false)
    @ApiModelProperty(name="title",notes="标题",required = true)
    private String title;

    @NotBlank
    @Column(name = "content",nullable = false)
    @ApiModelProperty(name="content",notes="内容",required = true)
    private String content;

    @NotNull
    @CreationTimestamp //自动设置创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")//设置日期格式
    @ApiModelProperty(name="start_time",notes="生效时间",required = true)
    @Column(name = "start_time",nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")//设置日期格式
    @ApiModelProperty(name="endTime",notes="失效时间")
    private Timestamp endTime;

    @NotNull
    @OneToMany //一旦删除通知，人员所接受到的通知就会删除。不会立即在数据库中删除，懒删除
    @ApiModelProperty(name="user_id",notes="接收用户，人员选择框")
    @Column(name = "user_id",nullable = false)
    private List<User> users;

    @NotNull
    @CreationTimestamp //自动设置创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")//设置日期格式
    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;

    @NotNull
    @Column(name = "type")
    @Value("#{1}") //创建的时候默认为1
    private  short type;
}