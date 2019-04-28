package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingUser;
import com.cqhc.modules.meeting.service.MeetingUserService;
import com.cqhc.modules.meeting.service.dto.MeetingUserDTO;
import com.cqhc.modules.meeting.service.query.MeetingUserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author huicheng
* @date 2019-04-28
*/
@RestController
@RequestMapping("api")
public class MeetingUserController {

    @Autowired
    private MeetingUserService meetingUserService;

    @Autowired
    private MeetingUserQueryService meetingUserQueryService;

    private static final String ENTITY_NAME = "meetingUser";

    @Log("查询MeetingUser")
    @GetMapping(value = "/meetingUser")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingUsers(MeetingUserDTO resources, Pageable pageable){
        return new ResponseEntity(meetingUserQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingUser")
    @PostMapping(value = "/meetingUser")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingUser resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingUserService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingUser")
    @PutMapping(value = "/meetingUser")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingUser resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingUserService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingUser")
    @DeleteMapping(value = "/meetingUser/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingUserService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}