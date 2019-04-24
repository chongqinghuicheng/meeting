package com.cqhc.modules.system.domain;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-24
*/
@Entity
@Data
@Table(name="meeting_info")
public class MeetingInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "password")
    private Integer password;

    @Column(name = "start_time",nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "org_unit")
    private String orgUnit;

    @Column(name = "contact")
    private String contact;

    @Column(name = "contact_phone")
    private Long contactPhone;

    @Column(name = "summary")
    private String summary;

    /**
     * 0-待进行
            1-进行中
            2-已完成
     */
    @Column(name = "status")
    private Integer status;

    @Column(name = "unit_id",nullable = false)
    private Long unitId;

    @Column(name = "creater",nullable = false)
    private Long creater;

    @Column(name = "create_time",nullable = false)
    private Timestamp createTime;
}