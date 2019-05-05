package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-30
*/
public interface MeetingNotesRepository extends JpaRepository<MeetingNotes, Long>, JpaSpecificationExecutor {
    //查询自己创建的笔记
    @Query(" select m from MeetingNotes m where m.userId = ?1 ")
    List<MeetingNotes> getMeetingNotes(Long id);



}