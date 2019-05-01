package com.cqhc.modules.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-24
*/
@Entity
@Data
@Table(name="unit")
public class Unit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "单位id", required = true)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(notes = "所在地区", required = true)
    @Column(name = "area_code",nullable = false)
    private String areaCode;

    @ApiModelProperty(notes = "单位名称", required = true)
    @Column(name = "name",nullable = false)
    private String name;

    @ApiModelProperty(notes = "详细地址", required = true)
    @Column(name = "address",nullable = false)
    private String address;

    @ApiModelProperty(notes = "单位电话", required = true)
    @Column(name = "work_tel",nullable = false)
    private String workTel;

    @ApiModelProperty(notes = "负责人", required = true)
    @Column(name = "principal",nullable = false)
    private String principal;

    @ApiModelProperty(notes = "负责人职务")
    @Column(name = "principal_position")
    private String principalPosition;

    @ApiModelProperty(notes = "负责人电话", required = true)
    @Column(name = "principal_phone",nullable = false)
    private Long principalPhone;

    @ApiModelProperty(notes = "联系人")
    @Column(name = "contact")
    private String contact;

    @ApiModelProperty(notes = "联系人职务")
    @Column(name = "contact_position")
    private String contactPosition;

    @ApiModelProperty(notes = "联系人电话")
    @Column(name = "contact_phone")
    private Long contactPhone;

    /**
     * 数据字典version_number
            0-基础版
            1-标准版
            2-党委版
            3-政府版
            4-人大版
            5-政协版
            6-部门版
            7-企业版
            9-其他
            
     */
    @ApiModelProperty(notes = "使用版本", required = true)
    @Column(name = "version",nullable = false)
    private short version;

    @ApiModelProperty(notes = "终端数", required = true)
    @Column(name = "terminal_num",nullable = false)
    private short terminalNum;

    /**
     * 数据字典dept_status
            true正常
            false停用
     */
    @ApiModelProperty(notes = "状态")
    @Column(name = "enabled",nullable = false)
    private Boolean enabled;

    @Transient
    private User user;
}