package com.cqhc.modules.system.service.impl;

import com.cqhc.modules.system.domain.Unit;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.system.repository.UnitRepository;
import com.cqhc.modules.system.service.UnitService;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.service.mapper.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

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

    @Override
    public UnitDTO findById(Long id) {
        Optional<Unit> unit = unitRepository.findById(id);
        ValidationUtil.isNull(unit,"Unit","id",id);
        return unitMapper.toDto(unit.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UnitDTO create(Unit resources) {
        return unitMapper.toDto(unitRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Unit resources) {
        Optional<Unit> optionalUnit = unitRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalUnit,"Unit","id",resources.getId());

        Unit unit = optionalUnit.get();
        // 此处需自己修改
        resources.setId(unit.getId());
        unitRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        unitRepository.deleteById(id);
    }
}