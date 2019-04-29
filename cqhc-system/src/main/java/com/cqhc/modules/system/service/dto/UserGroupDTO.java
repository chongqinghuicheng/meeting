package com.cqhc.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author huicheng
* @date 2019-04-26
*/
@Data
public class UserGroupDTO implements Serializable {

    private Long id;

    private String name;

    private Integer sort;
}