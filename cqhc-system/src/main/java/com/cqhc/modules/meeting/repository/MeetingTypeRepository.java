package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-25
*/
public interface MeetingTypeRepository extends JpaRepository<MeetingType, Long>, JpaSpecificationExecutor {
}