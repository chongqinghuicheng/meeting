package com.cqhc.modules.system.service.query;

import com.cqhc.modules.system.domain.FileInfo;
import com.cqhc.modules.system.repository.FileInfoRepository;
import com.cqhc.modules.system.service.dto.FileInfoDTO;
import com.cqhc.modules.system.service.mapper.FileInfoMapper;
import com.cqhc.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "fileInfo")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileInfoQueryService {

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(FileInfoDTO fileInfo, Pageable pageable){
        Page<FileInfo> page = fileInfoRepository.findAll(new Spec(fileInfo),pageable);
        return PageUtil.toPage(page.map(fileInfoMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(FileInfoDTO fileInfo){
        return fileInfoMapper.toDto(fileInfoRepository.findAll(new Spec(fileInfo)));
    }

    class Spec implements Specification<FileInfo> {

        private FileInfoDTO fileInfo;

        public Spec(FileInfoDTO fileInfo){
            this.fileInfo = fileInfo;
        }

        @Override
        public Predicate toPredicate(Root<FileInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(fileInfo.getFileName())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("file_name").as(String.class),"%"+fileInfo.getFileName()+"%"));
            }
            if(!ObjectUtils.isEmpty(fileInfo.getFilePath())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("file_path").as(String.class),"%"+fileInfo.getFilePath()+"%"));
            }
            if(!ObjectUtils.isEmpty(fileInfo.getFileSize())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("file_size").as(String.class),"%"+fileInfo.getFileSize()+"%"));
            }
            if(!ObjectUtils.isEmpty(fileInfo.getFilePage())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("file_page").as(Integer.class),"%"+fileInfo.getFilePage()+"%"));
            }
            if(!ObjectUtils.isEmpty(fileInfo.getVersion())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("version").as(Integer.class),fileInfo.getVersion()));
            }
            if(!ObjectUtils.isEmpty(fileInfo.getLinkName())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("link_name").as(String.class),"%"+fileInfo.getLinkName()+"%"));
            }
            if(!ObjectUtils.isEmpty(fileInfo.getCreater())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("creater").as(Long.class),"%"+fileInfo.getCreater()+"%"));
            }
            if(!ObjectUtils.isEmpty(fileInfo.getCreateTime())){
                /**
                * 精确
                */
                list.add(cb.equal(root.get("create_time").as(Timestamp.class),fileInfo.getCreateTime()));
            }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}