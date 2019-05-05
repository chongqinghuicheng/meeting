package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingNotice;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDTO;
import org.hibernate.Session;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-26
*/
@CacheConfig(cacheNames = "meetingNotice")
public interface MeetingNoticeService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingNoticeDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingNoticeDTO create(MeetingNotice resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingNotice resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    //获取自己创建的通知列表
    @CacheEvict(allEntries = true)
    List<MeetingNotice> findByUserId(Long userId);

    //获取自己接收到的通知列表
    @CacheEvict(allEntries = true)
    List<MeetingNotice> findByNoticeIds(Long userId);


}