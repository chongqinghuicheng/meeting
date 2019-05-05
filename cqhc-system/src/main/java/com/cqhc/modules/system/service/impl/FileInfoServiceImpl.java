package com.cqhc.modules.system.service.impl;

import com.cqhc.modules.system.domain.FileInfo;
import com.cqhc.utils.ValidationUtil;
import com.cqhc.modules.system.repository.FileInfoRepository;
import com.cqhc.modules.system.service.FileInfoService;
import com.cqhc.modules.system.service.dto.FileInfoDTO;
import com.cqhc.modules.system.service.mapper.FileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
* @author huicheng
* @date 2019-04-28
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileInfoServiceImpl implements FileInfoService {

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public FileInfoDTO findById(Long id) {
        Optional<FileInfo> fileInfo = fileInfoRepository.findById(id);
        ValidationUtil.isNull(fileInfo,"FileInfo","id",id);
        return fileInfoMapper.toDto(fileInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfoDTO create(FileInfo resources) {
        return fileInfoMapper.toDto(fileInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FileInfo resources) {
        Optional<FileInfo> optionalFileInfo = fileInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalFileInfo,"FileInfo","id",resources.getId());

        FileInfo fileInfo = optionalFileInfo.get();
        // 此处需自己修改
        resources.setId(fileInfo.getId());
        fileInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        fileInfoRepository.deleteById(id);
    }
}