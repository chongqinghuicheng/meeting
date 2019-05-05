package com.cqhc.modules.system.service.query;

import com.cqhc.utils.PageUtil;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.repository.UnitRepository;
import com.cqhc.modules.system.service.mapper.UnitMapper;
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
@CacheConfig(cacheNames = "unit")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UnitQueryService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitMapper unitMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(UnitDTO unit, Pageable pageable){
        Page<Unit> page = unitRepository.findAll(new Spec(unit),pageable);
        return PageUtil.toPage(page.map(unitMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(UnitDTO unit){
        return unitMapper.toDto(unitRepository.findAll(new Spec(unit)));
    }

    class Spec implements Specification<Unit> {

        private UnitDTO unit;

        public Spec(UnitDTO unit){
            this.unit = unit;
        }

        @Override
        public Predicate toPredicate(Root<Unit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(unit.getAreaCode())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("area_code").as(String.class),unit.getAreaCode()));
            }
            if(!ObjectUtils.isEmpty(unit.getName())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("name").as(String.class),"%"+unit.getName()+"%"));
            }
            if(!ObjectUtils.isEmpty(unit.getAddress())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("address").as(String.class),"%"+unit.getAddress()+"%"));
            }
            if(!ObjectUtils.isEmpty(unit.getPrincipal())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("principal").as(String.class),"%"+unit.getPrincipal()+"%"));
            }
            if(!ObjectUtils.isEmpty(unit.getContact())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("contact").as(String.class),"%"+unit.getContact()+"%"));
            }
            if(!ObjectUtils.isEmpty(unit.getVersion())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("version").as(Integer.class),unit.getVersion()));
            }
            if(!ObjectUtils.isEmpty(unit.getEnabled())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("enabled").as(Boolean.class),unit.getEnabled()));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}
