package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-30
*/
public interface MeetingNotesRepository extends JpaRepository<MeetingNotes, Long>, JpaSpecificationExecutor {
}