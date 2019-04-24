package com.cqhc.modules.system.service.mapper;

import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.modules.system.service.dto.ParameterConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author huicheng
* @date 2019-04-24
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParameterConfigMapper extends EntityMapper<ParameterConfigDTO, ParameterConfig> {

}