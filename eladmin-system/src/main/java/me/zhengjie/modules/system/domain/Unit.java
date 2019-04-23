package me.zhengjie.modules.system.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-23
*/
@Entity
@Data
@Table(name="unit")
public class Unit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "area_code",nullable = false)
    private String areaCode;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "work_tel",nullable = false)
    private String workTel;

    @Column(name = "principal",nullable = false)
    private String principal;

    @Column(name = "principal_position")
    private String principalPosition;

    @Column(name = "principal_phone",nullable = false)
    private Long principalPhone;

    @Column(name = "contact")
    private String contact;

    @Column(name = "contact_position")
    private String contactPosition;

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
    @Column(name = "version",nullable = false)
    private Integer version;

    @Column(name = "terminal_num",nullable = false)
    private Integer terminalNum;

    /**
     * 数据字典dept_status
            true正常
            false停用
     */
    @Column(name = "enabled",nullable = false)
    private Boolean enabled;
}