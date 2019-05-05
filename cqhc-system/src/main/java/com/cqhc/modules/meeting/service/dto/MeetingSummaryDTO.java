package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-30
*/
@Data
public class MeetingSummaryDTO implements Serializable {

    private Long id;

    private MeetingInfoDTO meeting;

    private String title;

    /**
     * 1-主持人
            2-出席人员
            3-列席人员
            4-其他人员
     */
    private String content;

    private Long creater;

    private Timestamp createTime;
}