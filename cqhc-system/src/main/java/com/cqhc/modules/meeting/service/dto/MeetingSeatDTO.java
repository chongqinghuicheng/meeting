package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-25
*/
@Data
public class MeetingSeatDTO implements Serializable {

    private Long id;

    private Long typeId;

    /**
     * 可以为空，有可能是直接某个会议类型下
     */
    private Long meetingId;

    private Long userId;

    private Integer rows;

    private Integer columns;
}