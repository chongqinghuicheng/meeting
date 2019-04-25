package com.cqhc.modules.meeting.service.query;

import com.cqhc.utils.PageUtil;
import com.cqhc.modules.meeting.domain.MeetingType;
import com.cqhc.modules.meeting.service.dto.MeetingTypeDTO;
import com.cqhc.modules.meeting.repository.MeetingTypeRepository;
import com.cqhc.modules.meeting.service.mapper.MeetingTypeMapper;
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
@CacheConfig(cacheNames = "meetingType")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingTypeQueryService {

    @Autowired
    private MeetingTypeRepository meetingTypeRepository;

    @Autowired
    private MeetingTypeMapper meetingTypeMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingTypeDTO meetingType, Pageable pageable){
        Page<MeetingType> page = meetingTypeRepository.findAll(new Spec(meetingType),pageable);
        return PageUtil.toPage(page.map(meetingTypeMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingTypeDTO meetingType){
        return meetingTypeMapper.toDto(meetingTypeRepository.findAll(new Spec(meetingType)));
    }

    class Spec implements Specification<MeetingType> {

        private MeetingTypeDTO meetingType;

        public Spec(MeetingTypeDTO meetingType){
            this.meetingType = meetingType;
        }

        @Override
        public Predicate toPredicate(Root<MeetingType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(meetingType.getName())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("name").as(String.class),"%"+meetingType.getName()+"%"));
            }
            if(!ObjectUtils.isEmpty(meetingType.getRemark())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("remark").as(String.class),"%"+meetingType.getRemark()+"%"));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}