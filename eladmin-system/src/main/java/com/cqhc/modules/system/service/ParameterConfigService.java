package com.cqhc.modules.system.service;

import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.modules.system.service.dto.ParameterConfigDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-24
*/
@CacheConfig(cacheNames = "parameterConfig")
public interface ParameterConfigService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    ParameterConfigDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    ParameterConfigDTO create(ParameterConfig resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(ParameterConfig resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}