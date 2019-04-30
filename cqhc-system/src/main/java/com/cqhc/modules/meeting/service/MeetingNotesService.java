package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingNotes;
import com.cqhc.modules.meeting.service.dto.MeetingNotesDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-30
*/
@CacheConfig(cacheNames = "meetingNotes")
public interface MeetingNotesService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingNotesDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingNotesDTO create(MeetingNotes resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingNotes resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}