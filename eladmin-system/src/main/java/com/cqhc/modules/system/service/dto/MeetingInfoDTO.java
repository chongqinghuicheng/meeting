package com.cqhc.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-24
*/
@Data
public class MeetingInfoDTO implements Serializable {

    private Long id;

    private String name;

    private Integer password;

    private Timestamp startTime;

    private Timestamp endTime;

    private String address;

    private String orgUnit;

    private String contact;

    private Long contactPhone;

    private String summary;

    /**
     * 0-待进行
            1-进行中
            2-已完成
     */
    private Integer status;

    private Long unitId;

    private Long creater;

    private Timestamp createTime;
}