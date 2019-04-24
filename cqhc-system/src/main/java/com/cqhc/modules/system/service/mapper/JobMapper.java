package com.cqhc.modules.system.service.mapper;

import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.system.domain.Job;
import com.cqhc.modules.system.service.dto.JobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",uses = {DeptMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends EntityMapper<JobDTO, Job> {

}