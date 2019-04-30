package com.cqhc.modules.meeting.service.query;

import com.cqhc.utils.PageUtil;
import com.cqhc.modules.meeting.domain.MeetingSummary;
import com.cqhc.modules.meeting.service.dto.MeetingSummaryDTO;
import com.cqhc.modules.meeting.repository.MeetingSummaryRepository;
import com.cqhc.modules.meeting.service.mapper.MeetingSummaryMapper;
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
@CacheConfig(cacheNames = "meetingSummary")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingSummaryQueryService {

    @Autowired
    private MeetingSummaryRepository meetingSummaryRepository;

    @Autowired
    private MeetingSummaryMapper meetingSummaryMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingSummaryDTO meetingSummary, Pageable pageable){
        Page<MeetingSummary> page = meetingSummaryRepository.findAll(new Spec(meetingSummary),pageable);
        return PageUtil.toPage(page.map(meetingSummaryMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingSummaryDTO meetingSummary){
        return meetingSummaryMapper.toDto(meetingSummaryRepository.findAll(new Spec(meetingSummary)));
    }

    class Spec implements Specification<MeetingSummary> {

        private MeetingSummaryDTO meetingSummary;

        public Spec(MeetingSummaryDTO meetingSummary){
            this.meetingSummary = meetingSummary;
        }

        @Override
        public Predicate toPredicate(Root<MeetingSummary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(meetingSummary.getTitle())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("title").as(String.class),"%"+meetingSummary.getTitle()+"%"));
            }
            if(!ObjectUtils.isEmpty(meetingSummary.getContent())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("content").as(String.class),"%"+meetingSummary.getContent()+"%"));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}