package com.cqhc.modules.system.service.query;

import com.cqhc.modules.system.domain.UserGroup;
import com.cqhc.modules.system.repository.UserGroupRepository;
import com.cqhc.modules.system.service.dto.UserGroupDTO;
import com.cqhc.modules.system.service.mapper.UserGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "userGroup")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserGroupQueryService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserGroupMapper userGroupMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object findBySort(int pageNumber,int pageSize){
        List<UserGroup> ListBySort = userGroupRepository.findBySort(pageNumber, pageSize);
        return ListBySort;
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(UserGroupDTO userGroup){
        return userGroupMapper.toDto(userGroupRepository.findAll(new Spec(userGroup)));
    }

    class Spec implements Specification<UserGroup> {

        private UserGroupDTO userGroup;

        public Spec(UserGroupDTO userGroup){
            this.userGroup = userGroup;
        }

        @Override
        public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(userGroup.getName())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("name").as(String.class),"%"+userGroup.getName()+"%"));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}