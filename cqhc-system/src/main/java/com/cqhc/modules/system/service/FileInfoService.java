package com.cqhc.modules.system.service;

import com.cqhc.modules.system.domain.FileInfo;
import com.cqhc.modules.system.service.dto.FileInfoDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

/**
* @author huicheng
* @date 2019-04-28
*/
@CacheConfig(cacheNames = "fileInfo")
public interface FileInfoService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @CacheEvict(allEntries = true)
    FileInfo upload(MultipartFile file);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    FileInfoDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    FileInfoDTO create(FileInfo resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(FileInfo resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}