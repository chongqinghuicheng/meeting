package com.cqhc.modules.system.repository;

import com.cqhc.modules.system.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-24
*/
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor {
}