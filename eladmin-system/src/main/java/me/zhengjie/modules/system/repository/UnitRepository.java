package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-23
*/
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor {
}