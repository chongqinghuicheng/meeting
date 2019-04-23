package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.Config;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.system.repository.ConfigRepository;
import me.zhengjie.modules.system.service.ConfigService;
import me.zhengjie.modules.system.service.dto.ConfigDTO;
import me.zhengjie.modules.system.service.mapper.ConfigMapper;
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
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public ConfigDTO findById(Long id) {
        Optional<Config> config = configRepository.findById(id);
        ValidationUtil.isNull(config,"Config","id",id);
        return configMapper.toDto(config.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ConfigDTO create(Config resources) {
        return configMapper.toDto(configRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Config resources) {
        Optional<Config> optionalConfig = configRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalConfig,"Config","id",resources.getId());

        Config config = optionalConfig.get();
        // 此处需自己修改
        resources.setId(config.getId());
        configRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        configRepository.deleteById(id);
    }
}