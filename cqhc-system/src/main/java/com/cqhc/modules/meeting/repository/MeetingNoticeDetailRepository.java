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
    @Query(" select m.notice_id from MeetingNoticeDetail m where m.user_id = ?1 ")
    List<Long> findMeetingNoticeId(Long userId);

    //当修改通知时，删除相对的明细表
    @Query(" delete from MeetingNoticeDetail where MeetingNoticeDetail.notice_id = ?1")
    void deleteByNoticeId(Long id);

    //通过通知ID和用户ID查询明细表
    @Query(" select m from MeetingNoticeDetail m where m.notice_id = ?1 and m.user_id =?2 ")
    MeetingNoticeDetail getMeetingNoticeDetail(Long noticeId,Long userId);

    //根据单位ID查询出该单位下所有用户
    @Query(" select u from user u where u.unit_id = ?1")
    Set<User> findByUnitId(Long id);
}