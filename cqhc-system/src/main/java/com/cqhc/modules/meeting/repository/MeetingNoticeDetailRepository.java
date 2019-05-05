package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingNoticeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-26
*/
public interface MeetingNoticeDetailRepository extends JpaRepository<MeetingNoticeDetail, Long>, JpaSpecificationExecutor {
}