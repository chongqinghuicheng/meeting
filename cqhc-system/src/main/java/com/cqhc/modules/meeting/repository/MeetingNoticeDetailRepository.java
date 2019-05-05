package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingNoticeDetail;
import com.cqhc.modules.system.domain.User;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
* @author huicheng
* @date 2019-04-26
*/
public interface MeetingNoticeDetailRepository extends JpaRepository<MeetingNoticeDetail, Long>, JpaSpecificationExecutor {
    //查询自己接收到的通知Id
    @Query(" select m.meetingNotice from MeetingNoticeDetail m where m.users = ?1 ")
    List<Long> findMeetingNoticeId(Long userId);

    //当修改通知时，删除相对的明细表
    @Query(" delete from MeetingNoticeDetail m where m.meetingNotice = ?1")
    void deleteByNoticeId(Long id);

    //通过通知ID和用户ID查询明细表
    @Query(" select m from MeetingNoticeDetail m where m.meetingNotice = ?1 and m.users =?2 ")
    MeetingNoticeDetail getMeetingNoticeDetail(Long noticeId,Long userId);

    //通过通知ID查询出该通知所有明细信息.并根据查看时间降序排序
    @Query(" select m from MeetingNoticeDetail m where m.meetingNotice = ?1 order by 'lockTime' desc ")
    List<MeetingNoticeDetail> getMeetingNoticeDetails(Long noticeId);

}