package com.cqhc.modules.system.service.query;

import com.cqhc.modules.meeting.domain.MeetingType;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.utils.PageUtil;
import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.modules.system.service.dto.ParameterConfigDTO;
import com.cqhc.modules.system.repository.ParameterConfigRepository;
import com.cqhc.modules.system.service.mapper.ParameterConfigMapper;
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

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "parameterConfig")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ParameterConfigQueryService {

    @Autowired
    private ParameterConfigRepository parameterConfigRepository;

    @Autowired
    private ParameterConfigMapper parameterConfigMapper;


    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ParameterConfigDTO parameterConfig, Pageable pageable){
        Page<ParameterConfig> page = parameterConfigRepository.findAll(new Spec(parameterConfig),pageable);
        return PageUtil.toPage(page.map(parameterConfigMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ParameterConfigDTO parameterConfig){
        return parameterConfigMapper.toDto(parameterConfigRepository.findAll(new Spec(parameterConfig)));
    }

    class Spec implements Specification<ParameterConfig> {

        private ParameterConfigDTO parameterConfig;

        public Spec(ParameterConfigDTO parameterConfig){
            this.parameterConfig = parameterConfig;
        }

        @Override
        public Predicate toPredicate(Root<ParameterConfig> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(parameterConfig.getName())){
                /**
                * 模糊.参数名
                */
                list.add(cb.like(root.get("name").as(String.class),"%"+parameterConfig.getName()+"%"));
            }
            if(!ObjectUtils.isEmpty(parameterConfig.getType())){
                /**
                * 精确.类型
                */
                list.add(cb.equal(root.get("type").as(Integer.class),parameterConfig.getType()));
            }
            if(!ObjectUtils.isEmpty(parameterConfig.getRemark())){
                /**
                * 模糊.描述
                */
                list.add(cb.like(root.get("remark").as(String.class),"%"+parameterConfig.getRemark()+"%"));
            }
            if (!ObjectUtils.isEmpty(parameterConfig.getUnit().getName())){
                /**
                 * 模糊.单位名称
                 */
                Join<Unit, ParameterConfig> join = root.join("grade", JoinType.LEFT);
                list.add(cb.like(join.get("unit").get("unitName").as(String.class), "%" +parameterConfig.getUnit().getName() + "%"));
            }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}