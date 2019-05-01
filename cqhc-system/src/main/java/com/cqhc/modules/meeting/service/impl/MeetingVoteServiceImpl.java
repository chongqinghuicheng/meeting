package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingVote;
import com.cqhc.modules.meeting.repository.MeetingVoteDetailRepository;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingVoteRepository;
import com.cqhc.modules.meeting.service.MeetingVoteService;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingVoteMapper;
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
public class MeetingVoteServiceImpl implements MeetingVoteService {

    @Autowired
    private MeetingVoteRepository meetingVoteRepository;

    @Autowired
    private MeetingVoteMapper meetingVoteMapper;

    @Autowired
    private MeetingVoteDetailRepository meetingVoteDetailRepository;

    @Override
    public MeetingVoteDTO findById(Long id) {
        Optional<MeetingVote> meetingVote = meetingVoteRepository.findById(id);
        ValidationUtil.isNull(meetingVote,"MeetingVote","id",id);
        return meetingVoteMapper.toDto(meetingVote.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingVoteDTO create(MeetingVote resources) {
        // 查询实到人数
        short actualNumber = meetingVoteRepository.getActualNumber(resources.getMeeting().getId());
        // 设置实到人数
        resources.setActualNumber(actualNumber);
        // 状态默认为“0-未进行”
        resources.setStatus(0);

        return meetingVoteMapper.toDto(meetingVoteRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingVote resources) {
        // 获取投票表
        Optional<MeetingVote> optionalMeetingVote = meetingVoteRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingVote,"MeetingVote","id",resources.getId());

        // 获取投票表实体
        MeetingVote meetingVote = optionalMeetingVote.get();
        resources.setId(meetingVote.getId());
        meetingVoteRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除投票明细表
        meetingVoteDetailRepository.deleteByDetailId(id);
        // 删除投票
        meetingVoteRepository.deleteById(id);
    }
}