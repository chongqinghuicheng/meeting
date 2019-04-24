package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingInfo;
import com.cqhc.modules.meeting.service.dto.MeetingInfoDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

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