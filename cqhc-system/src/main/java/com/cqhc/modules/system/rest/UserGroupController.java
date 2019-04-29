package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.UserGroup;
import com.cqhc.modules.system.service.UserGroupService;
import com.cqhc.modules.system.service.dto.UserGroupDTO;
import com.cqhc.modules.system.service.query.UserGroupQueryService;
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
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserGroupQueryService userGroupQueryService;

    private static final String ENTITY_NAME = "userGroup";

    @Log("查询UserGroup")
    @GetMapping(value = "/userGroup")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getUserGroups(UserGroupDTO resources, Pageable pageable){
        return new ResponseEntity(userGroupQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增UserGroup")
    @PostMapping(value = "/userGroup")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody UserGroup resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(userGroupService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改UserGroup")
    @PutMapping(value = "/userGroup")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody UserGroup resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        userGroupService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除UserGroup")
    @DeleteMapping(value = "/userGroup/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        userGroupService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}