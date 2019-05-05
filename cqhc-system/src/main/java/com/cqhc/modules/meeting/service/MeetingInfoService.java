package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingInfo;
import com.cqhc.modules.meeting.service.dto.MeetingInfoDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-24
*/
@CacheConfig(cacheNames = "meetingInfo")
public interface MeetingInfoService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingInfoDTO findById(Long id);

    /**
     * 查询所属会议
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    List<MeetingInfoDTO> getMeeting(Long id);

    /**
     * 获取本单位所有的会议
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    List<MeetingInfoDTO> getUnitMeeting(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingInfoDTO create(MeetingInfo resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingInfo resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}