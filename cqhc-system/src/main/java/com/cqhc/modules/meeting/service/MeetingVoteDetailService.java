package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingVoteDetail;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDetailDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-26
*/
@CacheConfig(cacheNames = "meetingVoteDetail")
public interface MeetingVoteDetailService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingVoteDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingVoteDetailDTO create(MeetingVoteDetail resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingVoteDetail resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}