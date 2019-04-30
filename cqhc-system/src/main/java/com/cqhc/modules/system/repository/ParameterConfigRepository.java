package com.cqhc.modules.system.repository;

import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.domain.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-24
*/

/*
* 需要做的：1.只能查看自己单位的参数。
* 2.其他单位只能修改直接单位参数的值
* */
public interface ParameterConfigRepository extends JpaRepository<ParameterConfig, Long>, JpaSpecificationExecutor {

    /*给单位表复制的单位级参数。type=1，UnitId=-1的. */
    @Query("select p from ParameterConfig p where p.type=1 and p.unit.id=-1 ")
    List<ParameterConfig> findByTypeAndUnit_Id(Long unitId);

    /**
     * findByname  为新增判断是否已存在该参数名
     * @param name
     * @return
     */
    ParameterConfig findByNameAndUnit(String name,Unit unit); //查询.可以直接规范方法命名，用JPA的SQL。


}