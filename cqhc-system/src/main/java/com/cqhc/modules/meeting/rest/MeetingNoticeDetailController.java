package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingNoticeDetail;
import com.cqhc.modules.meeting.service.MeetingNoticeDetailService;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDetailDTO;
import com.cqhc.modules.meeting.service.query.MeetingNoticeDetailQueryService;
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
public class MeetingNoticeDetailController {

    @Autowired
    private MeetingNoticeDetailService meetingNoticeDetailService;

    @Autowired
    private MeetingNoticeDetailQueryService meetingNoticeDetailQueryService;

    private static final String ENTITY_NAME = "meetingNoticeDetail";

    @Log("查询MeetingNoticeDetail")
    @GetMapping(value = "/meetingNoticeDetail")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingNoticeDetails(MeetingNoticeDetailDTO resources, Pageable pageable){
        return new ResponseEntity(meetingNoticeDetailQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingNoticeDetail")
    @PostMapping(value = "/meetingNoticeDetail")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingNoticeDetail resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingNoticeDetailService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingNoticeDetail")
    @PutMapping(value = "/meetingNoticeDetail")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingNoticeDetail resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingNoticeDetailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingNoticeDetail")
    @DeleteMapping(value = "/meetingNoticeDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingNoticeDetailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}