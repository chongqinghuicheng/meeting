package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-23
*/
@Data
public class ConfigDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String remark;

    /**
     * 单位ID
     */
    private Long unitId;
}