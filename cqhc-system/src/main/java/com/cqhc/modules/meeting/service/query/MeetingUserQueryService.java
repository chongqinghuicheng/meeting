package com.cqhc.modules.meeting.service.query;

import com.cqhc.utils.PageUtil;
import com.cqhc.modules.meeting.domain.MeetingUser;
import com.cqhc.modules.meeting.service.dto.MeetingUserDTO;
import com.cqhc.modules.meeting.repository.MeetingUserRepository;
import com.cqhc.modules.meeting.service.mapper.MeetingUserMapper;
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
@CacheConfig(cacheNames = "meetingUser")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingUserQueryService {

    @Autowired
    private MeetingUserRepository meetingUserRepository;

    @Autowired
    private MeetingUserMapper meetingUserMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingUserDTO meetingUser, Pageable pageable){
        Page<MeetingUser> page = meetingUserRepository.findAll(new Spec(meetingUser),pageable);
        return PageUtil.toPage(page.map(meetingUserMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingUserDTO meetingUser){
        return meetingUserMapper.toDto(meetingUserRepository.findAll(new Spec(meetingUser)));
    }

    class Spec implements Specification<MeetingUser> {

        private MeetingUserDTO meetingUser;

        public Spec(MeetingUserDTO meetingUser){
            this.meetingUser = meetingUser;
        }

        @Override
        public Predicate toPredicate(Root<MeetingUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(meetingUser.getType())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("type").as(Short.class),meetingUser.getType()));
            }
            if(!ObjectUtils.isEmpty(meetingUser.getAddress())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("address").as(String.class),"%"+meetingUser.getAddress()+"%"));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}