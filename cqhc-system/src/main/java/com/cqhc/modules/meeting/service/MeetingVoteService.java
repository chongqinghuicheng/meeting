package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingInfo;
import com.cqhc.modules.meeting.domain.MeetingVote;
import com.cqhc.modules.meeting.service.dto.MeetingInfoDTO;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-26
*/
@CacheConfig(cacheNames = "meetingVote")
public interface MeetingVoteService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingVoteDTO findById(Long id);

    /**
     * 全屏投影
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingVoteDTO projection(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingVoteDTO create(MeetingVote resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingVote resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 点击左剪头改变状态
     * @param resources
     */
    void updateLeftStatus(MeetingVote resources);

    /**
     * 点击右箭头修改状态
     * @param resources
     */
    void updateRightStatus(MeetingVote resources);
}