package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingNotice;
import com.cqhc.modules.meeting.service.MeetingNoticeService;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDTO;
import com.cqhc.modules.meeting.service.query.MeetingNoticeQueryService;
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
public class MeetingNoticeController {

    @Autowired
    private MeetingNoticeService meetingNoticeService;

    @Autowired
    private MeetingNoticeQueryService meetingNoticeQueryService;

    private static final String ENTITY_NAME = "meetingNotice";

    @Log("查询MeetingNotice")
    @GetMapping(value = "/meetingNotice")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingNotices(MeetingNoticeDTO resources, Pageable pageable){
        return new ResponseEntity(meetingNoticeQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingNotice")
    @PostMapping(value = "/meetingNotice")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingNotice resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingNoticeService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingNotice")
    @PutMapping(value = "/meetingNotice")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingNotice resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingNoticeService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingNotice")
    @DeleteMapping(value = "/meetingNotice/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingNoticeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}