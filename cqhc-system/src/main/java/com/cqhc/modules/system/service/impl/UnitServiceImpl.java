package com.cqhc.modules.system.service.impl;

import com.cqhc.exception.EntityExistException;
import com.cqhc.modules.meeting.repository.MeetingInfoRepository;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.domain.User;
import com.cqhc.modules.system.repository.UserRepository;
import com.cqhc.modules.system.service.*;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.system.repository.UnitRepository;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.service.mapper.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author huicheng
* @date 2019-04-24
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UnitServiceImpl implements UnitService {


    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingInfoRepository meetingInfoRepository;

    @Autowired
    private ParameterConfigService parameterConfigService;

    @Override
    public UnitDTO findById(Long id) {
        Optional<Unit> unit = unitRepository.findById(id);
        ValidationUtil.isNull(unit,"Unit","id",id);
        return unitMapper.toDto(unit.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UnitDTO create(Unit resources) {
        // 查询用户名称是否存在
        if (unitRepository.findByName(resources.getName()) > 0) {
            throw new EntityExistException(Unit.class,"name",resources.getName());
        }

        // 新增单位
        Unit unit = unitRepository.save(resources);

        if (unit != null) {
            // 给本单位新增一个管理员账号
            User user = resources.getUser();
            // 用户单位设置为本单位
            user.setUnit(unit);
            // 必须传入source参数为0
            user.setSource((short)0);
            userRepository.save(user);
            // 默认调用“参数设置”对应的方法，复制type等于1的数据
            parameterConfigService.batch(unit.getId());
        }
        return unitMapper.toDto(unit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Unit resources) {
        // 根据id获取单位
        Optional<Unit> optionalUnit = unitRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalUnit,"Unit","id",resources.getId());

        // 得到实体
        Unit unit = optionalUnit.get();

        // 查询用户名称是否存在
        if (unitRepository.findByName(resources.getName()) > 0) {
            throw new EntityExistException(Unit.class,"name",resources.getName());
        }

        resources.setId(unit.getId());
        unitRepository.save(resources);
    }

    /**
     * 删除单位
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 当本单位有账号和会议时，不能物理删除，只能更改状态
        if (userRepository.getByUnitId(id) > 0 || meetingInfoRepository.getByUnitId(id) > 0) {
            unitRepository.updateEnabled(id);
        } else {
            unitRepository.deleteById(id);
        }
    }
}