package com.cqhc.modules.meeting.service.query;

import com.cqhc.utils.PageUtil;
import com.cqhc.modules.meeting.domain.MeetingNoticeDetail;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDetailDTO;
import com.cqhc.modules.meeting.repository.MeetingNoticeDetailRepository;
import com.cqhc.modules.meeting.service.mapper.MeetingNoticeDetailMapper;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "meetingNoticeDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingNoticeDetailQueryService {

    @Autowired
    private MeetingNoticeDetailRepository meetingNoticeDetailRepository;

    @Autowired
    private MeetingNoticeDetailMapper meetingNoticeDetailMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingNoticeDetailDTO meetingNoticeDetail, Pageable pageable){
        Page<MeetingNoticeDetail> page = meetingNoticeDetailRepository.findAll(new Spec(meetingNoticeDetail),pageable);
        return PageUtil.toPage(page.map(meetingNoticeDetailMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingNoticeDetailDTO meetingNoticeDetail){
        //根据查看时间降序排序
        return meetingNoticeDetailMapper.toDto(meetingNoticeDetailRepository.findAll(new Spec(meetingNoticeDetail),new Sort(Sort.Direction.DESC,"lock_time")));
    }

    class Spec implements Specification<MeetingNoticeDetail> {

        private MeetingNoticeDetailDTO meetingNoticeDetail;

        public Spec(MeetingNoticeDetailDTO meetingNoticeDetail){
            this.meetingNoticeDetail = meetingNoticeDetail;
        }

        @Override
        public Predicate toPredicate(Root<MeetingNoticeDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}