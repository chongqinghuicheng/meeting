package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-28
*/
@Data
public class MeetingUserDTO implements Serializable {

    private Long id;

    private Long meetingId;

    private Long userId;

    /**
     * 1-主持人
            2-出席人员
            3-列席人员
            4-其他人员
     */
    private short type;

    private String address;

    private Timestamp createTime;
}