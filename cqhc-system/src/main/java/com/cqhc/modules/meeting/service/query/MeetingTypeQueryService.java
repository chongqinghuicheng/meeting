package com.cqhc.modules.meeting.service.query;

import com.cqhc.modules.system.domain.Unit;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
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
     * 不分页。条件查询,并根据序号升序排序。!!!还可以对排序进行二次封装，更好的使用。
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MeetingTypeDTO meetingType) {
        //根据序号升序排序
        List<MeetingType> list  = meetingTypeRepository.findAll(new Spec(meetingType), new Sort(Sort.Direction.ASC,"sort"));
        return meetingTypeMapper.toDto(list); //数据类型转换
    }

    class Spec implements Specification<MeetingType> {

        private MeetingTypeDTO meetingType;

        public Spec(MeetingTypeDTO meetingType) {
            this.meetingType = meetingType;
        }

        @Override
        public Predicate toPredicate(Root<MeetingType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(meetingType.getName())) {
                /**
                 * 模糊.会议名称
                 */
                list.add(cb.like(root.get("name").as(String.class), "%" + meetingType.getName() + "%"));
            }
            if (!ObjectUtils.isEmpty(meetingType.getRemark())) {
                /**
                 * 模糊。描述
                 */
                list.add(cb.like(root.get("remark").as(String.class), "%" + meetingType.getRemark() + "%"));
            }
            if (!ObjectUtils.isEmpty(meetingType.getUnit().getName())) {
                /**
                 * 模糊。单位名称
                 */
                Join<Unit, MeetingType> join = root.join("grade", JoinType.LEFT);
                list.add(cb.like(join.get("unit").get("unitName").as(String.class), "%" + meetingType.getUnit().getName() + "%"));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}