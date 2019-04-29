package com.cqhc.modules.system.service.impl;

import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleSizeExpr;
import com.cqhc.exception.BadRequestException;
import com.cqhc.exception.EntityExistException;
import com.cqhc.modules.meeting.domain.MeetingType;
import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.repository.UnitRepository;
import com.cqhc.modules.system.service.UnitService;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.service.mapper.UnitMapper;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.system.repository.ParameterConfigRepository;
import com.cqhc.modules.system.service.ParameterConfigService;
import com.cqhc.modules.system.service.dto.ParameterConfigDTO;
import com.cqhc.modules.system.service.mapper.ParameterConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-24
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ParameterConfigServiceImpl implements ParameterConfigService {

    @Autowired
    private ParameterConfigRepository parameterConfigRepository;

    @Autowired
    private ParameterConfigMapper parameterConfigMapper;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitMapper unitMapper;


    //通过ID查询
    @Override
    public ParameterConfigDTO findById(Long id) {
        Optional<ParameterConfig> parameterConfig = parameterConfigRepository.findById(id);
        ValidationUtil.isNull(parameterConfig,"ParameterConfig","id",id);
        return parameterConfigMapper.toDto(parameterConfig.get());
    }

    //新增
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParameterConfigDTO create(ParameterConfig resources) {
        //判断单位ID是否为-1
        if(resources.getUnit().getId()!=-1){
            throw new BadRequestException("不能新增！");
        }
        //判断能否新增，参数名是否已存在
        if (parameterConfigRepository.findByNameAndUnit(resources.getName(),resources.getUnit())!=null){
            throw new EntityExistException(ParameterConfig.class,"name",resources.getName());
        }

        return parameterConfigMapper.toDto(parameterConfigRepository.save(resources));
    }

    //修改
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ParameterConfig resources) {
        Optional<ParameterConfig> optionalParameterConfig = parameterConfigRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalParameterConfig,"ParameterConfig","id",resources.getId());

        ParameterConfig parameterConfig = optionalParameterConfig.get();

        ParameterConfig parameterConfig1=parameterConfigRepository.findByNameAndUnit(resources.getName(),parameterConfig.getUnit());
        //判断修改后的数据能否进行修改
        if (parameterConfig1!=null &&!parameterConfig1.getId().equals(parameterConfig.getId())){
           throw new EntityExistException(ParameterConfig.class,"name",resources.getName());
        }
        resources.setId(parameterConfig.getId());
        parameterConfigRepository.save(resources);
    }

    //删除
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        parameterConfigRepository.deleteById(id);
    }

    //查询除了ID=1的所有单位
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Unit> findUnit() {
        List<Unit> unitList=unitRepository.findAll();
        for(int i=0;i<unitList.size();i++){
            if(unitList.get(i).getId().equals(-1)){
                unitList.remove(i);
            }
        }
        return unitList;
    }

    //新增单位时，把我们这边所有的单位级参数全再新增参数
    @Transactional(rollbackFor = Exception.class)
    public void  batch(Long unitId){
        //获取所有需复制的参数
        List<ParameterConfig> parameterConfigList=parameterConfigRepository.findByTypeAndUnit_Id(unitId);
        //找到新增的单位
        Unit unit =unitMapper.toEntity(unitService.findById(unitId));
        //循环参数集合，给单位新增参数
        for(ParameterConfig parameterConfig:parameterConfigList){
            parameterConfig.setUnit(unit);
            parameterConfigRepository.save(parameterConfig);
        }
    }
};


