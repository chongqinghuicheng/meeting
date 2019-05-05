package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingNotes;
import com.cqhc.modules.meeting.domain.MeetingNotice;
import com.cqhc.modules.meeting.service.MeetingNotesService;
import com.cqhc.modules.meeting.service.dto.MeetingNotesDTO;
import com.cqhc.modules.meeting.service.query.MeetingNotesQueryService;
import com.cqhc.modules.security.service.JwtPermissionService;
import com.cqhc.modules.system.domain.User;
import com.cqhc.modules.system.service.UserService;
import com.cqhc.utils.SecurityContextHolder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    UserService userService;

    @Autowired
    JwtPermissionService jwtPermissionService;

    private static final String ENTITY_NAME = "meetingNotes";

    @Log("查询MeetingNotes")
    @GetMapping(value = "/meetingNotes")
    @ApiOperation(value = "查询笔记列表",notes = "关键字查询")//给接口文档的方法备注
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_NOTES_ALL','MEETING_NOTES_SELECT')")
    public ResponseEntity getMeetingNotess(MeetingNotesDTO resources){
        //获取该用户
        UserDetails userDetails = SecurityContextHolder.getUserDetails();//登录后保存的用户验证信息。
        User user=userService.findByName(userDetails.getUsername()); //获取用户信息

        //判断用户的权限
        if(jwtPermissionService.getPermission("MEETING_NOTES_DELETE_ALL")){
            //返回所有笔记列表
            return new ResponseEntity(meetingNotesQueryService.queryAll(resources),HttpStatus.OK);//返回所有列表
        }else {
            //返回自己创建的笔记
            return new ResponseEntity(meetingNotesService.getMeetingNotes(user.getId()),HttpStatus.OK);//返回自己接收到的通知
        }
    }

    @Log("新增MeetingNotes")
    @PostMapping(value = "/meetingNotes")
    @ApiOperation(value = "创建笔记")//给接口文档的方法备注
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_NOTES_ALL','MEETING_NOTES_CREATE')")
    @ApiImplicitParams({@ApiImplicitParam(name = "title",value = "标题",required = true,dataType ="String" ),
            @ApiImplicitParam(name = "content",value = "内容",required = true,dataType = "String")})
    public ResponseEntity create(@Validated @RequestBody MeetingNotes resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(meetingNotesService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingNotes")
    @PutMapping(value = "/meetingNotes")
    @ApiOperation(value = "修改笔记")//给接口文档的方法备注
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_NOTES_ALL','MEETING_NOTES_EDIT')")
    public ResponseEntity update(@Validated @RequestBody MeetingNotes resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        //获取该用户
        UserDetails userDetails = SecurityContextHolder.getUserDetails();//登录后保存的用户验证信息。
        User user=userService.findByName(userDetails.getUsername()); //获取用户信息
        //判断是否为自己创建的笔记
        if(!resources.getUserId().equals(user.getId())){
            throw new BadRequestException("不能修改！");
        }

        meetingNotesService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingNotes")
    @ApiOperation(value = "删除笔记")
    @DeleteMapping(value = "/meetingNotes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_NOTES_DELETE','MEETING_NOTES_DELETE_ALL')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingNotesService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}