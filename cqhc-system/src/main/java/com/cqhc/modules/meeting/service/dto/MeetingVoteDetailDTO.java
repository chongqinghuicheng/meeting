package com.cqhc.modules.meeting.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-26
*/
@Data
public class MeetingVoteDetailDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long voteId;

    /**
     * 会议投票时，显示以下选项
            数据字典meeting_vote_detail_status
            0-未按
            1-赞成
            2-反对
            3-弃权
            
            满意度测评时，显示以下选项
            数据字典meeting_evaluation_status
            0-未按
            1-满意
     
     */
    private Integer status;

    private Timestamp createTime;
}