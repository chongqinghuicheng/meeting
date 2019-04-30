package com.cqhc.modules.system.repository;

import com.cqhc.modules.system.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-28
*/
public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, JpaSpecificationExecutor {
}