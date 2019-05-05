package com.cqhc.modules.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author huicheng
* @date 2019-04-28
*/
@Entity
@Data
@Table(name="file_info")
public class FileInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(notes = "上传不需要，新增需要", required = true)
    private Long id;

    @Column(name = "file_name",nullable = false)
    @ApiModelProperty(notes = "文件名", required = true)
    private String fileName;

    @Column(name = "file_path",nullable = false)
    @ApiModelProperty(notes = "文件路径", required = true)
    private String filePath;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "file_page")
    private Integer filePage;

    @Column(name = "version",nullable = false)
    @ApiModelProperty(notes = "版本号", required = true)
    private Integer version;

    /**
     * 方便统计
     */
    @Column(name = "link_name",nullable = false)
    @ApiModelProperty(notes = "关联表名", required = true)
    private String linkName;

    @Column(name = "link_id")
    @ApiModelProperty(notes = "上传不需要，新增需要", required = false)
    private Long linkId;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "creater",nullable = false)
    @ApiModelProperty(notes = "创建人", required = true)
    private Long creater;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;
}