package com.cqhc.modules.system.repository;

import com.cqhc.modules.system.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author Jcy
* @date 2019-04-26
*/
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor {

    /**
     * 查询分组名字
     * @param name
     * @return
     */
    UserGroup findByName(String name);

    /**
     * 查询人员分组
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Query(value = "select usg.* from ((user_group usg LEFT JOIN user_group_detail usgd ON usg.id = usgd.group_id) \n" +
            "LEFT JOIN `user` us ON us.id = usgd.user_id) group by usg.name order by usg.sort",nativeQuery = true)
    List<UserGroup> findBySort(int pageNumber, int pageSize);

    /**
     * 查询人员分组名单
     *
     * @return
     */
    @Query(value = "select us.username as username from ((user_group usg LEFT JOIN user_group_detail usgd ON usg.id = usgd.group_id) \n" +
            "LEFT JOIN `user` us ON us.id = usgd.user_id) order by usg.sort",nativeQuery = true)
    List<UserGroup> findUsername();

    /**
     * 增加分组与用户关系
     *
     * @param userId
     * @param groupId
     */
    @Modifying
    @Query(value = "insert into user_group_detail(user_id,group_id) values (?1,?2);",nativeQuery = true)
    void createUserGDetail(Long userId,Long groupId);

    /**
     * 查询单位下所有启用的用户
     *
     * @param unitId
     * @return
     */
    @Query(value = "select username from user where unit_id = ?1 and enabled = 1",nativeQuery = true)
    List<UserGroup> findUserByUnitId(Long unitId);

}