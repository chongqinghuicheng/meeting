package com.cqhc.modules.meeting.service.query;

import com.cqhc.modules.meeting.domain.MeetingVote;
import com.cqhc.modules.meeting.repository.MeetingVoteRepository;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingVoteMapper;
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
@CacheConfig(cacheNames = "meetingVote")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingVoteQueryService {

    @Autowired
    private MeetingVoteRepository meetingVoteRepository;

    @Autowired
    private MeetingVoteMapper meetingVoteMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingVoteDTO meetingVote, Pageable pageable){
        Page<MeetingVote> page = meetingVoteRepository.findAll(new Spec(meetingVote),pageable);
        return PageUtil.toPage(page.map(meetingVoteMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingVoteDTO meetingVote){
        return meetingVoteMapper.toDto(meetingVoteRepository.findAll(new Spec(meetingVote)));
    }

    class Spec implements Specification<MeetingVote> {

        private MeetingVoteDTO meetingVote;

        public Spec(MeetingVoteDTO meetingVote){
            this.meetingVote = meetingVote;
        }

        @Override
        public Predicate toPredicate(Root<MeetingVote> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(meetingVote.getTitle())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("title").as(String.class),"%"+meetingVote.getTitle()+"%"));
            }
            if(!ObjectUtils.isEmpty(meetingVote.getType())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("type").as(Integer.class),meetingVote.getType()));
            }
            if(!ObjectUtils.isEmpty(meetingVote.getStatus())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("status").as(Integer.class),meetingVote.getStatus()));
            }
            if(!ObjectUtils.isEmpty(meetingVote.getCreateTime())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("create_time").as(Timestamp.class),meetingVote.getCreateTime()));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}