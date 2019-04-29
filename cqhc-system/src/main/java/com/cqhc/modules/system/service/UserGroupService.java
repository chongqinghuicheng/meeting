package com.cqhc.modules.system.service;

import com.cqhc.modules.system.domain.UserGroup;
import com.cqhc.modules.system.service.dto.UserGroupDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-26
*/
@CacheConfig(cacheNames = "userGroup")
public interface UserGroupService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UserGroupDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    UserGroupDTO create(UserGroup resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(UserGroup resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}