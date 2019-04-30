package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-30
*/
@Data
public class MeetingNotesDTO implements Serializable {

    private Long id;

    private String titile;

    private String content;

    private Long meetingId;

    /**
     * 登记人
     */
    private Long userId;

    private Timestamp createTime;
}