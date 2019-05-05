package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingNotes;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingNotesRepository;
import com.cqhc.modules.meeting.service.MeetingNotesService;
import com.cqhc.modules.meeting.service.dto.MeetingNotesDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingNotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-30
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingNotesServiceImpl implements MeetingNotesService {

    @Autowired
    private MeetingNotesRepository meetingNotesRepository;

    @Autowired
    private MeetingNotesMapper meetingNotesMapper;

    @Override
    public MeetingNotesDTO findById(Long id) {
        Optional<MeetingNotes> meetingNotes = meetingNotesRepository.findById(id);
        ValidationUtil.isNull(meetingNotes,"MeetingNotes","id",id);
        return meetingNotesMapper.toDto(meetingNotes.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingNotesDTO create(MeetingNotes resources) {
        return meetingNotesMapper.toDto(meetingNotesRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingNotes resources) {
        Optional<MeetingNotes> optionalMeetingNotes = meetingNotesRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingNotes,"MeetingNotes","id",resources.getId());

        MeetingNotes meetingNotes = optionalMeetingNotes.get();
        // 此处需自己修改
        resources.setId(meetingNotes.getId());
        meetingNotesRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingNotesRepository.deleteById(id);
    }
}