package com.cqhc.modules.system.repository;

import com.cqhc.modules.system.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huicheng
* @date 2019-04-26
*/
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor {
}