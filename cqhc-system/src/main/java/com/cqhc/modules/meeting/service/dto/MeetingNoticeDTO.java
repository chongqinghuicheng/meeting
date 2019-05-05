package com.cqhc.modules.meeting.service.dto;

import com.cqhc.modules.system.domain.User;
import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

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

    private List<User> users;

    private Timestamp createTime;
}