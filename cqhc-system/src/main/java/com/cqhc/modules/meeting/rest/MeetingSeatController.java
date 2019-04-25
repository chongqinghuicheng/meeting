package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingSeat;
import com.cqhc.modules.meeting.service.MeetingSeatService;
import com.cqhc.modules.meeting.service.dto.MeetingSeatDTO;
import com.cqhc.modules.meeting.service.query.MeetingSeatQueryService;
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
public class MeetingSeatController {

    @Autowired
    private MeetingSeatService meetingSeatService;

    @Autowired
    private MeetingSeatQueryService meetingSeatQueryService;

    private static final String ENTITY_NAME = "meetingSeat";

    @Log("查询MeetingSeat")
    @GetMapping(value = "/meetingSeat")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingSeats(MeetingSeatDTO resources, Pageable pageable){
        return new ResponseEntity(meetingSeatQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingSeat")
    @PostMapping(value = "/meetingSeat")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingSeat resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingSeatService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingSeat")
    @PutMapping(value = "/meetingSeat")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingSeat resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingSeatService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingSeat")
    @DeleteMapping(value = "/meetingSeat/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingSeatService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}