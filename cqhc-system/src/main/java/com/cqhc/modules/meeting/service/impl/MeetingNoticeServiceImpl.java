package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingNotice;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingNoticeRepository;
import com.cqhc.modules.meeting.service.MeetingNoticeService;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingNoticeMapper;
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
public class MeetingNoticeServiceImpl implements MeetingNoticeService {

    @Autowired
    private MeetingNoticeRepository meetingNoticeRepository;

    @Autowired
    private MeetingNoticeMapper meetingNoticeMapper;

    @Override
    public MeetingNoticeDTO findById(Long id) {
        Optional<MeetingNotice> meetingNotice = meetingNoticeRepository.findById(id);
        ValidationUtil.isNull(meetingNotice,"MeetingNotice","id",id);
        return meetingNoticeMapper.toDto(meetingNotice.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingNoticeDTO create(MeetingNotice resources) {
        return meetingNoticeMapper.toDto(meetingNoticeRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingNotice resources) {
        Optional<MeetingNotice> optionalMeetingNotice = meetingNoticeRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingNotice,"MeetingNotice","id",resources.getId());

        MeetingNotice meetingNotice = optionalMeetingNotice.get();
        // 此处需自己修改
        resources.setId(meetingNotice.getId());
        meetingNoticeRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingNoticeRepository.deleteById(id);
    }
}