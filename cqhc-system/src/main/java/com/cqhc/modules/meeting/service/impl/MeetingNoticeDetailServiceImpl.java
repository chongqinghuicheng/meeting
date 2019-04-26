package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingNoticeDetail;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingNoticeDetailRepository;
import com.cqhc.modules.meeting.service.MeetingNoticeDetailService;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDetailDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingNoticeDetailMapper;
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
public class MeetingNoticeDetailServiceImpl implements MeetingNoticeDetailService {

    @Autowired
    private MeetingNoticeDetailRepository meetingNoticeDetailRepository;

    @Autowired
    private MeetingNoticeDetailMapper meetingNoticeDetailMapper;

    @Override
    public MeetingNoticeDetailDTO findById(Long id) {
        Optional<MeetingNoticeDetail> meetingNoticeDetail = meetingNoticeDetailRepository.findById(id);
        ValidationUtil.isNull(meetingNoticeDetail,"MeetingNoticeDetail","id",id);
        return meetingNoticeDetailMapper.toDto(meetingNoticeDetail.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingNoticeDetailDTO create(MeetingNoticeDetail resources) {
        return meetingNoticeDetailMapper.toDto(meetingNoticeDetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingNoticeDetail resources) {
        Optional<MeetingNoticeDetail> optionalMeetingNoticeDetail = meetingNoticeDetailRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingNoticeDetail,"MeetingNoticeDetail","id",resources.getId());

        MeetingNoticeDetail meetingNoticeDetail = optionalMeetingNoticeDetail.get();
        // 此处需自己修改
        resources.setId(meetingNoticeDetail.getId());
        meetingNoticeDetailRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingNoticeDetailRepository.deleteById(id);
    }
}