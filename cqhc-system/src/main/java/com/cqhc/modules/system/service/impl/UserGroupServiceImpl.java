package com.cqhc.modules.system.service.impl;

import com.cqhc.modules.system.domain.UserGroup;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.system.repository.UserGroupRepository;
import com.cqhc.modules.system.service.UserGroupService;
import com.cqhc.modules.system.service.dto.UserGroupDTO;
import com.cqhc.modules.system.service.mapper.UserGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-26
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public UserGroupDTO findById(Long id) {
        Optional<UserGroup> userGroup = userGroupRepository.findById(id);
        ValidationUtil.isNull(userGroup,"UserGroup","id",id);
        return userGroupMapper.toDto(userGroup.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserGroupDTO create(UserGroup resources) {
        return userGroupMapper.toDto(userGroupRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserGroup resources) {
        Optional<UserGroup> optionalUserGroup = userGroupRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalUserGroup,"UserGroup","id",resources.getId());

        UserGroup userGroup = optionalUserGroup.get();
        // 此处需自己修改
        resources.setId(userGroup.getId());
        userGroupRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userGroupRepository.deleteById(id);
    }
}