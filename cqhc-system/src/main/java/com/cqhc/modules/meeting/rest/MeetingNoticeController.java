package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingNotice;
import com.cqhc.modules.meeting.service.MeetingNoticeDetailService;
import com.cqhc.modules.meeting.service.MeetingNoticeService;
import com.cqhc.modules.meeting.service.dto.MeetingNoticeDTO;
import com.cqhc.modules.meeting.service.query.MeetingNoticeQueryService;
import com.cqhc.modules.security.rest.AuthenticationController;
import com.cqhc.modules.security.service.JwtPermissionService;
import com.cqhc.modules.system.domain.User;
import com.cqhc.modules.system.service.UserService;
import com.cqhc.utils.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.Request;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.EntityManagerProxy;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.security.auth.message.callback.PrivateKeyCallback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
* @author huicheng
* @date 2019-04-26
*/
@RestController
@RequestMapping("api")
@Api(value = "通知管理",description = "通知管理") //API接口注释
public class MeetingNoticeController {

    @Autowired
    private MeetingNoticeService meetingNoticeService;

    @Autowired
    private MeetingNoticeQueryService meetingNoticeQueryService;

    @Autowired
    private MeetingNoticeDetailService meetingNoticeDetailService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserService userService;

    @Autowired
    JwtPermissionService jwtPermissionService;

    private static final String ENTITY_NAME = "meetingNotice";

    @Log("查询MeetingNotice")
    @GetMapping(value = "/meetingNotice")
    @ApiOperation(value = "查询通知列表",notes = "关键字查询")//给接口文档的方法备注
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_NOTICE_SELECT','MEETING_NOTICE_ALL')")
    public ResponseEntity getMeetingNotices(MeetingNoticeDTO resources){
        //获取该用户
        UserDetails userDetails = SecurityContextHolder.getUserDetails();//登录后保存的用户验证信息。
        User user=userService.findByName(userDetails.getUsername()); //获取用户信息

        //判断用户的权限
        if(jwtPermissionService.getPermission("MEETING_NOTICE_EDIT_ALL")){
            //返回所有通知列表
            return new ResponseEntity(meetingNoticeQueryService.queryAll(resources),HttpStatus.OK);//返回所有列表
        }else if (jwtPermissionService.getPermission("MEETING_NOTICE_EDIT")){
            //返回自己创建的和接收到的通知列表
            List<MeetingNotice> list1=meetingNoticeService.findByUserId(user.getId());
            List<MeetingNotice> list2=meetingNoticeService.findByNoticeId(user.getId());
            list1.addAll(list2); //合并两个集合
            return new ResponseEntity(list1,HttpStatus.OK);
        }else {
            return new ResponseEntity(meetingNoticeService.findByUserId(user.getId()),HttpStatus.OK);//返回自己接收到的通知
        }
    }

    @Log("新增MeetingNotice")
    @PostMapping(value = "/meetingNotice")
    @ApiOperation(value = "创建通知")//给接口文档的方法备注
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_NOTICE_ALL','MEETING_NOTICE_CREATE')")
    @ApiImplicitParams({@ApiImplicitParam(name = "title",value = "标题",required = true,dataType ="String" ),
            @ApiImplicitParam(name = "startTime",value = "生效时间",required = true,dataType = "Timestamp"),
            @ApiImplicitParam(name = "endTime",value = "失效时间",dataType = "Timestamp"),
            @ApiImplicitParam(name = "users",value = "创建人",dataType = "User"),
            @ApiImplicitParam(name = "content",value = "内容",required = true,dataType = "String")})
    public ResponseEntity create(@Validated @RequestBody MeetingNotice resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }


        return new ResponseEntity(meetingNoticeService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingNotice")
    @PutMapping(value = "/meetingNotice")
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_NOTICE_ALL','MEETING_NOTICE_EDIT','MEETING_NOTICE_EDIT_ALL')")
    public ResponseEntity update(@Validated @RequestBody MeetingNotice resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingNoticeService.update(resources);
        //修改后删除相对的通知明细
        meetingNoticeDetailService.deleteByNoticeId(resources.getId());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MeetingNotice")
    @DeleteMapping(value = "/meetingNotice/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_NOTICE_ALL','MEETING_NOTICE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        meetingNoticeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}