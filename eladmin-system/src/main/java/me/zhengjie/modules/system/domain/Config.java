package me.zhengjie.modules.system.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-23
*/
@Entity
@Data
@Table(name="config")
public class Config implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name",nullable = false)
    private String name;

    /**
     * 值
     */
    @Column(name = "value")
    private String value;

    /**
     * 描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 单位ID
     */
    @Column(name = "unit_id")
    private Long unitId;
}