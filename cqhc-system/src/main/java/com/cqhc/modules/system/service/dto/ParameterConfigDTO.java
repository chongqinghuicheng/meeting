package com.cqhc.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-24
*/
@Data
public class ParameterConfigDTO implements Serializable {

    private Long id;

    private String name;

    private UnitDTO unit;

    private String value;

    /**
     * 0-平台级
      1-单位级
     */
    private Integer type;

    private String remark;
}