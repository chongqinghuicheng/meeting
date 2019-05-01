package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-26
*/
@Data
public class MeetingVoteDTO implements Serializable {

    private Long id;

    private MeetingInfoDTO meeting;

    private String title;

    /**
     * 数据字典meeting_vote_type
            1-会议投票
            2-满意度测评
     */
    private Integer type;

    private short actualNumber;

    /**
     * 数据字典meeting_vote_status
            0-未进行
            1-进行中
            2-已结束
     */
    private Integer status;

    private Timestamp createTime;

    private String remark;
}