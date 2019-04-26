package com.cqhc.modules.meeting.service.query;

import com.cqhc.modules.meeting.domain.MeetingVoteDetail;
import com.cqhc.modules.meeting.repository.MeetingVoteDetailRepository;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDetailDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingVoteDetailMapper;
import com.cqhc.utils.PageUtil;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "meetingVoteDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingVoteDetailQueryService {

    @Autowired
    private MeetingVoteDetailRepository meetingVoteDetailRepository;

    @Autowired
    private MeetingVoteDetailMapper meetingVoteDetailMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingVoteDetailDTO meetingVoteDetail, Pageable pageable){
        Page<MeetingVoteDetail> page = meetingVoteDetailRepository.findAll(new Spec(meetingVoteDetail),pageable);
        return PageUtil.toPage(page.map(meetingVoteDetailMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingVoteDetailDTO meetingVoteDetail){
        return meetingVoteDetailMapper.toDto(meetingVoteDetailRepository.findAll(new Spec(meetingVoteDetail)));
    }

    class Spec implements Specification<MeetingVoteDetail> {

        private MeetingVoteDetailDTO meetingVoteDetail;

        public Spec(MeetingVoteDetailDTO meetingVoteDetail){
            this.meetingVoteDetail = meetingVoteDetail;
        }

        @Override
        public Predicate toPredicate(Root<MeetingVoteDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(meetingVoteDetail.getStatus())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("status").as(Integer.class),meetingVoteDetail.getStatus()));
            }
            if(!ObjectUtils.isEmpty(meetingVoteDetail.getCreateTime())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("create_time").as(Timestamp.class),meetingVoteDetail.getCreateTime()));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}