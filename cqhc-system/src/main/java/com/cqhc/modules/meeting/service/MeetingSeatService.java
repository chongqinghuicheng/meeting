package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingSeat;
import com.cqhc.modules.meeting.service.dto.MeetingSeatDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-25
*/
@CacheConfig(cacheNames = "meetingSeat")
public interface MeetingSeatService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingSeatDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingSeatDTO create(MeetingSeat resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingSeat resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}