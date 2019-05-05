package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingInfo;
import com.cqhc.modules.meeting.domain.MeetingUser;
import com.cqhc.modules.meeting.domain.MeetingVote;
import com.cqhc.modules.meeting.domain.MeetingVoteDetail;
import com.cqhc.modules.meeting.repository.MeetingInfoRepository;
import com.cqhc.modules.meeting.repository.MeetingUserRepository;
import com.cqhc.modules.meeting.repository.MeetingVoteDetailRepository;
import com.cqhc.modules.meeting.service.MeetingInfoService;
import com.cqhc.modules.meeting.service.dto.MeetingInfoDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingInfoMapper;
import com.cqhc.modules.system.domain.User;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingVoteRepository;
import com.cqhc.modules.meeting.service.MeetingVoteService;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingVoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    // @Override
    // public MeetingVoteDTO projection(Long id) {
    //     MeetingVote meetingVote = new MeetingVote();
    //     // 查询未按的人数
    //     Long unselected = meetingInfoRepository.unselected(id);
    //     meetingVote.setUnselected(unselected);
    //     // 查询赞成的人数
    //     Long approve = meetingInfoRepository.approve(id);
    //     meetingVote.setApprove(approve);
    //     // 查询反对的人数
    //     Long oppose = meetingInfoRepository.oppose(id);
    //     meetingVote.setOppose(oppose);
    //     // 查询弃权的人数
    //     Long waiver = meetingInfoRepository.waiver(id);
    //     meetingVote.setWaiver(waiver);
    //
    //     // 查询投票人数
    //     Long sum = approve + oppose + waiver;
    //
    //     // 若实到人数与投票人数相等时，自动改变状态为“2-已结束”
    //     if (meetingVoteRepository.getActualNumber(id) == sum) {
    //         meetingVoteRepository.updateStatus(id);
    //     }
    //     return meetingVoteMapper.toDto(meetingVote);
    // }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingVoteDTO create(MeetingVote resources) {
        // 查询实到人数
        short actualNumber = meetingVoteRepository.getActualNumber(resources.getMeeting().getId());
        // 设置实到人数
        resources.setActualNumber(actualNumber);
        // 如果状态没设置默认为“0-未进行”
        if (resources.getStatus() == null) {
            resources.setStatus(0);
        }

        // 新增投票表
        MeetingVote meetingVote = meetingVoteRepository.save(resources);

        // 创建投票明细表实体
        MeetingVoteDetail meetingVoteDetail = new MeetingVoteDetail();
        // 设置明细表的投票id为此id
        meetingVoteDetail.setVoteId(resources.getId());
        // 状态默认为“0-未按”
        meetingVoteDetail.setStatus((short)0);

        // 投票表新增成功后添加投票明细表
        if (meetingVote != null && resources.getMeetingUsers() != null) {
            for (MeetingUser meetingUser : resources.getMeetingUsers()) {
                // 设置用户id
                meetingVoteDetail.setUserId(meetingUser.getId());
                // 新增
                meetingVoteDetailRepository.save(meetingVoteDetail);
            }
        }

        return meetingVoteMapper.toDto(meetingVote);
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

    @Override
    public void updateLeftStatus(MeetingVote resources) {
        // 获取投票表
        Optional<MeetingVote> optionalMeetingVote = meetingVoteRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingVote,"MeetingVote","id",resources.getId());

        // 获取投票表实体
        MeetingVote meetingVote = optionalMeetingVote.get();
        resources.setId(meetingVote.getId());
        // 如果状态大于0的时候就把状态加1
        if (meetingVote.getStatus() > 0) {
            resources.setStatus(meetingVote.getStatus() - 1);
        }
        meetingVoteRepository.save(resources);
    }

    @Override
    public void updateRightStatus(MeetingVote resources) {
        // 获取投票表
        Optional<MeetingVote> optionalMeetingVote = meetingVoteRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingVote,"MeetingVote","id",resources.getId());

        // 获取投票表实体
        MeetingVote meetingVote = optionalMeetingVote.get();
        resources.setId(meetingVote.getId());
        // 如果状态小于3的时候就把状态减1
        if (meetingVote.getStatus() < 3) {
            resources.setStatus(meetingVote.getStatus() + 1);
        }
        meetingVoteRepository.save(resources);
    }
}