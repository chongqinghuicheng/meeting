package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.MeetingInfo;
import com.cqhc.modules.system.service.MeetingInfoService;
import com.cqhc.modules.system.service.dto.MeetingInfoDTO;
import com.cqhc.modules.system.service.query.MeetingInfoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author huicheng
* @date 2019-04-24
*/
@RestController
@RequestMapping("api")
public class MeetingInfoController {

    @Autowired
    private MeetingInfoService meetingInfoService;

    @Autowired
    private MeetingInfoQueryService meetingInfoQueryService;

    private static final String ENTITY_NAME = "meetingInfo";

    @Log("查询MeetingInfo")
    @GetMapping(value = "/meetingInfo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingInfos(MeetingInfoDTO resources, Pageable pageable){
        return new ResponseEntity(meetingInfoQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingInfo")
    @PostMapping(value = "/meetingInfo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingInfo resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingInfo")
    @PutMapping(value = "/meetingInfo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingInfo resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingInfo")
    @DeleteMapping(value = "/meetingInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}