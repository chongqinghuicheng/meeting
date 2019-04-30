package com.cqhc.modules.meeting.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.meeting.domain.MeetingType;
import com.cqhc.modules.meeting.service.MeetingTypeService;
import com.cqhc.modules.meeting.service.dto.MeetingTypeDTO;
import com.cqhc.modules.meeting.service.query.MeetingTypeQueryService;
import com.cqhc.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.util.HashSet;
import java.util.Set;

/**
* @author huicheng
* @date 2019-04-25
*/
@RestController
@RequestMapping("api")
@Api(value = "会议类型",description = "会议类型") //API接口注释
public class MeetingTypeController {

    @Autowired
    private MeetingTypeService meetingTypeService;

    @Autowired
    private MeetingTypeQueryService meetingTypeQueryService;

    private static final String ENTITY_NAME = "meetingType";

    @Log("查询MeetingType")
    @GetMapping(value = "/meetingType")
    @ApiOperation(value = "查询会议类型",notes = "可根据类型名称、描述、单位名称查询")//给接口文档的方法备注
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_TYPE_ALL','MEETING_TYPE_SELECT')") //方法的访问权限授权
    public ResponseEntity getMeetingTypes( MeetingTypeDTO meetingTypeDTO){
        return new ResponseEntity(meetingTypeQueryService.queryAll(meetingTypeDTO),HttpStatus.OK);
    }

    @Log("新增MeetingType")
    @PostMapping(value = "/meetingType")
    @ApiOperation(value = "新增会议类型")
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_TYPE_ALL','MEETING_TYPE_CREATE')")
    @ApiImplicitParams({@ApiImplicitParam(name = "sort",value = "序号",required = true,dataType ="Integer" ),
                    @ApiImplicitParam(name = "name",value = "名称",required = true,dataType = "String"),
                    @ApiImplicitParam(name = "enabled",value = "状态",required = true,dataType = "String"),
                    @ApiImplicitParam(name = "remark",value = "描述",dataType = "String")})
    public ResponseEntity create(@Validated @RequestBody MeetingType resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }

        return new ResponseEntity(meetingTypeService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改MeetingType")
    @PutMapping(value = "/meetingType")
    @ApiOperation(value = "会议类型修改")
    @PreAuthorize("hasAnyRole('ADMIN','MEETING_TYPE_ALL','MEETING_TYPE_EDIT')")
    @ApiImplicitParams({@ApiImplicitParam(name = "sort",value = "序号",required = true,dataType ="Integer" ),
            @ApiImplicitParam(name = "name",value = "名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "enabled",value = "状态",required = true,dataType = "String"),
            @ApiImplicitParam(name = "remark",value = "描述",dataType = "String")})
    public ResponseEntity update(@Validated @RequestBody MeetingType resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        meetingTypeService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}