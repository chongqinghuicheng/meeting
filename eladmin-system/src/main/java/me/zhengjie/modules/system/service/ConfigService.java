package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Config;
import me.zhengjie.modules.system.service.dto.ConfigDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author huicheng
* @date 2019-04-23
*/
@CacheConfig(cacheNames = "config")
public interface ConfigService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    ConfigDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    ConfigDTO create(Config resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Config resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}