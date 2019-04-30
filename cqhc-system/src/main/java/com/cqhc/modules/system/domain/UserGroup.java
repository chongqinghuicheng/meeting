package com.cqhc.modules.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
* @author huicheng
* @date 2019-04-26
*/
@Entity
@Data
@Table(name="user_group")
public class UserGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(notes = "新增时不传，修改要传", required = true)
    private Long id;

    @NotBlank
    @Column(name = "name",unique = true)
    @ApiModelProperty(notes = "分组名称", required = true)
    private String name;

    @Column(name = "sort",nullable = false)
    @ApiModelProperty(notes = "分组序号", required = true)
    private Integer sort;

    @ManyToMany
    @JoinTable(name = "user_group_detail", joinColumns = {@JoinColumn(name = "group_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")})
    @ApiModelProperty(notes = "通过user_id传入user", required = false)
    private Set<User> users;
}