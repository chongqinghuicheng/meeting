package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingInfo;
import com.cqhc.modules.meeting.domain.MeetingUser;
import com.cqhc.modules.meeting.repository.MeetingUserRepository;
import com.cqhc.modules.meeting.service.dto.MeetingUserDTO;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingUserMapper;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingInfoRepository;
import com.cqhc.modules.meeting.service.MeetingInfoService;
import com.cqhc.modules.meeting.service.dto.MeetingInfoDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-24
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingInfoServiceImpl implements MeetingInfoService {

    @Autowired
    private MeetingInfoRepository meetingInfoRepository;

    @Autowired
    private MeetingInfoMapper meetingInfoMapper;

    @Autowired
    private MeetingUserRepository meetingUserRepository;

    @Autowired
    private MeetingUserMapper meetingUserMapper;

    @Override
    public MeetingInfoDTO findById(Long id) {
        Optional<MeetingInfo> meetingInfo = meetingInfoRepository.findById(id);
        ValidationUtil.isNull(meetingInfo,"MeetingInfo","id",id);
        return meetingInfoMapper.toDto(meetingInfo.get());
    }

    @Override
    public List<MeetingInfoDTO> getMeeting(Long id) {
        // 获取本单位待进行或进行中的会议
        return meetingInfoMapper.toDto(meetingInfoRepository.getMeeting(id));
    }

    @Override
    public List<MeetingUserDTO> getMeetingUser(MeetingInfoDTO resources) {
        // 根据所选会议自动填充参与人列表
        return meetingUserMapper.toDto(meetingUserRepository.getMeetingUser(resources.getId()));
    }

    @Override
    public List<MeetingInfoDTO> getUnitMeeting(Long id) {
        // 获取本单位下的所有会议
        return meetingInfoMapper.toDto(meetingInfoRepository.getUnitMeeting(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingInfoDTO create(MeetingInfo resources) {
        return meetingInfoMapper.toDto(meetingInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingInfo resources) {
        Optional<MeetingInfo> optionalMeetingInfo = meetingInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingInfo,"MeetingInfo","id",resources.getId());

        MeetingInfo meetingInfo = optionalMeetingInfo.get();
        // 此处需自己修改
        resources.setId(meetingInfo.getId());
        meetingInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingInfoRepository.deleteById(id);
    }
}