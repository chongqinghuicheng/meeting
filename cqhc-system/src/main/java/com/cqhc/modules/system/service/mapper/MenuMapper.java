package com.cqhc.modules.system.service.mapper;

import com.cqhc.modules.system.domain.Menu;
import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.system.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}
