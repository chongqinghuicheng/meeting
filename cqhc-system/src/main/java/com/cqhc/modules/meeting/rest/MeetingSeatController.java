package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingSeat;
import com.cqhc.modules.meeting.service.MeetingSeatService;
import com.cqhc.modules.meeting.service.dto.MeetingSeatDTO;
import com.cqhc.modules.meeting.service.query.MeetingSeatQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author huicheng
* @date 2019-04-25
*/
@RestController
@RequestMapping("api")
@Api(value = "/会议座次管理", description = "会议座次管理")
public class MeetingSeatController {

    @Autowired
    private MeetingSeatService meetingSeatService;

    @Autowired
    private MeetingSeatQueryService meetingSeatQueryService;

    private static final String ENTITY_NAME = "meetingSeat";

    @Log("查询座次")
    @GetMapping(value = "/meetingSeat")
    @ApiOperation(value = "查询座次")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_SEAT_ALL', 'MEETING_SEAT_SELECT')")
    public ResponseEntity getMeetingSeats(MeetingSeatDTO resources, Pageable pageable){
        return new ResponseEntity(meetingSeatQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("生成会议座次")
    @PostMapping(value = "/meetingSeat")
    @ApiOperation(value = "生成会议座次")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_SEAT_ALL', 'MEETING_SEAT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody MeetingSeat resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingSeatService.create(resources),HttpStatus.CREATED);
    }

    @Log("设置座次人员")
    @PutMapping(value = "/meetingSeat")
    @ApiOperation(value = "设置座次人员")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_SEAT_ALL', 'MEETING_SEAT_CREATE')")
    public ResponseEntity setPerson(@Validated @RequestBody MeetingSeat resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingSeatService.setPerson(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}