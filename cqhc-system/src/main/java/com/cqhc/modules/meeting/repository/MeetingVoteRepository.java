package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author huicheng
* @date 2019-04-26
*/
public interface MeetingVoteRepository extends JpaRepository<MeetingVote, Long>, JpaSpecificationExecutor {

    /**
     * 查询实到人数
     * @param id
     */
    @Query(value = "SELECT COUNT(*) FROM meeting_info mi, meeting_user mu WHERE mi.id = mu.meeting_id AND mi.id = ?1 AND mu.create_time IS NOT NULL AND mu.type = 1 or mu.type = 2;",nativeQuery = true)
    short getActualNumber(Long id);

    /**
     * 改变状态为“2-已结束”
     * @param id
     */
    @Modifying
    @Query(value = "UPDATE meeting_vote SET `status` = 2 WHERE id = ?1",nativeQuery = true)
    void updateStatus(Long id);
}