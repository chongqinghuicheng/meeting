package com.cqhc.modules.system.service.mapper;

import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.system.domain.UserGroup;
import com.cqhc.modules.system.service.dto.UserGroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author huicheng
* @date 2019-04-26
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserGroupMapper extends EntityMapper<UserGroupDTO, UserGroup> {

}