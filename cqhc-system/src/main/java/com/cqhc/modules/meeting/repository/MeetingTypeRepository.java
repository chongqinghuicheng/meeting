package com.cqhc.modules.meeting.repository;

import com.cqhc.modules.meeting.domain.MeetingType;
import com.cqhc.modules.system.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-25
*/
public interface MeetingTypeRepository extends JpaRepository<MeetingType, Long>, JpaSpecificationExecutor {

/*
* 通过类型名称+单位ID查询.给逻辑层新增判断用。
* */
@Query("select m from MeetingType m where m.name = ?1 and m.unit = ?2")
MeetingType findByNameAndUnit(String name, Unit unit);
}