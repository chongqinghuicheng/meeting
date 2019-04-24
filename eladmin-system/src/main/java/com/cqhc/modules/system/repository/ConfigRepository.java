package com.cqhc.modules.system.repository;

import com.cqhc.modules.system.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-23
*/
public interface ConfigRepository extends JpaRepository<Config, Long>, JpaSpecificationExecutor {
}