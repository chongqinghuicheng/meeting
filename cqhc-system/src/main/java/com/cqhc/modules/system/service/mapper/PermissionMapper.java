package com.cqhc.modules.system.service.mapper;

import com.cqhc.modules.system.domain.Permission;
import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.system.service.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
