package com.cqhc.modules.security.repository;

import com.cqhc.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Jcy
 * @create @create: 2019-04-25 16:08
 */
public interface AuthenticationRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    /**
     * 查询用户最否有改密码的最后时间
     *
     * @param id
     * @return
     */
    @Query(value = "select count(*) from user where id = ?1 and last_password_reset_time != 0",nativeQuery = true)
    Long findByIdAndlastPass(Long id);

    /**
     * 查询单位状态
     *
     * @param id
     * @return
     */

    @Query(value = "select enabled from unit where id = ?1",nativeQuery = true)
    Boolean findEnableById(Long id);

    /**
     * 禁用用户账号
     *
     * @param username
     */
    @Modifying
    @Query(value = "update user set enabled = 0 where username = ?1",nativeQuery = true)
    void disenabledUser(String username);

}