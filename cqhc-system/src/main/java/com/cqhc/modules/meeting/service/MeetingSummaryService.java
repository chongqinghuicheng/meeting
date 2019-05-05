package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingSummary;
import com.cqhc.modules.meeting.service.dto.MeetingSummaryDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-30
*/
@CacheConfig(cacheNames = "meetingSummary")
public interface MeetingSummaryService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingSummaryDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingSummaryDTO create(MeetingSummary resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingSummary resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}