package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-30
*/
public interface MeetingSummaryRepository extends JpaRepository<MeetingSummary, Long>, JpaSpecificationExecutor {
}