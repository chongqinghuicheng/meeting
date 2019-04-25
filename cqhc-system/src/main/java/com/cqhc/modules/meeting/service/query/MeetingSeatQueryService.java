package com.cqhc.modules.meeting.service.query;

import com.cqhc.utils.PageUtil;
import com.cqhc.modules.meeting.domain.MeetingSeat;
import com.cqhc.modules.meeting.service.dto.MeetingSeatDTO;
import com.cqhc.modules.meeting.repository.MeetingSeatRepository;
import com.cqhc.modules.meeting.service.mapper.MeetingSeatMapper;
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
@CacheConfig(cacheNames = "meetingSeat")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingSeatQueryService {

    @Autowired
    private MeetingSeatRepository meetingSeatRepository;

    @Autowired
    private MeetingSeatMapper meetingSeatMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingSeatDTO meetingSeat, Pageable pageable){
        Page<MeetingSeat> page = meetingSeatRepository.findAll(new Spec(meetingSeat),pageable);
        return PageUtil.toPage(page.map(meetingSeatMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingSeatDTO meetingSeat){
        return meetingSeatMapper.toDto(meetingSeatRepository.findAll(new Spec(meetingSeat)));
    }

    class Spec implements Specification<MeetingSeat> {

        private MeetingSeatDTO meetingSeat;

        public Spec(MeetingSeatDTO meetingSeat){
            this.meetingSeat = meetingSeat;
        }

        @Override
        public Predicate toPredicate(Root<MeetingSeat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}