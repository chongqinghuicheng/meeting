package com.cqhc.modules.system.repository;

import com.cqhc.modules.system.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jie
* @date 2019-04-10
*/
public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor {
}