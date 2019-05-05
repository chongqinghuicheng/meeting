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
    @Query(value = "SELECT mi.* FROM unit u, meeting_info mi WHERE u.id = mi.unit_id AND mi.`status` != 0 AND u.id = ?1",nativeQuery = true)
    List<MeetingInfo> getMeeting(Long id);

    /**
     * 获取本单位所有的会议
     * @param id
     * @return
     */
    @Query(value = "SELECT mi.* FROM unit u, meeting_info mi WHERE u.id = mi.unit_id AND u.id = ?1",nativeQuery = true)
    List<MeetingInfo> getUnitMeeting(Long id);

    /**
     * 查询未按的人数
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM meeting_info mi, meeting_vote mv, meeting_vote_detail mvd WHERE mi.id =  mv.meeting_id AND mv.id = mvd.vote_id AND mvd.`status` = 0 AND mi.id = ?1",nativeQuery = true)
    Long unselected(Long id);

    /**
     * 查询赞成的人数
     * @param id
     */
    @Query(value = "SELECT COUNT(*) FROM meeting_info mi, meeting_vote mv, meeting_vote_detail mvd WHERE mi.id =  mv.meeting_id AND mv.id = mvd.vote_id AND mvd.`status` = 1 AND mi.id = ?1",nativeQuery = true)
    Long approve(Long id);

    /**
     * 查询反对的人数
     * @param id
     */
    @Query(value = "SELECT COUNT(*) FROM meeting_info mi, meeting_vote mv, meeting_vote_detail mvd WHERE mi.id =  mv.meeting_id AND mv.id = mvd.vote_id AND mvd.`status` = 2 AND mi.id = ?1",nativeQuery = true)
    Long oppose(Long id);

    /**
     * 查询弃权的人数
     * @param id
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM meeting_info mi, meeting_vote mv, meeting_vote_detail mvd WHERE mi.id =  mv.meeting_id AND mv.id = mvd.vote_id AND mvd.`status` = 3 AND mi.id = ?1",nativeQuery = true)
    Long waiver(Long id);
}