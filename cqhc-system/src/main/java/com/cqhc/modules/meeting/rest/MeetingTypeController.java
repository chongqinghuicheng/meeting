package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingType;
import com.cqhc.modules.meeting.service.MeetingTypeService;
import com.cqhc.modules.meeting.service.dto.MeetingTypeDTO;
import com.cqhc.modules.meeting.service.query.MeetingTypeQueryService;
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
public class MeetingTypeController {

    @Autowired
    private MeetingTypeService meetingTypeService;

    @Autowired
    private MeetingTypeQueryService meetingTypeQueryService;

    private static final String ENTITY_NAME = "meetingType";

    @Log("查询MeetingType")
    @GetMapping(value = "/meetingType")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingTypes(MeetingTypeDTO resources, Pageable pageable){
        return new ResponseEntity(meetingTypeQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingType")
    @PostMapping(value = "/meetingType")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingType resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingTypeService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingType")
    @PutMapping(value = "/meetingType")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingType resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingTypeService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingType")
    @DeleteMapping(value = "/meetingType/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingTypeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}