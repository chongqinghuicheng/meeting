package me.zhengjie.modules.system.service.query;

import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.system.domain.Config;
import me.zhengjie.modules.system.service.dto.ConfigDTO;
import me.zhengjie.modules.system.repository.ConfigRepository;
import me.zhengjie.modules.system.service.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "config")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ConfigQueryService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ConfigMapper configMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ConfigDTO config, Pageable pageable){
        Page<Config> page = configRepository.findAll(new Spec(config),pageable);
        return PageUtil.toPage(page.map(configMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ConfigDTO config){
        return configMapper.toDto(configRepository.findAll(new Spec(config)));
    }

    class Spec implements Specification<Config> {

        private ConfigDTO config;

        public Spec(ConfigDTO config){
            this.config = config;
        }

        @Override
        public Predicate toPredicate(Root<Config> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}