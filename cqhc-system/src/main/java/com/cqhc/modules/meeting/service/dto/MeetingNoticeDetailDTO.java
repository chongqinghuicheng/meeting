package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-26
*/
@Data
public class MeetingNoticeDetailDTO implements Serializable {

    private Long id;

    private Long noticeId;

    private Long userId;

    /**
     * 数据字典meeting_notice_detail_status
            
            0-未查看
            1-已查看
     */
    private Integer status;

    private Timestamp lockTime;

    /**
     * 数据字典meeting_notice_detail_back
            true-参加
            false-不参加
     */
    private Boolean enabled;

    private String remark;
}