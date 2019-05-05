package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingSummary;
import com.cqhc.modules.meeting.repository.MeetingInfoRepository;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingSummaryRepository;
import com.cqhc.modules.meeting.service.MeetingSummaryService;
import com.cqhc.modules.meeting.service.dto.MeetingSummaryDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingSummaryMapper;
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
public class MeetingSummaryServiceImpl implements MeetingSummaryService {

    @Autowired
    private MeetingSummaryRepository meetingSummaryRepository;

    @Autowired
    private MeetingSummaryMapper meetingSummaryMapper;

    @Override
    public MeetingSummaryDTO findById(Long id) {
        Optional<MeetingSummary> meetingSummary = meetingSummaryRepository.findById(id);
        ValidationUtil.isNull(meetingSummary,"MeetingSummary","id",id);
        return meetingSummaryMapper.toDto(meetingSummary.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingSummaryDTO create(MeetingSummary resources) {
        // 获取附件

        // 内容和附件，必填其中之一
        if(resources.getContent() == null){
            throw new IndexOutOfBoundsException("内容和附件，必填其中之一");
        }
        
        return meetingSummaryMapper.toDto(meetingSummaryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MeetingSummary resources) {
        // 获取纪要表
        Optional<MeetingSummary> optionalMeetingSummary = meetingSummaryRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingSummary,"MeetingSummary","id",resources.getId());

        // 获取纪要表实体
        MeetingSummary meetingSummary = optionalMeetingSummary.get();

        // 获取附件

        // 内容和附件，必填其中之一
        if(resources.getContent() == null){
            throw new IndexOutOfBoundsException("内容和附件，必填其中之一");
        }

        resources.setId(meetingSummary.getId());
        meetingSummaryRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        meetingSummaryRepository.deleteById(id);
    }
}