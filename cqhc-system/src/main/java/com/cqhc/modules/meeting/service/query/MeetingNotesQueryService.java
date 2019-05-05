package com.cqhc.modules.meeting.service.query;

import com.cqhc.modules.meeting.domain.MeetingNotes;
import com.cqhc.modules.meeting.repository.MeetingNotesRepository;
import com.cqhc.modules.meeting.service.dto.MeetingNotesDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingNotesMapper;
import com.cqhc.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@CacheConfig(cacheNames = "meetingNotes")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingNotesQueryService {

    @Autowired
    private MeetingNotesRepository meetingNotesRepository;

    @Autowired
    private MeetingNotesMapper meetingNotesMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingNotesDTO meetingNotes, Pageable pageable){
        Page<MeetingNotes> page = meetingNotesRepository.findAll(new Spec(meetingNotes),pageable);
        return PageUtil.toPage(page.map(meetingNotesMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingNotesDTO meetingNotes){
        return meetingNotesMapper.toDto(meetingNotesRepository.findAll(new Spec(meetingNotes),new Sort(Sort.Direction.DESC,"createTime")));
    }

    class Spec implements Specification<MeetingNotes> {

        private MeetingNotesDTO meetingNotes;

        public Spec(MeetingNotesDTO meetingNotes){
            this.meetingNotes = meetingNotes;
        }

        @Override
        public Predicate toPredicate(Root<MeetingNotes> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(meetingNotes.getTitile())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("titile").as(String.class),"%"+meetingNotes.getTitile()+"%"));
            }
            if(!ObjectUtils.isEmpty(meetingNotes.getUserId())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("user_id").as(String.class),"%"+meetingNotes.getUserId()+"%"));
            }
            if(!ObjectUtils.isEmpty(meetingNotes.getCreateTime())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("create_time").as(Timestamp.class),meetingNotes.getCreateTime()));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}