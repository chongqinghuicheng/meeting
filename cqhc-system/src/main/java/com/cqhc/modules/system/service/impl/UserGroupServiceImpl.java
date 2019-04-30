package com.cqhc.modules.system.service.impl;

import com.cqhc.exception.EntityExistException;
import com.cqhc.modules.system.domain.UserGroup;
import com.cqhc.modules.system.repository.UserGroupRepository;
import com.cqhc.modules.system.service.UserGroupService;
import com.cqhc.modules.system.service.dto.UserGroupDTO;
import com.cqhc.modules.system.service.mapper.UserGroupMapper;
import com.cqhc.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public UserGroupDTO findById(Long id) {
        Optional<UserGroup> userGroup = userGroupRepository.findById(id);
        ValidationUtil.isNull(userGroup,"UserGroup","id",id);
        return userGroupMapper.toDto(userGroup.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserGroupDTO create(UserGroup resources) {

        if(userGroupRepository.findByName(resources.getName())!=null){
            throw new EntityExistException(UserGroup.class,"name",resources.getName());
        }
        // 利用@JoinTable注解，直接创建关系表
        UserGroup userGroup = userGroupRepository.save(resources);
        return userGroupMapper.toDto(userGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserGroup resources) {
        // 查询数据库本来的人员分组
        Optional<UserGroup> optionalUserGroup = userGroupRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalUserGroup,"UserGroup","id",resources.getId());

        UserGroup userGroup = optionalUserGroup.get();

        UserGroup userGroup1 = userGroupRepository.findByName(userGroup.getName());

        if(userGroup1 !=null&&!userGroup.getId().equals(userGroup1.getId())){
            throw new EntityExistException(UserGroup.class,"name",resources.getName());
        }

        userGroup.setName(resources.getName());
        userGroup.setSort(resources.getSort());
        userGroup.setUsers(resources.getUsers());
        userGroupRepository.save(userGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userGroupRepository.deleteById(id);
    }
}