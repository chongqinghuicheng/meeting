package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingVoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author huicheng
* @date 2019-04-26
*/
public interface MeetingVoteDetailRepository extends JpaRepository<MeetingVoteDetail, Long>, JpaSpecificationExecutor {

    /**
     * 删除投票同时删除投票明细表
     * @param id
     */
    @Modifying
    @Query(value = "DELETE FROM meeting_vote_detail WHERE vote_id = ?1",nativeQuery = true)
    void deleteByDetailId(Long id);
}