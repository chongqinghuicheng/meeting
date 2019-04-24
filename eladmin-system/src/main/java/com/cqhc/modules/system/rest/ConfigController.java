package com.cqhc.modules.system.rest;

import com.cqhc.modules.system.domain.Config;
import com.cqhc.modules.system.service.ConfigService;
import com.cqhc.modules.system.service.dto.ConfigDTO;
import com.cqhc.modules.system.service.query.ConfigQueryService;
import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.Config;
import com.cqhc.modules.system.service.ConfigService;
import com.cqhc.modules.system.service.dto.ConfigDTO;
import com.cqhc.modules.system.service.query.ConfigQueryService;
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
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private ConfigQueryService configQueryService;

    private static final String ENTITY_NAME = "config";

    @Log("查询Config")
    @GetMapping(value = "/config")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getConfigs(ConfigDTO resources, Pageable pageable){
        return new ResponseEntity(configQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增Config")
    @PostMapping(value = "/config")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody Config resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(configService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Config")
    @PutMapping(value = "/config")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody Config resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        configService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Config")
    @DeleteMapping(value = "/config/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        configService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}