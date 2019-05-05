package com.cqhc.modules.meeting.service.impl;

import com.cqhc.modules.meeting.domain.MeetingSeat;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.meeting.repository.MeetingSeatRepository;
import com.cqhc.modules.meeting.service.MeetingSeatService;
import com.cqhc.modules.meeting.service.dto.MeetingSeatDTO;
import com.cqhc.modules.meeting.service.mapper.MeetingSeatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-25
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingSeatServiceImpl implements MeetingSeatService {

    @Autowired
    private MeetingSeatRepository meetingSeatRepository;

    @Autowired
    private MeetingSeatMapper meetingSeatMapper;

    @Override
    public MeetingSeatDTO findById(Long id) {
        Optional<MeetingSeat> meetingSeat = meetingSeatRepository.findById(id);
        ValidationUtil.isNull(meetingSeat,"MeetingSeat","id",id);
        return meetingSeatMapper.toDto(meetingSeat.get());
    }

    /**
     * 生成MeetingSeat
     * @param resources
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MeetingSeatDTO> create(MeetingSeat resources) {
        // 删除以前的数据
        meetingSeatRepository.deleteAll();

        List<MeetingSeatDTO> listMeetingSeatDto = new ArrayList<>();
        // 需要生成的列数
        short columns = resources.getRows();
        // 总共要生成的数量
        int sum = resources.getRows() * resources.getColumns();
        // 初始化行数
        short row = 1;
        // 初始化列数
        short column = 1;
        // 生成座次
        for (int i = 0; i < sum; i++) {
            MeetingSeat meetingSeat = new MeetingSeat();
            meetingSeat.setType(resources.getType());
            meetingSeat.setRows(row);
            meetingSeat.setColumns(column);
            MeetingSeatDTO meetingSeatDTO = meetingSeatMapper.toDto(meetingSeatRepository.save(meetingSeat));

            listMeetingSeatDto.add(meetingSeatDTO);
            // 列数加1
            column++;
            // 当列数大于等于需要生成的列数时把行数加1，列数变为1
            if (column >= columns) {
                row++;
                column = 1;
            }
        }
        return listMeetingSeatDto;
    }

    /**
     * 设置座次人员
     * @param resources
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setPerson(MeetingSeat resources) {
        // 获取该会议座次
        Optional<MeetingSeat> optionalMeetingSeat = meetingSeatRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMeetingSeat,"MeetingSeat","id",resources.getId());

        // 得到实体
        MeetingSeat meetingSeat = optionalMeetingSeat.get();
        resources.setId(meetingSeat.getId());
        meetingSeatRepository.save(resources);
    }
}