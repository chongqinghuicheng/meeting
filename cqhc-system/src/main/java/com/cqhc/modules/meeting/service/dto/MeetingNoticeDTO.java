package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-26
*/
@Data
public class MeetingNoticeDTO implements Serializable {

    private Long id;

    private Long meetingId;

    private String title;

    private String content;

    private Timestamp startTime;

    private Timestamp endTime;

    private Long userId;

    private Timestamp createTime;
}