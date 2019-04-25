package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-25
*/
@Data
public class MeetingTypeDTO implements Serializable {

    private Long id;

    private Integer sort;

    private String name;

    private Long unitId;

    /**
     * 数据字典meeting_type_status
            true正常
            false停用
     */
    private Boolean enabled;

    private String remark;
}