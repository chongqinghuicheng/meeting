package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.FileInfo;
import com.cqhc.modules.system.service.FileInfoService;
import com.cqhc.modules.system.service.dto.FileInfoDTO;
import com.cqhc.modules.system.service.query.FileInfoQueryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author huicheng
* @date 2019-04-28
*/
@RestController
@RequestMapping("api")
@Api(value = "/通用文件信息", description = "通用文件信息")
public class FileInfoController {

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private FileInfoQueryService fileInfoQueryService;

    private static final String ENTITY_NAME = "fileInfo";

    @Log("查询文件信息")
    @GetMapping(value = "/fileInfo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getFileInfos(FileInfoDTO resources, Pageable pageable){
        return new ResponseEntity(fileInfoQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增文件信息")
    @PostMapping(value = "/fileInfo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody FileInfo resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(fileInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改文件信息")
    @PutMapping(value = "/fileInfo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody FileInfo resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        fileInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除文件信息")
    @DeleteMapping(value = "/fileInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        fileInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}