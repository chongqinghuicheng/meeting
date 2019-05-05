package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.service.UnitService;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.service.query.UnitQueryService;
import io.swagger.annotations.Api;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_ALL', 'DICT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        unitService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}