package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingNotice;
import com.cqhc.modules.meeting.repository.MeetingNoticeDetailRepository;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingNoticeRepository;
import com.cqhc.modules.meeting.service.MeetingNoticeService;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingNoticeMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private MeetingNoticeDetailRepository meetingNoticeDetailRepository;

    @Autowired
    HttpServletRequest request;

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

    //查找自己创建的通知信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MeetingNotice> findByUserId(Long userId){
        return meetingNoticeRepository.findByUserId(userId);
    }

    //查找自己接收到的通知信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MeetingNotice> findByNoticeIds(Long userId){
        //通过明细表查询接收到的通知ID
        List<Long> meetingNoticeIds=meetingNoticeDetailRepository.findMeetingNoticeId(userId);
        //通过接收到的通知ID集合查询出通知集合
        List<MeetingNotice> meetingNoticeList=meetingNoticeRepository.findAllById(meetingNoticeIds);

        return meetingNoticeList;
    }

}