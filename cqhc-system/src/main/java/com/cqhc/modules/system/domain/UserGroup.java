package com.cqhc.modules.system.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

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
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "sort",nullable = false)
    private Integer sort;
}