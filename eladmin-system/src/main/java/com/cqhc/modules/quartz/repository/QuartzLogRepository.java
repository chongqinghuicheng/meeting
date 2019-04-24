package com.cqhc.modules.quartz.repository;

import com.cqhc.modules.quartz.domain.QuartzLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jie
 * @date 2019-01-07
 */
public interface QuartzLogRepository extends JpaRepository<QuartzLog,Long>, JpaSpecificationExecutor {

}
