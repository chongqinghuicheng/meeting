package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingType;
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

    @Override
    public MeetingTypeDTO findById(Long id) {
        Optional<MeetingType> meetingType = meetingTypeRepository.findById(id);
        ValidationUtil.isNull(meetingType,"MeetingType","id",id);
        return meetingTypeMapper.toDto(meetingType.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingTypeDTO create(MeetingType resources) {
        return meetingTypeMapper.toDto(meetingTypeRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingType resources) {
        Optional<MeetingType> optionalMeetingType = meetingTypeRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingType,"MeetingType","id",resources.getId());

        MeetingType meetingType = optionalMeetingType.get();
        // 此处需自己修改
        resources.setId(meetingType.getId());
        meetingTypeRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingTypeRepository.deleteById(id);
    }
}