package com.cqhc.modules.system.service.mapper;

import com.cqhc.modules.system.domain.User;
import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.system.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",uses = {RoleMapper.class, DeptMapper.class, JobMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDTO, User> {

}
