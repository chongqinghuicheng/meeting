package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingInfo;
import com.cqhc.modules.meeting.domain.MeetingSummary;
import com.cqhc.modules.meeting.service.dto.MeetingInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-24
*/
public interface MeetingInfoRepository extends JpaRepository<MeetingInfo, Long>, JpaSpecificationExecutor {

    /**
     * 根据unit_id查询单位下的会议
     * @param id
     * @return
     */
    @Query(value = "select COUNT(*) from meeting_info where unit_id = ?1",nativeQuery = true)
    Long getByUnitId(Long id);

    /**
     * 获取本单位待进行或进行中的会议
     * @param id
     * @return
     */
    @Query(value = "SELECT mi.* FROM unit u, meeting_vote mv, meeting_info mi WHERE u.id = mi.unit_id AND mi.id = mv.meeting_id AND mv.`status` != 0 AND u.id = ?1 GROUP BY mi.id",nativeQuery = true)
    List<MeetingInfo> getMeeting(Long id);

    /**
     * 获取本单位所有的会议
     * @param id
     * @return
     */
    @Query(value = "SELECT mi.* FROM meeting_info mi, unit u WHERE u.id = mi.unit_id AND u.id = ?1",nativeQuery = true)
    List<MeetingInfo> getUnitMeeting(Long id);

    /**
     * 附件会议纪要
     * @param id
     * @return
     */
    // @Query(value = "",nativeQuery = true)
    // MeetingSummary getFileInfo(Long id);
}