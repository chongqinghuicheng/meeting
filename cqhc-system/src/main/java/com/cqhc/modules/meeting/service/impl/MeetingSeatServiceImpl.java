package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingSeat;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingSeatRepository;
import com.cqhc.modules.meeting.service.MeetingSeatService;
import com.cqhc.modules.meeting.service.dto.MeetingSeatDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingSeatMapper;
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
public class MeetingSeatServiceImpl implements MeetingSeatService {

    @Autowired
    private MeetingSeatRepository meetingSeatRepository;

    @Autowired
    private MeetingSeatMapper meetingSeatMapper;

    @Override
    public MeetingSeatDTO findById(Long id) {
        Optional<MeetingSeat> meetingSeat = meetingSeatRepository.findById(id);
        ValidationUtil.isNull(meetingSeat,"MeetingSeat","id",id);
        return meetingSeatMapper.toDto(meetingSeat.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingSeatDTO create(MeetingSeat resources) {
        return meetingSeatMapper.toDto(meetingSeatRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingSeat resources) {
        Optional<MeetingSeat> optionalMeetingSeat = meetingSeatRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingSeat,"MeetingSeat","id",resources.getId());

        MeetingSeat meetingSeat = optionalMeetingSeat.get();
        // 此处需自己修改
        resources.setId(meetingSeat.getId());
        meetingSeatRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingSeatRepository.deleteById(id);
    }
}