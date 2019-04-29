package com.cqhc.modules.meeting.service.mapper;

import com.cqhc.mapper.EntityMapper;
import com.cqhc.modules.meeting.domain.MeetingVote;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author huicheng
* @date 2019-04-26
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeetingVoteMapper extends EntityMapper<MeetingVoteDTO, MeetingVote> {

}