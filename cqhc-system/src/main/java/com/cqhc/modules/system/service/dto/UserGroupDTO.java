package com.cqhc.modules.system.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
* @author huicheng
* @date 2019-04-26
*/
@Data
public class UserGroupDTO implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String name;

    private Integer sort;

    @ApiModelProperty(hidden = true)
    private Set<UserDTO> users;
}