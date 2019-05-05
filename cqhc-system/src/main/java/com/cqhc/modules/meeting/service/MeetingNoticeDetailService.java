package com.cqhc.modules.meeting.service;

import com.cqhc.modules.meeting.domain.MeetingNoticeDetail;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDetailDTO;
import com.cqhc.modules.system.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
* @author huicheng
* @date 2019-04-26
*/
@CacheConfig(cacheNames = "meetingNoticeDetail")
public interface MeetingNoticeDetailService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MeetingNoticeDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MeetingNoticeDetailDTO create(MeetingNoticeDetail resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(MeetingNoticeDetail resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    //根据通知ID删除
    @CacheEvict(allEntries = true)
    void deleteByNoticeId(Long id);

    //通过通知ID和用户ID查询明细表
    @CacheEvict(allEntries = true)
    MeetingNoticeDetail getMeetingNoticeDetail(Long noticeId,Long userId);

    //根据单位ID查询出该单位下所有用户
    @CacheEvict(allEntries = true)
    Set<User> findByUnitId(Long id);
}