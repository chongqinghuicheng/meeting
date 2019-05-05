package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingNotice;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-26
*/
public interface MeetingNoticeRepository extends JpaRepository<MeetingNotice, Long>, JpaSpecificationExecutor {
//查找自己创建的通知.
    @Query(" select m from Meeting_Notice where m.user_id = ?1" )
    List<MeetingNotice> findByUserId(Long userId);

//通过明细表的通知ID，查询自己接收到的通知.findById.

}