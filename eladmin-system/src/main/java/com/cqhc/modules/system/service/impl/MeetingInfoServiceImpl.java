package com.cqhc.modules.system.service.impl;

import com.cqhc.modules.system.domain.MeetingInfo;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.system.repository.MeetingInfoRepository;
import com.cqhc.modules.system.service.MeetingInfoService;
import com.cqhc.modules.system.service.dto.MeetingInfoDTO;
import com.cqhc.modules.system.service.mapper.MeetingInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public MeetingInfoDTO findById(Long id) {
        Optional<MeetingInfo> meetingInfo = meetingInfoRepository.findById(id);
        ValidationUtil.isNull(meetingInfo,"MeetingInfo","id",id);
        return meetingInfoMapper.toDto(meetingInfo.get());
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