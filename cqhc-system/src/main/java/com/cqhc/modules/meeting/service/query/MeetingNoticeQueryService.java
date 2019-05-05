package com.cqhc.modules.meeting.service.query;

import com.cqhc.modules.meeting.domain.MeetingNotice;
import com.cqhc.modules.meeting.repository.MeetingNoticeRepository;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingNoticeMapper;
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
@CacheConfig(cacheNames = "meetingNotice")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingNoticeQueryService {

    @Autowired
    private MeetingNoticeRepository meetingNoticeRepository;

    @Autowired
    private MeetingNoticeMapper meetingNoticeMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingNoticeDTO meetingNotice, Pageable pageable){
        Page<MeetingNotice> page = meetingNoticeRepository.findAll(new Spec(meetingNotice),pageable);
        return PageUtil.toPage(page.map(meetingNoticeMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingNoticeDTO meetingNotice){
        //根据生效时间降序排序
        return meetingNoticeMapper.toDto(meetingNoticeRepository.findAll(new Spec(meetingNotice),new Sort(Sort.Direction.DESC,"start_time")));
    }

    class Spec implements Specification<MeetingNotice> {

        private MeetingNoticeDTO meetingNotice;

        public Spec(MeetingNoticeDTO meetingNotice){
            this.meetingNotice = meetingNotice;
        }

        @Override
        public Predicate toPredicate(Root<MeetingNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(meetingNotice.getTitle())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("title").as(String.class),"%"+meetingNotice.getTitle()+"%"));
            }
            if(!ObjectUtils.isEmpty(meetingNotice.getStartTime())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("start_time").as(Timestamp.class),meetingNotice.getStartTime()));
            }
            if(!ObjectUtils.isEmpty(meetingNotice.getEndTime())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("end_time").as(Timestamp.class),meetingNotice.getEndTime()));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}