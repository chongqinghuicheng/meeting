package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingNotes;
import com.cqhc.modules.system.domain.User;
import com.cqhc.modules.system.repository.UserRepository;
import com.cqhc.utils.SecurityContextHolder;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingNotesRepository;
import com.cqhc.modules.meeting.service.MeetingNotesService;
import com.cqhc.modules.meeting.service.dto.MeetingNotesDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingNotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Autowired
    private UserRepository userRepository;

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
        //获取该用户
        UserDetails userDetails = SecurityContextHolder.getUserDetails();//登录后保存的用户验证信息。
        User user=userRepository.findByUsername(userDetails.getUsername()); //获取用户信息

        //判断是否为自己创建的笔记
        if(resources.getUserId().equals(user.getId())){
            Optional<MeetingNotes> optionalMeetingNotes = meetingNotesRepository.findById(resources.getId());
            ValidationUtil.isNull( optionalMeetingNotes,"MeetingNotes","id",resources.getId());

            MeetingNotes meetingNotes = optionalMeetingNotes.get();
            // 此处需自己修改
            resources.setId(meetingNotes.getId());
            meetingNotesRepository.save(resources);
        }else {

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingNotesRepository.deleteById(id);
    }

    //查询自己创建的笔记
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MeetingNotes> getMeetingNotes(Long id){
        return meetingNotesRepository.getMeetingNotes(id);
    }
}