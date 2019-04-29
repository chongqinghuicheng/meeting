package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingVoteDetail;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingVoteDetailRepository;
import com.cqhc.modules.meeting.service.MeetingVoteDetailService;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDetailDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingVoteDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-26
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingVoteDetailServiceImpl implements MeetingVoteDetailService {

    @Autowired
    private MeetingVoteDetailRepository meetingVoteDetailRepository;

    @Autowired
    private MeetingVoteDetailMapper meetingVoteDetailMapper;

    @Override
    public MeetingVoteDetailDTO findById(Long id) {
        Optional<MeetingVoteDetail> meetingVoteDetail = meetingVoteDetailRepository.findById(id);
        ValidationUtil.isNull(meetingVoteDetail,"MeetingVoteDetail","id",id);
        return meetingVoteDetailMapper.toDto(meetingVoteDetail.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingVoteDetailDTO create(MeetingVoteDetail resources) {
        return meetingVoteDetailMapper.toDto(meetingVoteDetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingVoteDetail resources) {
        Optional<MeetingVoteDetail> optionalMeetingVoteDetail = meetingVoteDetailRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingVoteDetail,"MeetingVoteDetail","id",resources.getId());

        MeetingVoteDetail meetingVoteDetail = optionalMeetingVoteDetail.get();
        // 此处需自己修改
        resources.setId(meetingVoteDetail.getId());
        meetingVoteDetailRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingVoteDetailRepository.deleteById(id);
    }
}