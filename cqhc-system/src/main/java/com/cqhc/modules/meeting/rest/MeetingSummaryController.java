package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingSummary;
import com.cqhc.modules.meeting.service.MeetingInfoService;
import com.cqhc.modules.meeting.service.MeetingSummaryService;
import com.cqhc.modules.meeting.service.dto.MeetingSummaryDTO;
import com.cqhc.modules.meeting.service.query.MeetingSummaryQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
* @date 2019-04-30
*/
@RestController
@RequestMapping("api")
@Api(value = "/会议纪要", description = "会议纪要")
public class MeetingSummaryController {

    @Autowired
    private MeetingSummaryService meetingSummaryService;

    @Autowired
    private MeetingSummaryQueryService meetingSummaryQueryService;

    @Autowired
    private MeetingInfoService meetingInfoService;

    private static final String ENTITY_NAME = "meetingSummary";

    @Log("查询会议纪要")
    @GetMapping(value = "/meetingSummary")
    @ApiOperation(value = "查询会议纪要", notes = "可根据标题、所属会议、创建人、创建日期查询")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_SUMMARY_ALL', 'MEETING_SUMMARY_SELECT')")
    public ResponseEntity getMeetingSummarys(MeetingSummaryDTO resources, Pageable pageable){
        return new ResponseEntity(meetingSummaryQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    /**
     * 获取本单位所有的会议
     * @param id
     * @return
     */
    @GetMapping(value = "/meetingSummary/getUnitMeeting/{id}")
    @ApiOperation(value = "获取本单位所有的会议")
    @ApiImplicitParam(name = "id", value = "单位id", required = true, dataType = "Long")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_SUMMARY_ALL', 'MEETING_SUMMARY_SELECT')")
    public ResponseEntity getByUnitMeetingId(@PathVariable Long id){
        return new ResponseEntity(meetingInfoService.getUnitMeeting(id),HttpStatus.OK);
    }

    @Log("新增会议纪要")
    @PostMapping(value = "/meetingSummary")
    @ApiOperation(value = "新增会议纪要")
    @ApiImplicitParams({@ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "meeting", value = "所属会议", required = true, dataType = "MeetingType"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "String")})
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_SUMMARY_ALL', 'MEETING_SUMMARY_CREATE')")
    public ResponseEntity create(@Validated @RequestBody MeetingSummary resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingSummaryService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改会议纪要")
    @PutMapping(value = "/meetingSummary")
    @ApiOperation(value = "修改会议纪要")
    @ApiImplicitParams({@ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "meeting", value = "所属会议", required = true, dataType = "MeetingType"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "String")})
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_SUMMARY_ALL', 'MEETING_SUMMARY_EDIT')")
    public ResponseEntity update(@Validated @RequestBody MeetingSummary resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingSummaryService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除会议纪要")
    @DeleteMapping(value = "/meetingSummary/{id}")
    @ApiOperation(value = "删除会议纪要")
    @ApiImplicitParam(name = "id", value = "会议纪要id", required = true, dataType = "Long")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_SUMMARY_ALL', 'MEETING_SUMMARY_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingSummaryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}