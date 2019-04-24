package com.cqhc.modules.system.service.mapper;

import com.cqhc.modules.system.domain.Role;
import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.system.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = {PermissionMapper.class, MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
