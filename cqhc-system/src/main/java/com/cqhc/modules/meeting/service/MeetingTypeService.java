package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingType;
import com.cqhc.modules.meeting.service.dto.MeetingTypeDTO;
import com.cqhc.modules.system.domain.Unit;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-25
*/
@CacheConfig(cacheNames = "meetingType")
public interface MeetingTypeService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingTypeDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingTypeDTO create(MeetingType resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingType resources);

    @CacheEvict(allEntries = true)
    MeetingType findByNameAndUnit(String name, Unit unit);
}