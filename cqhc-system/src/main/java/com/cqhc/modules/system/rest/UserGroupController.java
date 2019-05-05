package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.UserGroup;
import com.cqhc.modules.system.service.UserGroupService;
import com.cqhc.modules.system.service.query.UserGroupQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(value = "/人员分组", description = "人员分组")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserGroupQueryService userGroupQueryService;

    private static final String ENTITY_NAME = "userGroup";

    @Log("查询人员分组")
    @GetMapping(value = "/userGroup")
    @ApiOperation(value = "人员分组查询")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUserGroups(Pageable pageable){
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Object ListBySort = userGroupQueryService.findBySort(pageNumber, pageSize);
        return new ResponseEntity(ListBySort,HttpStatus.OK);
    }

    @Log("新增人员分组")
    @PostMapping(value = "/userGroup")
    @ApiOperation(value = "人员分组新增")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody UserGroup resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(userGroupService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改人员分组")
    @PutMapping(value = "/userGroup")
    @ApiOperation(value = "人员分组修改")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody UserGroup resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        userGroupService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除人员分组")
    @DeleteMapping(value = "/userGroup/{id}")
    @ApiOperation(value = "人员分组删除")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @ApiImplicitParam(name = "id", value = "人员分组id", required = true, dataType = "Long")
    public ResponseEntity delete(@PathVariable Long id){
        userGroupService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}