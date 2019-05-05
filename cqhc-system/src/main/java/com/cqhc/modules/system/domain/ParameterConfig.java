package com.cqhc.modules.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-24
*/
@Entity
@Data
@Table(name="parameter_config")
public class ParameterConfig implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //主键策略：自动增长
    @Column(name = "id")
    private Long id;

    /**
     * 名称
     */
    @NotBlank //只能为字符串，且不能为空，长度必须>0
    @ApiModelProperty(name="name",notes="名称")
    @Column(name = "name",nullable = false,unique = true)//唯一,必填
    private String name;

    /**
     * 单位ID
     */
    @OneToOne
    @ApiModelProperty(name="unit_id",notes="单位")
    @JoinColumn(name = "unit_id") //name都是写多的那方的外键
    private Unit unit;

    /**
     * 值
     */
    @NotBlank
    @ApiModelProperty(name="value",notes="值")
    @Column(name = "value",nullable = false)
    private String value;

    /**
     * 0-平台级
            1-单位级
     */
    @NotNull //不能为NULL
    @Range(min = 0,max = 1) //最小为0，最大为1
    @Column(name = "type",nullable = false)
    private short type;

    /**
     * 描述
     */
    @ApiModelProperty(name="remark",notes="描述")
    @Column(name = "remark")
    private String remark;

}