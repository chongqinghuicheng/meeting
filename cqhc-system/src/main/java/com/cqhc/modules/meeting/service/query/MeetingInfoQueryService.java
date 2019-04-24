package com.cqhc.modules.meeting.service.query;

import com.cqhc.modules.meeting.domain.MeetingInfo;
import com.cqhc.modules.meeting.repository.MeetingInfoRepository;
import com.cqhc.modules.meeting.service.dto.MeetingInfoDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingInfoMapper;
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
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "meetingInfo")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingInfoQueryService {

    @Autowired
    private MeetingInfoRepository meetingInfoRepository;

    @Autowired
    private MeetingInfoMapper meetingInfoMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingInfoDTO meetingInfo, Pageable pageable){
        Page<MeetingInfo> page = meetingInfoRepository.findAll(new Spec(meetingInfo),pageable);
        return PageUtil.toPage(page.map(meetingInfoMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingInfoDTO meetingInfo){
        return meetingInfoMapper.toDto(meetingInfoRepository.findAll(new Spec(meetingInfo)));
    }

    class Spec implements Specification<MeetingInfo> {

        private MeetingInfoDTO meetingInfo;

        public Spec(MeetingInfoDTO meetingInfo){
            this.meetingInfo = meetingInfo;
        }

        @Override
        public Predicate toPredicate(Root<MeetingInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(meetingInfo.getName())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("name").as(String.class),"%"+meetingInfo.getName()+"%"));
            }
            if(!ObjectUtils.isEmpty(meetingInfo.getStartTime())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("start_time").as(Timestamp.class),meetingInfo.getStartTime()));
            }
            if(!ObjectUtils.isEmpty(meetingInfo.getEndTime())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("end_time").as(Timestamp.class),meetingInfo.getEndTime()));
            }
            if(!ObjectUtils.isEmpty(meetingInfo.getAddress())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("address").as(String.class),"%"+meetingInfo.getAddress()+"%"));
            }
            if(!ObjectUtils.isEmpty(meetingInfo.getStatus())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("status").as(Integer.class),meetingInfo.getStatus()));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}