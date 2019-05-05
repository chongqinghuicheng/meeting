package com.cqhc.modules.meeting.service.impl;

import com.cqhc.exception.EntityExistException;
import com.cqhc.modules.meeting.domain.MeetingType;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.domain.User;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingTypeRepository;
import com.cqhc.modules.meeting.service.MeetingTypeService;
import com.cqhc.modules.meeting.service.dto.MeetingTypeDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-25
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingTypeServiceImpl implements MeetingTypeService {

    @Autowired
    private MeetingTypeRepository meetingTypeRepository;

    @Autowired
    private MeetingTypeMapper meetingTypeMapper;

    @Override //重写父类的方法
    public MeetingTypeDTO findById(Long id) {
        Optional<MeetingType> meetingType = meetingTypeRepository.findById(id);
        ValidationUtil.isNull(meetingType,"MeetingType","id",id);
        return meetingTypeMapper.toDto(meetingType.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class) //配置事务管理
    public MeetingTypeDTO create(MeetingType resources) {
        //新增时，判断名称+单位是否已经存在
        if(meetingTypeRepository.findByNameAndUnit(resources.getName(),resources.getUnit())!=null){
            throw new EntityExistException(MeetingType.class,"name",resources.getName()); //报已存在异常
        }

        return meetingTypeMapper.toDto(meetingTypeRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingType resources) {
        Optional<MeetingType> optionalMeetingType = meetingTypeRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingType,"MeetingType","id",resources.getId());

        MeetingType meetingType = optionalMeetingType.get();
        // 此处需自己修改!!!!!!!!

        //判断是否已经存在修改后的名称+单位
        MeetingType meetingType1=meetingTypeRepository.findByNameAndUnit(meetingType.getName(),meetingType.getUnit());
//        if(meetingTypeRepository.findByNameAndUnit(meetingType.getId(),meetingType.getName(),meetingType.getUnit()) ==null){
//            throw new EntityExistException(MeetingType.class,"name",resources.getName()); //报已存在异常
//        }
        if(meetingType1 != null&&!meetingType1.getId().equals(meetingType.getId())){
            throw new EntityExistException(MeetingType.class,"name",resources.getName()); //报已存在异常
        }

        resources.setId(meetingType.getId());
        meetingTypeRepository.save(resources); //实现数据更新，没被更新的字段会被置为Null。
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingType findByNameAndUnit(String name, Unit unit){
        return meetingTypeRepository.findByNameAndUnit(name,unit);
    }
}