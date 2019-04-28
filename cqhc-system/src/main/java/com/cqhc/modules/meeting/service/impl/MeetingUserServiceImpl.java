package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingUser;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingUserRepository;
import com.cqhc.modules.meeting.service.MeetingUserService;
import com.cqhc.modules.meeting.service.dto.MeetingUserDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-28
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingUserServiceImpl implements MeetingUserService {

    @Autowired
    private MeetingUserRepository meetingUserRepository;

    @Autowired
    private MeetingUserMapper meetingUserMapper;

    @Override
    public MeetingUserDTO findById(Long id) {
        Optional<MeetingUser> meetingUser = meetingUserRepository.findById(id);
        ValidationUtil.isNull(meetingUser,"MeetingUser","id",id);
        return meetingUserMapper.toDto(meetingUser.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingUserDTO create(MeetingUser resources) {
        return meetingUserMapper.toDto(meetingUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingUser resources) {
        Optional<MeetingUser> optionalMeetingUser = meetingUserRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingUser,"MeetingUser","id",resources.getId());

        MeetingUser meetingUser = optionalMeetingUser.get();
        // 此处需自己修改
        resources.setId(meetingUser.getId());
        meetingUserRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingUserRepository.deleteById(id);
    }
}