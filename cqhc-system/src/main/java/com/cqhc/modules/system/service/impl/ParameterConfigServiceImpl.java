package com.cqhc.modules.system.service.impl;

import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.system.repository.ParameterConfigRepository;
import com.cqhc.modules.system.service.ParameterConfigService;
import com.cqhc.modules.system.service.dto.ParameterConfigDTO;
import com.cqhc.modules.system.service.mapper.ParameterConfigMapper;
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
public class ParameterConfigServiceImpl implements ParameterConfigService {

    @Autowired
    private ParameterConfigRepository parameterConfigRepository;

    @Autowired
    private ParameterConfigMapper parameterConfigMapper;

    @Override
    public ParameterConfigDTO findById(Long id) {
        Optional<ParameterConfig> parameterConfig = parameterConfigRepository.findById(id);
        ValidationUtil.isNull(parameterConfig,"ParameterConfig","id",id);
        return parameterConfigMapper.toDto(parameterConfig.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParameterConfigDTO create(ParameterConfig resources) {
        return parameterConfigMapper.toDto(parameterConfigRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ParameterConfig resources) {
        Optional<ParameterConfig> optionalParameterConfig = parameterConfigRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalParameterConfig,"ParameterConfig","id",resources.getId());

        ParameterConfig parameterConfig = optionalParameterConfig.get();
        // 此处需自己修改
        resources.setId(parameterConfig.getId());
        parameterConfigRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        parameterConfigRepository.deleteById(id);
    }
}