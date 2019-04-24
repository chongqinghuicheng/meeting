package com.cqhc.modules.system.rest;

import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.service.UnitService;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.service.query.UnitQueryService;
import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.service.UnitService;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.service.query.UnitQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author huicheng
* @date 2019-04-23
*/
@RestController
@RequestMapping("api")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @Autowired
    private UnitQueryService unitQueryService;

    private static final String ENTITY_NAME = "unit";

    @Log("查询Unit")
    @GetMapping(value = "/unit")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getUnits(UnitDTO resources, Pageable pageable){
        return new ResponseEntity(unitQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增Unit")
    @PostMapping(value = "/unit")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody Unit resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(unitService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Unit")
    @PutMapping(value = "/unit")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody Unit resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        unitService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Unit")
    @DeleteMapping(value = "/unit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        unitService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}