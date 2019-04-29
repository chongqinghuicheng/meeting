package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingVoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-26
*/
public interface MeetingVoteDetailRepository extends JpaRepository<MeetingVoteDetail, Long>, JpaSpecificationExecutor {
}