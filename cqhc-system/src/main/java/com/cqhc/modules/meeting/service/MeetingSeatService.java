package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingSeat;
import com.cqhc.modules.meeting.service.dto.MeetingSeatDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

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
     * 生成座次
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    List<MeetingSeatDTO> create(MeetingSeat resources);

    /**
     * 设置座次人员
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void setPerson(MeetingSeat resources);
}