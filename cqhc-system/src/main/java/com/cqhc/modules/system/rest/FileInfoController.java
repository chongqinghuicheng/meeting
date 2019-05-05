package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.FileInfo;
import com.cqhc.modules.system.service.FileInfoService;
import com.cqhc.modules.system.service.dto.FileInfoDTO;
import com.cqhc.modules.system.service.query.FileInfoQueryService;
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
import org.springframework.web.multipart.MultipartFile;

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
    @ApiOperation(value = "文件信息查询")
    public ResponseEntity getFileInfos(FileInfoDTO resources, Pageable pageable){
        return new ResponseEntity(fileInfoQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("上传文件信息")
    @PostMapping(value = "/fileInfo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "上传文件信息",notes = "通过file传入文件名、路径等")
    public ResponseEntity create(@RequestParam MultipartFile file,@Validated @RequestBody FileInfo resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        FileInfo upload = fileInfoService.upload(file);
        resources.setFileName(upload.getFileName());
        resources.setFilePath(upload.getFilePath());
        resources.setFileSize(upload.getFileSize());
        resources.setFilePage(upload.getFilePage());
        return new ResponseEntity(fileInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("新增文件信息")
    @PutMapping(value = "/fileInfo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "新增文件信息")
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
    @ApiOperation(value = "删除文件信息")
    @ApiImplicitParam(name = "id", value = "文件信息id", required = true, dataType = "Long")
    public ResponseEntity delete(@PathVariable Long id){
        fileInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}