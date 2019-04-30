package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingSummary;
import com.cqhc.modules.meeting.service.MeetingSummaryService;
import com.cqhc.modules.meeting.service.dto.MeetingSummaryDTO;
import com.cqhc.modules.meeting.service.query.MeetingSummaryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author huicheng
* @date 2019-04-30
*/
@RestController
@RequestMapping("api")
public class MeetingSummaryController {

    @Autowired
    private MeetingSummaryService meetingSummaryService;

    @Autowired
    private MeetingSummaryQueryService meetingSummaryQueryService;

    private static final String ENTITY_NAME = "meetingSummary";

    @Log("查询MeetingSummary")
    @GetMapping(value = "/meetingSummary")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingSummarys(MeetingSummaryDTO resources, Pageable pageable){
        return new ResponseEntity(meetingSummaryQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingSummary")
    @PostMapping(value = "/meetingSummary")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingSummary resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingSummaryService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingSummary")
    @PutMapping(value = "/meetingSummary")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingSummary resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingSummaryService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingSummary")
    @DeleteMapping(value = "/meetingSummary/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingSummaryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}