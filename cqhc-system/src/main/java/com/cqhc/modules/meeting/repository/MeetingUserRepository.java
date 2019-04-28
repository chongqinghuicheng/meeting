package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-28
*/
public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long>, JpaSpecificationExecutor {
}