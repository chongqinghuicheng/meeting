package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.service.UnitService;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.service.query.UnitQueryService;
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
* @date 2019-04-24
*/
@RestController
@RequestMapping("api")
@Api(value = "/单位管理", description = "单位管理")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @Autowired
    private UnitQueryService unitQueryService;

    private static final String ENTITY_NAME = "unit";

    @Log("查询单位")
    @GetMapping(value = "/unit")
    @ApiOperation(value = "查询单位", notes = "可根据所在地区、关键字、使用版本、状态查询")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_ALL', 'DICT_SELECT')")
    public ResponseEntity getUnits(UnitDTO resources, Pageable pageable){
        return new ResponseEntity(unitQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增单位")
    @PostMapping(value = "/unit")
    @ApiOperation(value = "新增单位")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "单位名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "areaCode", value = "所在地区", required = true, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "详细地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "workTel", value = "单位电话", required = true, dataType = "String"),
            @ApiImplicitParam(name = "principal", value = "负责人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "principalPosition", value = "负责人职务", dataType = "String"),
            @ApiImplicitParam(name = "principalPhone", value = "负责人电话", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "contact", value = "联系人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "contactPosition", value = "负责人职务", dataType = "String"),
            @ApiImplicitParam(name = "version", value = "联系人电话", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "short", value = "使用版本", required = true, dataType = "short"),
            @ApiImplicitParam(name = "terminalNum", value = "终端数", required = true, dataType = "short"),
            @ApiImplicitParam(name = "enabled", value = "状态", required = true, dataType = "Boolean")})
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_ALL', 'DICT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Unit resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(unitService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改单位")
    @PutMapping(value = "/unit")
    @ApiOperation(value = "修改单位")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "单位名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "areaCode", value = "所在地区", required = true, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "详细地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "workTel", value = "单位电话", required = true, dataType = "String"),
            @ApiImplicitParam(name = "principal", value = "负责人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "principalPosition", value = "负责人职务", dataType = "String"),
            @ApiImplicitParam(name = "principalPhone", value = "负责人电话", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "contact", value = "联系人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "contactPosition", value = "负责人职务", dataType = "String"),
            @ApiImplicitParam(name = "version", value = "联系人电话", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "short", value = "使用版本", required = true, dataType = "short"),
            @ApiImplicitParam(name = "terminalNum", value = "终端数", required = true, dataType = "short"),
            @ApiImplicitParam(name = "enabled", value = "状态", required = true, dataType = "Boolean")})
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_ALL', 'DICT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Unit resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        unitService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除单位")
    @DeleteMapping(value = "/unit/{id}")
    @ApiOperation(value = "删除单位")
    @ApiImplicitParam(name = "id", value = "单位id", required = true, dataType = "Long")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_ALL', 'DICT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        unitService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}