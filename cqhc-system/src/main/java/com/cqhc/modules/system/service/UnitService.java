package com.cqhc.modules.system.service;

import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.service.dto.UnitDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-24
*/
@CacheConfig(cacheNames = "unit")
public interface UnitService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UnitDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    UnitDTO create(Unit resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Unit resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}