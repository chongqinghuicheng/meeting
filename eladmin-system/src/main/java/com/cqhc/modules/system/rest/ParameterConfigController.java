package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.modules.system.service.ParameterConfigService;
import com.cqhc.modules.system.service.dto.ParameterConfigDTO;
import com.cqhc.modules.system.service.query.ParameterConfigQueryService;
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
public class ParameterConfigController {

    @Autowired
    private ParameterConfigService parameterConfigService;

    @Autowired
    private ParameterConfigQueryService parameterConfigQueryService;

    private static final String ENTITY_NAME = "parameterConfig";

    @Log("查询ParameterConfig")
    @GetMapping(value = "/parameterConfig")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getParameterConfigs(ParameterConfigDTO resources, Pageable pageable){
        return new ResponseEntity(parameterConfigQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增ParameterConfig")
    @PostMapping(value = "/parameterConfig")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody ParameterConfig resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(parameterConfigService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ParameterConfig")
    @PutMapping(value = "/parameterConfig")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody ParameterConfig resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        parameterConfigService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ParameterConfig")
    @DeleteMapping(value = "/parameterConfig/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        parameterConfigService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}