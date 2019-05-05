package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-28
*/
public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long>, JpaSpecificationExecutor {

    /**
     * 根据所选会议自动填充参与人列表
     * @param id
     * @return
     */
    @Query(value = "SELECT u.* FROM user u, meeting_user mu WHERE u.id = mu.user_id AND mu.meeting_id = ?1 AND mu.type = 1 OR mu.type = 2",nativeQuery = true)
    List<MeetingUser> getMeetingUser(Long id);
}