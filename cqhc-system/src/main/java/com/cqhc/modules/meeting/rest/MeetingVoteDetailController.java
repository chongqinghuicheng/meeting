package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingVoteDetail;
import com.cqhc.modules.meeting.service.MeetingVoteDetailService;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDetailDTO;
import com.cqhc.modules.meeting.service.query.MeetingVoteDetailQueryService;
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
public class MeetingVoteDetailController {

    @Autowired
    private MeetingVoteDetailService meetingVoteDetailService;

    @Autowired
    private MeetingVoteDetailQueryService meetingVoteDetailQueryService;

    private static final String ENTITY_NAME = "meetingVoteDetail";

    @Log("查询MeetingVoteDetail")
    @GetMapping(value = "/meetingVoteDetail")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingVoteDetails(MeetingVoteDetailDTO resources, Pageable pageable){
        return new ResponseEntity(meetingVoteDetailQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingVoteDetail")
    @PostMapping(value = "/meetingVoteDetail")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingVoteDetail resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingVoteDetailService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingVoteDetail")
    @PutMapping(value = "/meetingVoteDetail")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingVoteDetail resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingVoteDetailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingVoteDetail")
    @DeleteMapping(value = "/meetingVoteDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingVoteDetailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}