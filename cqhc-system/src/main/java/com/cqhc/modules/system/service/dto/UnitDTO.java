package com.cqhc.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-24
*/
@Data
public class UnitDTO implements Serializable {

    private Long id;

    private String areaCode;

    private String name;

    private String address;

    private String workTel;

    private String principal;

    private String principalPosition;

    private Long principalPhone;

    private String contact;

    private String contactPosition;

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
    private short version;

    private short terminalNum;

    /**
     * 数据字典dept_status
            true正常
            false停用
     */
    private Boolean enabled;
}