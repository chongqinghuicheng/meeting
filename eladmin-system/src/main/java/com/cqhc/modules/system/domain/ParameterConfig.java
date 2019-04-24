package com.cqhc.modules.system.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-24
*/
@Entity
@Data
@Table(name="parameter_config")
public class ParameterConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "unit_id",nullable = false)
    private Long unitId;

    @Column(name = "value",nullable = false)
    private String value;

    /**
     * 0-平台级
            1-单位级
     */
    @Column(name = "type",nullable = false)
    private Integer type;

    @Column(name = "remark")
    private String remark;
}