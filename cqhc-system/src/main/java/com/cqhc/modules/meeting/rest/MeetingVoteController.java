package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingInfo;
import com.cqhc.modules.meeting.domain.MeetingVote;
import com.cqhc.modules.meeting.domain.MeetingVoteDetail;
import com.cqhc.modules.meeting.repository.MeetingInfoRepository;
import com.cqhc.modules.meeting.service.MeetingInfoService;
import com.cqhc.modules.meeting.service.MeetingVoteDetailService;
import com.cqhc.modules.meeting.service.MeetingVoteService;
import com.cqhc.modules.meeting.service.dto.MeetingInfoDTO;
import com.cqhc.modules.meeting.service.dto.MeetingVoteDTO;
import com.cqhc.modules.meeting.service.query.MeetingVoteQueryService;
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
* @date 2019-04-26
*/
@RestController
@RequestMapping("api")
@Api(value = "/会议投票", description = "会议投票")
public class MeetingVoteController {

    @Autowired
    private MeetingVoteService meetingVoteService;

    @Autowired
    private MeetingVoteQueryService meetingVoteQueryService;

    @Autowired
    private MeetingInfoService meetingInfoService;

    private static final String ENTITY_NAME = "meetingVote";

    @Log("查询会议投票")
    @GetMapping(value = "/meetingVote")
    @ApiOperation(value = "查询会议投票", notes = "可根据标题、状态查询")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_VOTE_ALL', 'MEETING_INFO_SELECT')")
    public ResponseEntity getMeetingVotes(MeetingVoteDTO resources, Pageable pageable){
        return new ResponseEntity(meetingVoteQueryService.queryAll(resources, pageable),HttpStatus.OK);
    }

    /**
     * 获取本单位待进行或进行中的会议，新增时下拉框
     * @param id
     * @return
     */
    @GetMapping(value = "/meetingVote/getMeeting/{id}")
    @ApiOperation(value = "获取本单位待进行或进行中的会议")
    @ApiImplicitParam(name = "id", value = "单位id", required = true, dataType = "Long")
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_VOTE_ALL', 'MEETING_INFO_SELECT')")
    public ResponseEntity getUnitMeeting(@PathVariable Long id) {
        return new ResponseEntity(meetingInfoService.getMeeting(id), HttpStatus.OK);
    }

    /**
     * 根据所选会议自动填充参与人列表
     * @param id
     * @return
     */
    @GetMapping(value = "/meetingVote/getMeetingUser/{id}")
    @ApiOperation(value = "参与人列表")
    @ApiImplicitParam(name = "id", value = "会议id", required = true, dataType = "Long")
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_VOTE_ALL', 'MEETING_INFO_SELECT')")
    public ResponseEntity getMeetingUser(@PathVariable Long id) {
        return new ResponseEntity(meetingInfoService.getMeetingUser(id), HttpStatus.OK);
    }

    // /**
    //  * 全屏投影
    //  * @param id
    //  * @return
    //  */
    // @GetMapping(value = "/meetingVote/projection/{id}")
    // @ApiOperation(value = "全屏投影")
    // @ApiImplicitParam(name = "id", value = "会议id", required = true, dataType = "Long")
    // @PreAuthorize("hasAnyRole('ADMIN','MEETING_VOTE_ALL', 'MEETING_INFO_SELECT', 'MEETING_INFO_ALL', 'MEETING_INFO_EDIT')")
    // public ResponseEntity projection(@PathVariable Long id) {
    //     return new ResponseEntity(meetingVoteService.projection(id), HttpStatus.OK);
    // }

    @Log("新增会议投票")
    @PostMapping(value = "/meetingVote")
    @ApiOperation(value = "新增会议投票")
    @ApiImplicitParams({@ApiImplicitParam(name = "type",value = "标题",required = true,dataType ="Integer" ),
            @ApiImplicitParam(name = "remark",value = "描述",required = true,dataType = "String")})
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_INFO_ALL', 'MEETING_INFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody MeetingVote resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingVoteService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改会议投票")
    @PutMapping(value = "/meetingVote")
    @ApiOperation(value = "修改会议投票")
    @ApiImplicitParams({@ApiImplicitParam(name = "type",value = "标题",required = true,dataType ="Integer" ),
            @ApiImplicitParam(name = "actualNumber",value = "实到人数",required = true,dataType = "String"),
            @ApiImplicitParam(name = "remark",value = "描述",required = true,dataType = "String")})
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_INFO_ALL', 'MEETING_INFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody MeetingVote resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingVoteService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 点击左剪头改变状态
     * @param resources
     * @return
     */
    @PutMapping(value = "/meetingVote/updateLeftStatus")
    @ApiOperation(value = "点击左剪头改变状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "meeting",value = "会议",required = true,dataType ="MeetingInfo" ),
            @ApiImplicitParam(name = "title",value = "标题",required = true,dataType = "String"),
            @ApiImplicitParam(name = "type",value = "类型",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "actualNumber",value = "实到人数",dataType = "short"),
            @ApiImplicitParam(name = "status",value = "状态",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "createTime",value = "创建日期",required = true,dataType = "Timestamp"),
            @ApiImplicitParam(name = "remark",value = "描述",dataType = "String")})
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_INFO_ALL', 'MEETING_INFO_EDIT')")
    public ResponseEntity updateLeftStatus(@Validated @RequestBody MeetingVote resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingVoteService.updateLeftStatus(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 点击右箭头修改状态
     * @param resources
     * @return
     */
    @PutMapping(value = "/meetingVote/updateRightStatus")
    @ApiOperation(value = "点击右箭头修改状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "meeting",value = "会议",required = true,dataType ="MeetingInfo" ),
            @ApiImplicitParam(name = "title",value = "标题",required = true,dataType = "String"),
            @ApiImplicitParam(name = "type",value = "类型",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "actualNumber",value = "实到人数",dataType = "short"),
            @ApiImplicitParam(name = "status",value = "状态",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "createTime",value = "创建日期",required = true,dataType = "Timestamp"),
            @ApiImplicitParam(name = "remark",value = "描述",dataType = "String")})
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_INFO_ALL', 'MEETING_INFO_EDIT')")
    public ResponseEntity updateRightStatus(@Validated @RequestBody MeetingVote resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingVoteService.updateRightStatus(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除会议投票")
    @DeleteMapping(value = "/meetingVote/{id}")
    @ApiOperation(value = "删除会议投票")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEETING_INFO_ALL', 'MEETING_INFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingVoteService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}