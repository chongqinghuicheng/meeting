package com.cqhc.modules.meeting.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-25
*/
@Entity
@Data
@Table(name="meeting_type")
public class MeetingType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sort",nullable = false)
    private Integer sort;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "unit_id",nullable = false)
    private Long unitId;

    /**
     * 数据字典meeting_type_status
            true正常
            false停用
     */
    @Column(name = "enabled",nullable = false)
    private Boolean enabled;

    @Column(name = "remark")
    private String remark;
}