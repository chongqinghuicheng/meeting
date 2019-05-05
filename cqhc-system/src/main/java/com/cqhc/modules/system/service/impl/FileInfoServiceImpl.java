package com.cqhc.modules.system.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.FileInfo;
import com.cqhc.modules.system.repository.FileInfoRepository;
import com.cqhc.modules.system.service.FileInfoService;
import com.cqhc.modules.system.service.dto.FileInfoDTO;
import com.cqhc.modules.system.service.mapper.FileInfoMapper;
import com.cqhc.utils.ElAdminConstant;
import com.cqhc.utils.FileUtil;
import com.cqhc.utils.RequestHolder;
import com.cqhc.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
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

    public static final String SUCCESS = "success";

    public static final String CODE = "code";

    public static final String MSG = "msg";

    @Override
    public FileInfoDTO findById(Long id) {
        Optional<FileInfo> fileInfo = fileInfoRepository.findById(id);
        ValidationUtil.isNull(fileInfo,"FileInfo","id",id);
        return fileInfoMapper.toDto(fileInfo.get());
    }

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public FileInfo upload(MultipartFile multipartFile){
        File file = FileUtil.toFile(multipartFile);

        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("smfile", file);
        String result= HttpUtil.post(ElAdminConstant.Url.SM_MS_URL, paramMap);

        JSONObject jsonObject = JSONUtil.parseObj(result);
        FileInfo fileInfo = null;
        if(!jsonObject.get(CODE).toString().equals(SUCCESS)){
            throw new BadRequestException(jsonObject.get(MSG).toString());
        }
        //转成实体类
        fileInfo = JSON.parseObject(jsonObject.get("data").toString(), FileInfo.class);
        fileInfo.setFileName(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename())+"."+FileUtil.getExtensionName(multipartFile.getOriginalFilename()));
        fileInfo.setFileSize(FileUtil.getSize(Integer.valueOf(fileInfo.getFileSize())));
        fileInfoRepository.save(fileInfo);
        return fileInfo;
    }

    /**
     * 上传文件信息
     *
     * @param resources
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfoDTO create(FileInfo resources) {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        resources.setSessionId(sessionId);
        if(resources.getLinkName()==""){
            resources.setLinkName("临时表");
        }
        return fileInfoMapper.toDto(fileInfoRepository.save(resources));
    }

    /**
     * 新增文件信息
     *
     * @param resources
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FileInfo resources) {
        Optional<FileInfo> optionalFileInfo = fileInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalFileInfo,"FileInfo","id",resources.getId());

        FileInfo fileInfo = optionalFileInfo.get();

        fileInfo.setLinkName(resources.getLinkName());
        if(resources.getLinkId()!=null){
            fileInfo.setSessionId("");
            fileInfo.setLinkId(resources.getLinkId());
        }
        fileInfoRepository.save(fileInfo);
    }

    /**
     * 删除文件信息
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        fileInfoRepository.deleteById(id);
    }
}