package com.cqhc.modules.meeting.domain;

import com.cqhc.modules.system.domain.Unit;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-25
*/
@Entity
@Data
@Table(name="meeting_type")
public class MeetingType implements Serializable {

    /*
    * ID
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键策略：自动增长
    @Column(name = "id")
   // @NotNull(groups = MeetingType.Update.class)修改时，ID是必填项
    private Long id;

    /*
    * 序号
    * */
    @NotNull
    @Column(name = "sort",nullable = false)
    private short sort;

    /*
    * 名称
    * */
    @NotBlank
    @Column(name = "name",nullable = false)
    private String name;

    /*
    * 单位ID
    * */
    @ManyToOne  //多对一关系
    @JoinColumn(name = "unit_id") //外键注解.
    private Unit unit;

    /**
     * 数据字典meeting_type_status
            true正常
            false停用
     */
    @NotNull
    @Column(name = "enabled",nullable = false)
    private Boolean enabled;

    /*
    * 描述
    * */
    @Column(name = "remark")
    private String remark;

}