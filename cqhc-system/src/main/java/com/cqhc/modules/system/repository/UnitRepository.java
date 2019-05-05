package com.cqhc.modules.system.repository;

import com.cqhc.modules.system.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author huicheng
* @date 2019-04-24
*/
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor {

    /**
     * 删除时若有数据修改单位状态为停用
     * @param id
     */
    @Modifying
    @Query(value = "update unit set enabled = 0 where id= ?1",nativeQuery = true)
    void updateEnabled(Long id);

    /**
     * 查询该名称是否存在
     * @param name
     * @return
     */
    @Query(value = "select count(*) from unit where name = ?1",nativeQuery = true)
    Long findByName(String name);
}