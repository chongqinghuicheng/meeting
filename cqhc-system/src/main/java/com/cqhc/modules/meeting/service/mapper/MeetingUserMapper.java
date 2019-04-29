package com.cqhc.modules.meeting.service.mapper;

import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.meeting.domain.MeetingUser;
import com.cqhc.modules.meeting.service.dto.MeetingUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author huicheng
* @date 2019-04-28
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeetingUserMapper extends EntityMapper<MeetingUserDTO, MeetingUser> {

}