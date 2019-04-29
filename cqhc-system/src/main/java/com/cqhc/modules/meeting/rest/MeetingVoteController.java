package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingVote;
import com.cqhc.modules.meeting.service.MeetingVoteService;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDTO;
import com.cqhc.modules.meeting.service.query.MeetingVoteQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author huicheng
* @date 2019-04-26
*/
@RestController
@RequestMapping("api")
public class MeetingVoteController {

    @Autowired
    private MeetingVoteService meetingVoteService;

    @Autowired
    private MeetingVoteQueryService meetingVoteQueryService;

    private static final String ENTITY_NAME = "meetingVote";

    @Log("查询MeetingVote")
    @GetMapping(value = "/meetingVote")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingVotes(MeetingVoteDTO resources, Pageable pageable){
        return new ResponseEntity(meetingVoteQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingVote")
    @PostMapping(value = "/meetingVote")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingVote resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingVoteService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingVote")
    @PutMapping(value = "/meetingVote")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingVote resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingVoteService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingVote")
    @DeleteMapping(value = "/meetingVote/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingVoteService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}