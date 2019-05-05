package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingNotes;
import com.cqhc.modules.meeting.service.MeetingNotesService;
import com.cqhc.modules.meeting.service.dto.MeetingNotesDTO;
import com.cqhc.modules.meeting.service.query.MeetingNotesQueryService;
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
public class MeetingNotesController {

    @Autowired
    private MeetingNotesService meetingNotesService;

    @Autowired
    private MeetingNotesQueryService meetingNotesQueryService;

    private static final String ENTITY_NAME = "meetingNotes";

    @Log("查询MeetingNotes")
    @GetMapping(value = "/meetingNotes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getMeetingNotess(MeetingNotesDTO resources, Pageable pageable){
        return new ResponseEntity(meetingNotesQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增MeetingNotes")
    @PostMapping(value = "/meetingNotes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody MeetingNotes resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingNotesService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingNotes")
    @PutMapping(value = "/meetingNotes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody MeetingNotes resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingNotesService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingNotes")
    @DeleteMapping(value = "/meetingNotes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingNotesService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}