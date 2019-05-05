package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-26
*/
public interface MeetingNoticeRepository extends JpaRepository<MeetingNotice, Long>, JpaSpecificationExecutor {
}