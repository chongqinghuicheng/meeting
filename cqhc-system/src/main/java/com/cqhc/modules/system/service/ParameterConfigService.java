package com.cqhc.modules.system.service;

import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.service.dto.ParameterConfigDTO;
import com.cqhc.modules.system.service.dto.UnitDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-24
*/
@CacheConfig(cacheNames = "parameterConfig")
public interface ParameterConfigService {

    /**
     * findById.通过ID查询
     * @param id
     * @return
     */
    @Cacheable(key = "#p0") //缓存:方法的返回结果时对应的key的
    ParameterConfigDTO findById(Long id);


    /**
     * create 新增
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)  //清除该方法的缓存。默认为false
    ParameterConfigDTO create(ParameterConfig resources);

    /**
     * update 修改
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(ParameterConfig resources);

    /**
     * delete 删除
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    @Cacheable(keyGenerator = "keyGenerator")
    List<Unit> findUnit();

    @CacheEvict(allEntries = true)
    void batch(Long unitId);

}