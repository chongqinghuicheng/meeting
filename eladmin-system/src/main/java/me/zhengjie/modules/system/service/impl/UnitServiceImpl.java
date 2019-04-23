package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.Unit;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.system.repository.UnitRepository;
import me.zhengjie.modules.system.service.UnitService;
import me.zhengjie.modules.system.service.dto.UnitDTO;
import me.zhengjie.modules.system.service.mapper.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-23
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